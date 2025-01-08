package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.api.raw.impl.RawCodeApiImpl
import com.icure.cardinal.sdk.model.ListOfIds
import com.icure.cardinal.sdk.model.filter.code.CodeIdsByTypeCodeVersionIntervalFilter
import com.icure.utils.InternalIcureApi
import com.icure.cardinal.sdk.utils.Serialization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.icure.asynclogic.CodeLogic
import org.taktik.icure.asynclogic.bridge.auth.KmehrAuthProvider
import org.taktik.icure.asynclogic.bridge.mappers.CodeMapper
import org.taktik.icure.asynclogic.impl.BridgeAsyncSessionLogic
import org.taktik.icure.config.BridgeConfig
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.domain.filter.chain.FilterChain
import org.taktik.icure.entities.base.Code
import org.taktik.icure.entities.base.CodeStub
import org.taktik.icure.errors.UnauthorizedException
import org.taktik.icure.exceptions.BridgeException
import org.taktik.icure.pagination.PaginationElement
import java.io.InputStream

@Service
class CodeLogicBridge(
	private val asyncSessionLogic: BridgeAsyncSessionLogic,
	private val bridgeConfig: BridgeConfig,
	private val codeMapper: CodeMapper,
) : GenericLogicBridge<Code>(), CodeLogic {

	@OptIn(InternalIcureApi::class)
	private suspend fun getApi() = asyncSessionLogic.getCurrentJWT()?.let { token ->
		RawCodeApiImpl(
			apiUrl = bridgeConfig.iCureUrl,
			authProvider = KmehrAuthProvider(token),
			httpClient = bridgeHttpClient,
			json = Serialization.json
		)
	} ?: throw UnauthorizedException("You must be logged in to perform this operation")

	override suspend fun create(batch: List<Code>): List<Code>? {
		throw BridgeException()
	}

	override suspend fun modify(code: Code): Code? {
		throw BridgeException()
	}

	@OptIn(InternalIcureApi::class)
	override suspend fun create(code: Code): Code? =
		getApi().createCode(codeMapper.map(code)).successBody().let(codeMapper::map)

	@OptIn(InternalIcureApi::class)
	override fun findCodesBy(type: String?, code: String?, version: String?): Flow<Code> = flow {
		val filter = CodeIdsByTypeCodeVersionIntervalFilter(
			startType = type,
			startCode = code,
			startVersion = version,
			endType = type,
			endCode = code,
			endVersion = version
		)
		val api = getApi()
		val codeIds = api.matchCodesBy(filter).successBody()
		emitAll(
			api.getCodes(ListOfIds(codeIds))
				.successBody()
				.map(codeMapper::map)
				.asFlow()
		)
	}

	override fun findCodesBy(region: String?, type: String?, code: String?, version: String?): Flow<Code> {
		throw BridgeException()
	}

	override fun findCodesBy(
		region: String?,
		type: String?,
		code: String?,
		version: String?,
		paginationOffset: PaginationOffset<List<String?>>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun findCodesByLabel(
		region: String?,
		language: String,
		types: Set<String>,
		label: String,
		version: String?,
		paginationOffset: PaginationOffset<List<String?>>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override suspend fun get(id: String): Code? {
		throw BridgeException()
	}

	override suspend fun get(type: String, code: String, version: String): Code? {
		throw BridgeException()
	}

	override fun getCodes(ids: List<String>): Flow<Code> {
		throw BridgeException()
	}

	override suspend fun getOrCreateCode(type: String, code: String, version: String): Code? {
		throw BridgeException()
	}

	override fun getRegions(): List<String> {
		throw BridgeException()
	}

	override fun getTagTypeCandidates(): List<String> {
		throw BridgeException()
	}

	override suspend fun <T : Enum<*>> importCodesFromEnum(e: Class<T>) {
		throw BridgeException()
	}

	override suspend fun importCodesFromJSON(stream: InputStream) {
		throw BridgeException()
	}

	override suspend fun importCodesFromXml(md5: String, type: String, stream: InputStream) {
		throw BridgeException()
	}

	@OptIn(InternalIcureApi::class)
	override suspend fun isValid(type: String?, code: String?, version: String?): Boolean =
		if(type != null && code != null)
			getApi().isCodeValid(type, code, version).successBody().response
		else false

	override suspend fun isValid(code: Code, ofType: String?): Boolean {
		throw BridgeException()
	}

	override suspend fun isValid(code: CodeStub, ofType: String?): Boolean = isValid(code.type, code.code, code.version)

	@OptIn(InternalIcureApi::class)
	override suspend fun getCodeByLabel(region: String?, label: String, type: String, languages: List<String>): Code? {
		if(region == null) {
			throw IllegalArgumentException("Region cannot be null")
		}
		return getApi().getCodeByRegionLanguageTypeLabel(region, label, type, languages.joinToString(","))
			.successBody()
			?.let(codeMapper::map)
	}

	override fun listCodeIdsByTypeCodeVersionInterval(
		startType: String?,
		startCode: String?,
		startVersion: String?,
		endType: String?,
		endCode: String?,
		endVersion: String?
	): Flow<String> {
		throw BridgeException()
	}

	override fun findCodesByQualifiedLinkId(
		linkType: String,
		linkedId: String?,
		pagination: PaginationOffset<List<String>>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun listCodeTypesBy(region: String?, type: String?): Flow<String> {
		throw BridgeException()
	}

	override fun listCodes(
		paginationOffset: PaginationOffset<*>?,
		filterChain: FilterChain<Code>,
		sort: String?,
		desc: Boolean?
	): Flow<ViewQueryResultEvent> {
		throw BridgeException()
	}

	override fun modify(batch: List<Code>): Flow<Code> {
		throw BridgeException()
	}

}
