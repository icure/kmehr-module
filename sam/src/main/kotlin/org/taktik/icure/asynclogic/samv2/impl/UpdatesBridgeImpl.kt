package org.taktik.icure.asynclogic.samv2.impl

import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.icure.asyncjacksonhttpclient.net.addSinglePathComponent
import io.icure.asyncjacksonhttpclient.net.web.HttpMethod
import io.icure.asyncjacksonhttpclient.net.web.Request
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
import org.springframework.beans.factory.annotation.Value
import org.taktik.icure.asynclogic.samv2.UpdatesBridge
import org.taktik.icure.entities.base.StoredDocument
import org.taktik.icure.entities.samv2.updates.SamUpdate
import org.taktik.icure.entities.samv2.updates.SignatureUpdate
import org.taktik.icure.entities.samv2.updates.UpdateType
import java.net.URI

@OptIn(ExperimentalCoroutinesApi::class)
@Component
class UpdatesBridgeImpl(
	private val client: WebClient,
	private val objectMapper: ObjectMapper,
	@Value("\${icure.sam.updaterUrl}") updaterUrl: String,
) : UpdatesBridge {

	private val baseUri = URI(updaterUrl)

	override suspend fun getFollowingUpdates(jwt: String, currentPatch: SamUpdate?): List<SamUpdate> = client
		.uri(baseUri.addSinglePathComponent("api").addSinglePathComponent("updates"))
		.header(HttpHeaderNames.AUTHORIZATION.toString(), "Bearer $jwt")
		.method(HttpMethod.POST)
		.body(objectMapper.writeValueAsString(currentPatch))
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
			val jsonEvents = this@retrieveAndParseArrayResponse.retrieve().toJsonEvents(asyncParser).produceIn(this)
			val firstEvent = jsonEvents.receive()
			check(firstEvent == StartArray) { "First event must be StartArray" }
			do {
				val nextValue = jsonEvents.nextValue(asyncParser) // This method returns null only if the next token is EndArray
				val nextToken = nextValue?.firstToken()
				when {
					klass != String::class.java && nextToken == JsonToken.START_OBJECT -> {
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
			} while(nextValue?.firstToken() != null)
			jsonEvents.cancel()
		}
	}
}