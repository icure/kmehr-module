package org.taktik.icure.asynclogic.samv2.impl

import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.ObjectMapper
import io.icure.asyncjacksonhttpclient.net.addSinglePathComponent
import io.icure.asyncjacksonhttpclient.net.param
import io.icure.asyncjacksonhttpclient.net.web.HttpMethod
import io.icure.asyncjacksonhttpclient.net.web.Request
import io.icure.asyncjacksonhttpclient.net.web.Response
import org.springframework.stereotype.Component
import io.icure.asyncjacksonhttpclient.net.web.WebClient
import io.icure.asyncjacksonhttpclient.parser.StartArray
import io.icure.asyncjacksonhttpclient.parser.nextValue
import io.netty.handler.codec.http.HttpHeaderNames
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withTimeout
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.taktik.icure.asynclogic.samv2.UpdatesBridge
import org.taktik.icure.entities.base.StoredDocument
import org.taktik.icure.entities.samv2.updates.SamUpdate
import org.taktik.icure.entities.samv2.updates.SignatureUpdate
import org.taktik.icure.entities.samv2.updates.UpdateType
import java.net.URI
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@Component
@ConditionalOnProperty(name = ["icure.sam.updaterUrl"], matchIfMissing = false)
class UpdatesBridgeImpl(
	private val client: WebClient,
	private val objectMapper: ObjectMapper,
	@param:Value("\${icure.sam.entityDownloadTimeout:60}") private val entityDownloadTimeout: Int = 60,
	@Value("\${icure.sam.updaterUrl}") updaterUrl: String,
) : UpdatesBridge {

	class UpdaterBridgeException(code: Int, message: String): Exception("Error received from updater backend $code: $message")

	private val baseUri = URI(updaterUrl)

	override suspend fun getFollowingUpdates(jwt: String, currentPatch: SamUpdate?, forceSnapshot: Boolean): List<SamUpdate> = client
		.uri(baseUri
			.addSinglePathComponent("api")
			.addSinglePathComponent("updates")
			.param("forceSnapshot", forceSnapshot.toString())
		)
		.header(HttpHeaderNames.AUTHORIZATION.toString(), "Bearer $jwt")
		.header(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
		.method(HttpMethod.POST)
		.let {
			if (currentPatch != null) {
				it.body(objectMapper.writeValueAsString(currentPatch))
			} else it
		}
		.retrieveAndParseArrayResponse(SamUpdate::class.java)
		.toList()

	override fun <T : StoredDocument> getEntityUpdateContent(
		klass: Class<T>,
		patchId: String,
		type: UpdateType,
		resourceName: String
	): Flow<T> = getAnyUpdateContent(klass, patchId, type, resourceName)

	override fun getEntityDeleteContent(
		patchId: String,
		type: UpdateType,
		resourceName: String
	): Flow<String> = getAnyUpdateContent(String::class.java, patchId, type, resourceName)

	override fun getSignaturesUpdateContent(
		patchId: String,
		type: UpdateType,
		resourceName: String
	): Flow<SignatureUpdate> = getAnyUpdateContent(SignatureUpdate::class.java, patchId, type, resourceName)

	private fun <T : Any> getAnyUpdateContent(
		klass: Class<T>,
		patchId: String,
		type: UpdateType,
		resourceName: String
	): Flow<T> = client
		.uri(baseUri.addSinglePathComponent(patchId).addSinglePathComponent(type.urlComponent).addSinglePathComponent(resourceName))
		.method(HttpMethod.GET)
		.retrieveAndParseArrayResponse(klass)

	private fun <T : Any> Request.retrieveAndParseArrayResponse(klass: Class<T>): Flow<T> = flow {
		coroutineScope {
			val asyncParser = objectMapper.createNonBlockingByteArrayParser()
			val jsonEvents = this@retrieveAndParseArrayResponse.retrieve()
				.throwOnError()
				.toJsonEvents(asyncParser)
				.produceIn(this)
			val firstEvent = withTimeout(entityDownloadTimeout.seconds) {
				jsonEvents.receive()
			}
			check(firstEvent == StartArray) { "First event must be StartArray" }
			do {
				val nextValue = withTimeout(entityDownloadTimeout.seconds) {
					jsonEvents.nextValue(asyncParser) // This method returns null only if the next token is EndArray
				}
				val nextToken = nextValue?.firstToken()
				when {
					klass != String::class.java && nextToken == JsonToken.START_OBJECT -> withTimeout(entityDownloadTimeout.seconds) {
						emit(nextValue.asParser(objectMapper).readValueAs(klass))
					}
					klass == String::class.java && nextToken == JsonToken.VALUE_STRING -> {
						// It's ok to suppress it, type inference does not realize klass is string
						@Suppress("UNCHECKED_CAST")
						emit(nextValue.asParser(objectMapper).readValueAs(klass) as T)
					}
					nextToken == null -> {}
					else -> error("Unexpected token type: $nextToken")
				}
			} while(nextToken != null)
			jsonEvents.cancel()
		}
	}

	private fun Response.throwOnError() = onStatus(400) {
		throw UpdaterBridgeException(it.statusCode, it.responseBodyAsString())
	}.onStatus(401) {
		throw UpdaterBridgeException(it.statusCode, it.responseBodyAsString())
	}.onStatus(403) {
		throw UpdaterBridgeException(it.statusCode, it.responseBodyAsString())
	}.onStatus(404) {
		throw UpdaterBridgeException(it.statusCode, it.responseBodyAsString())
	}.onStatus(500) {
		throw UpdaterBridgeException(it.statusCode, it.responseBodyAsString())
	}.onStatus(502) {
		throw UpdaterBridgeException(it.statusCode, it.responseBodyAsString())
	}.onStatus(503) {
		throw UpdaterBridgeException(it.statusCode, it.responseBodyAsString())
	}
}