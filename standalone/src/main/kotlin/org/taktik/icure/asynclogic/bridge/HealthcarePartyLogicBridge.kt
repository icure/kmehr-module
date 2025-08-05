package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.cardinal.sdk.api.HealthcarePartyApi
import com.icure.cardinal.sdk.filters.HealthcarePartyFilters
import com.icure.cardinal.sdk.utils.RequestStatusException
import com.icure.cardinal.sdk.utils.Serialization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import com.icure.cardinal.sdk.model.HealthcareParty as SdkHealthcareParty
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.JsonElement
import org.springframework.stereotype.Service
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.couchdb.entity.ComplexKey
import org.taktik.icure.asynclogic.HealthcarePartyLogic
import org.taktik.icure.asynclogic.bridge.mappers.HealthcarePartyMapper
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.domain.filter.chain.FilterChain
import org.taktik.icure.entities.HealthcareParty
import org.taktik.icure.exceptions.BridgeException
import org.taktik.icure.pagination.PaginationElement

@Service
class HealthcarePartyLogicBridge(
	private val sdk: CardinalBaseApis,
	private val healthcarePartyMapper: HealthcarePartyMapper
) : GenericLogicBridge<HealthcareParty>(), HealthcarePartyLogic {

	override suspend fun createHealthcareParty(healthcareParty: HealthcareParty): HealthcareParty? =
		sdk.healthcareParty.createHealthcareParty(healthcarePartyMapper.map(healthcareParty))
			.let { healthcarePartyMapper.map(it) }

	override fun filterHealthcareParties(
		paginationOffset: PaginationOffset<Nothing>,
		filter: FilterChain<HealthcareParty>,
	): Flow<ViewQueryResultEvent> {
		throw BridgeException()
	}

	override fun findHealthcarePartiesBy(
		fuzzyName: String,
		offset: PaginationOffset<String>,
		desc: Boolean?,
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun findHealthcarePartiesBy(offset: PaginationOffset<String>, desc: Boolean?): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun findHealthcarePartiesBySsinOrNihii(
		searchValue: String,
		paginationOffset: PaginationOffset<String>,
		desc: Boolean,
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override suspend fun getAesExchangeKeysForDelegate(healthcarePartyId: String): Map<String, Map<String, Map<String, String>>> {
		throw BridgeException()
	}

	@Deprecated("A HCP may now have multiple AES Keys. Use getAesExchangeKeysForDelegate instead")
	override suspend fun getHcPartyKeysForDelegate(healthcarePartyId: String): Map<String, String> {
		throw BridgeException()
	}

	private suspend fun getHcpHierarchyIdsRecursive(
		api: HealthcarePartyApi,
		healthcarePartyId: String?,
		hcpHierarchy: Set<String> = emptySet()
	): Set<String> =
		if (healthcarePartyId != null) {
			try {
				val hcp = api.getHealthcareParty(healthcarePartyId)
				if (hcp != null) {
					getHcpHierarchyIdsRecursive(api, hcp.parentId, hcpHierarchy + healthcarePartyId)
				} else {
					hcpHierarchy
				}
			} catch (_: RequestStatusException) {
				hcpHierarchy
			}
		} else hcpHierarchy


	override suspend fun getHcpHierarchyIds(sender: HealthcareParty): HashSet<String> =
		getHcpHierarchyIdsRecursive(sdk.healthcareParty, sender.id).toHashSet()

	override fun getHealthcareParties(ids: List<String>): Flow<HealthcareParty> =
		if(ids.isNotEmpty()) flow {
			emitAll(
				sdk.healthcareParty
					.getHealthcareParties(ids )
					.map(healthcarePartyMapper::map)
					.asFlow()
			)
		} else emptyFlow()

	override fun getHealthcarePartiesByParentId(parentId: String): Flow<HealthcareParty> {
		throw BridgeException()
	}

	override suspend fun getHealthcareParty(id: String): HealthcareParty? =
		sdk.healthcareParty.getHealthcareParty(id)?.let {
			healthcarePartyMapper.map(it)
		}

	override suspend fun getPublicKey(healthcarePartyId: String): String? {
		throw BridgeException()
	}

	override fun listHealthcarePartiesBySpecialityAndPostcode(
		type: String,
		spec: String,
		firstCode: String,
		lastCode: String,
		offset: PaginationOffset<ComplexKey>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun listHealthcarePartiesBy(searchString: String, offset: Int, limit: Int): Flow<HealthcareParty> {
		throw BridgeException()
	}

	private fun findHealthcarePartiesByNameRecursive(
		name: String,
		desc: Boolean? = null,
		startKey: JsonElement? = null,
		startDocumentId: String? = null
	) : Flow<HealthcareParty> = flow {
		val result = sdk.healthcareParty.findHealthcarePartiesByName(
			name = name,
			startKey = startKey?.let { Serialization.json.encodeToString(it) },
			startDocumentId = startDocumentId,
			limit = 1000,
			desc = desc
		)
		emitAll(result.rows.map(healthcarePartyMapper::map).asFlow())
		if(result.nextKeyPair?.startKeyDocId != null) {
			emitAll(
				findHealthcarePartiesByNameRecursive(
					name,
					desc,
					result.nextKeyPair?.startKey,
					result.nextKeyPair?.startKeyDocId
				)
			)
		}
	}

	override fun listHealthcarePartiesByName(name: String): Flow<HealthcareParty> = flow {
		emitAll(findHealthcarePartiesByNameRecursive(name))
	}

	// Note: the batch size is intentionally small because in all the usages of this function only the first
	// result is taken, so there is no need to have a bigger batch size.
	private fun listHealthcarePartiesBySsinOrNihii(query: String, filter: (SdkHealthcareParty) -> Boolean): Flow<HealthcareParty> = flow {
		val filterOptions = HealthcarePartyFilters.byNationalIdentifier(query)
		val hcpIds = sdk.healthcareParty.matchHealthcarePartiesBy(filterOptions)

		hcpIds.chunked(50).forEach { batch ->
			emitAll(sdk.healthcareParty
				.getHealthcareParties(batch)
				.filter(filter)
				.map(healthcarePartyMapper::map)
				.asFlow()
			)
		}
	}


	override fun listHealthcarePartiesBySsin(ssin: String): Flow<HealthcareParty> =
		listHealthcarePartiesBySsinOrNihii(ssin) { it.ssin == ssin }

	// Note: the batch size is intentionally small because in all the usages of this function only the first
	// result is taken, so there is no need to have a bigger batch size.
	override fun listHealthcarePartiesByNihii(nihii: String): Flow<HealthcareParty> =
		listHealthcarePartiesBySsinOrNihii(nihii) { it.nihii == nihii }

	override suspend fun modifyHealthcareParty(healthcareParty: HealthcareParty): HealthcareParty? {
		throw BridgeException()
	}

}
