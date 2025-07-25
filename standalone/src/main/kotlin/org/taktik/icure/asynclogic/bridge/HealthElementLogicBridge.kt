package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.cardinal.sdk.api.raw.RawHealthElementApi
import com.icure.cardinal.sdk.filters.HealthElementFilters
import com.icure.utils.InternalIcureApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.icure.asynclogic.HealthElementLogic
import org.taktik.icure.asynclogic.bridge.mappers.HealthElementMapper
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.domain.filter.chain.FilterChain
import org.taktik.icure.entities.HealthElement
import org.taktik.icure.entities.embed.Delegation
import org.taktik.icure.entities.requests.BulkShareOrUpdateMetadataParams
import org.taktik.icure.entities.requests.EntityBulkShareResult
import org.taktik.icure.exceptions.BridgeException

@OptIn(InternalIcureApi::class)
@Service
class HealthElementLogicBridge(
	private val sdk: CardinalBaseApis,
	private val rawHealthElementApi: RawHealthElementApi,
	private val healthElementMapper: HealthElementMapper
) : GenericLogicBridge<HealthElement>(), HealthElementLogic {

	override fun createEntities(entities: Collection<HealthElement>): Flow<HealthElement> = flow {
		emitAll(rawHealthElementApi
			.createHealthElements(entities.map(healthElementMapper::map))
			.successBody()
			.map(healthElementMapper::map)
			.asFlow()
		)
	}

	override fun filter(
		paginationOffset: PaginationOffset<Nothing>,
		filter: FilterChain<HealthElement>
	): Flow<ViewQueryResultEvent> {
		throw BridgeException()
	}

	override suspend fun getHealthElement(healthElementId: String): HealthElement? {
		throw BridgeException()
	}

	override fun getHealthElements(healthElementIds: Collection<String>): Flow<HealthElement> {
		throw BridgeException()
	}

	override suspend fun addDelegation(healthElementId: String, delegation: Delegation): HealthElement? {
		throw BridgeException()
	}

	override suspend fun addDelegations(healthElementId: String, delegations: List<Delegation>): HealthElement? {
		throw BridgeException()
	}

	override fun bulkShareOrUpdateMetadata(requests: BulkShareOrUpdateMetadataParams): Flow<EntityBulkShareResult<HealthElement>> {
		throw BridgeException()
	}

	@Deprecated("This method cannot include results with secure delegations, use listHealthElementIdsByDataOwnerPatientOpeningDate instead")
	override fun listHealthElementsByHcPartyAndSecretPatientKeys(
		hcPartyId: String,
		secretPatientKeys: List<String>
	): Flow<HealthElement> = flow {
		val filter = HealthElementFilters.byPatientSecretIdsOpeningDateForDataOwner(
			dataOwnerId = hcPartyId,
			secretIds = secretPatientKeys
		)
		val healthElementIds = sdk.healthElement.matchHealthElementsBy(filter)
		if (healthElementIds.isNotEmpty()) {
			emitAll(sdk.healthElement
				.getHealthElements(healthElementIds)
				.map(healthElementMapper::map)
				.asFlow()
			)
		}
	}

	override fun listHealthElementIdsByDataOwnerPatientOpeningDate(
		dataOwnerId: String,
		secretForeignKeys: Set<String>,
		startDate: Long?,
		endDate: Long?,
		descending: Boolean
	): Flow<String> {
		throw BridgeException()
	}

	@Deprecated("This method is inefficient for high volumes of keys, use listHealthElementIdsByDataOwnerPatientOpeningDate instead")
	override suspend fun listLatestHealthElementsByHcPartyAndSecretPatientKeys(
		hcPartyId: String,
		secretPatientKeys: List<String>
	): List<HealthElement> = sdk.healthElement.let { api ->
		val filter = HealthElementFilters.byPatientSecretIdsOpeningDateForDataOwner(
			dataOwnerId = hcPartyId,
			secretIds = secretPatientKeys
		)
		val healthElementIds = api.matchHealthElementsBy(filter)
		if (healthElementIds.isNotEmpty()) {
			api.getHealthElements(healthElementIds).groupBy {
				it.healthElementId
			}.values.mapNotNull { value ->
				value.maxByOrNull { it.modified ?: it.created ?: 0L }
			}.map(healthElementMapper::map)
		} else {
			emptyList()
		}

	}

	override suspend fun modifyHealthElement(healthElement: HealthElement): HealthElement? {
		throw BridgeException()
	}
}
