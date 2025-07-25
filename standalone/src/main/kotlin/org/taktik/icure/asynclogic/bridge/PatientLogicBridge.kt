package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.cardinal.sdk.api.raw.RawPatientApi
import com.icure.utils.InternalIcureApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.couchdb.entity.ComplexKey
import org.taktik.icure.asynclogic.PatientLogic
import org.taktik.icure.asynclogic.bridge.mappers.PatientFilterMapper
import org.taktik.icure.asynclogic.bridge.mappers.PatientMapper
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.db.Sorting
import org.taktik.icure.domain.filter.AbstractFilter
import org.taktik.icure.domain.filter.chain.FilterChain
import org.taktik.icure.entities.Patient
import org.taktik.icure.entities.embed.Delegation
import org.taktik.icure.entities.requests.BulkShareOrUpdateMetadataParams
import org.taktik.icure.entities.requests.EntityBulkShareResult
import org.taktik.icure.exceptions.BridgeException
import org.taktik.icure.pagination.PaginationElement
import java.time.Instant

@OptIn(InternalIcureApi::class)
@Service
class PatientLogicBridge(
	private val sdk: CardinalBaseApis,
	private val rawPatientApi: RawPatientApi,
	private val patientMapper: PatientMapper,
	private val patientFilterMapper: PatientFilterMapper
) : GenericLogicBridge<Patient>(), PatientLogic {

	override suspend fun addDelegations(patientId: String, delegations: Collection<Delegation>): Patient? {
		throw BridgeException()
	}

	override fun bulkShareOrUpdateMetadata(requests: BulkShareOrUpdateMetadataParams): Flow<EntityBulkShareResult<Patient>> {
		throw BridgeException()
	}

	override suspend fun countByHcParty(healthcarePartyId: String): Int {
		throw BridgeException()
	}

	override suspend fun createPatient(patient: Patient): Patient? =
		rawPatientApi.createPatient(patientMapper.map(patient))
			.successBody()
			.let(patientMapper::map)

	override fun createPatients(patients: List<Patient>): Flow<Patient> = flow {
		emitAll(
			rawPatientApi.createPatientsFull(patients.map(patientMapper::map))
				.successBody().let { result ->
				getPatients(result.map { it.id })
			}
		)
	}

	override suspend fun addDelegation(patientId: String, delegation: Delegation): Patient? {
		throw BridgeException()
	}

	override fun findByHcPartyAndIdentifier(healthcarePartyId: String, system: String, id: String): Flow<Patient> {
		throw BridgeException()
	}

	override fun findByHcPartyAndSsin(
		ssin: String?,
		healthcarePartyId: String,
		paginationOffset: PaginationOffset<List<String>>,
	): Flow<ViewQueryResultEvent> {
		throw BridgeException()
	}

	override fun findByHcPartyDateOfBirth(
		date: Int?,
		healthcarePartyId: String,
		paginationOffset: PaginationOffset<List<String>>,
	): Flow<ViewQueryResultEvent> {
		throw BridgeException()
	}

	override suspend fun findByUserId(id: String): Patient? {
		throw BridgeException()
	}

	override fun findDeletedPatientsByDeleteDate(
		start: Long,
		end: Long?,
		descending: Boolean,
		paginationOffset: PaginationOffset<Long>,
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun fuzzySearchPatients(
		firstName: String?,
		lastName: String?,
		dateOfBirth: Int?,
		healthcarePartyId: String?,
	): Flow<Patient> {
		throw BridgeException()
	}

	override suspend fun getAesExchangeKeysForDelegate(healthcarePartyId: String): Map<String, Map<String, Map<String, String>>> {
		throw BridgeException()
	}

	override fun listOfPatientsModifiedAfter(
		date: Long,
		paginationOffset: PaginationOffset<Long>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun getDuplicatePatientsBySsin(
		healthcarePartyId: String,
		paginationOffset: PaginationOffset<ComplexKey>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun getDuplicatePatientsByName(
		healthcarePartyId: String,
		paginationOffset: PaginationOffset<ComplexKey>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override suspend fun getByExternalId(externalId: String): Patient? {
		throw BridgeException()
	}

	@Deprecated("A DataOwner may now have multiple AES Keys. Use getAesExchangeKeysForDelegate instead")
	override suspend fun getHcPartyKeysForDelegate(healthcarePartyId: String): Map<String, String> {
		throw BridgeException()
	}

	override suspend fun getPatient(patientId: String): Patient? =
		sdk.patient.getPatient(patientId)?.let(patientMapper::map)

	override fun getPatients(patientIds: Collection<String>): Flow<Patient> = flow {
		emitAll(sdk.patient
			.getPatients(patientIds.toList())
			.map(patientMapper::map)
			.asFlow()
		)
	}

	override fun listDeletedPatientsByNames(firstName: String?, lastName: String?): Flow<Patient> {
		throw BridgeException()
	}

	override suspend fun mergePatients(
		fromId: String,
		expectedFromRev: String,
		updatedInto: Patient,
		omitEncryptionKeysOfFrom: Boolean
	): Patient {
		throw BridgeException()
	}

	override fun listOfMergesAfter(date: Long?): Flow<Patient> {
		throw BridgeException()
	}

	override fun findByHcPartyIdsOnly(
		healthcarePartyId: String,
		offset: PaginationOffset<ComplexKey>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun findByHcPartyAndSsinOrDateOfBirthOrNameContainsFuzzy(
		healthcarePartyId: String,
		offset: PaginationOffset<ComplexKey>,
		searchString: String?,
		sorting: Sorting<PatientLogic.Companion.PatientSearchField>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override fun listPatients(
		paginationOffset: PaginationOffset<*>,
		filterChain: FilterChain<Patient>,
		sort: String?,
		desc: Boolean?,
	): Flow<ViewQueryResultEvent> {
		throw BridgeException()
	}

	override fun findByHcPartyNameContainsFuzzy(
		searchString: String?,
		healthcarePartyId: String,
		offset: PaginationOffset<ComplexKey>,
		descending: Boolean
	): Flow<ViewQueryResultEvent> {
		throw BridgeException()
	}

	override fun findOfHcPartyNameContainsFuzzy(
		searchString: String?,
		healthcarePartyId: String,
		offset: PaginationOffset<ComplexKey>,
		descending: Boolean
	): Flow<ViewQueryResultEvent> {
		throw BridgeException()
	}

	override fun findOfHcPartyAndSsinOrDateOfBirthOrNameContainsFuzzy(
		healthcarePartyId: String,
		offset: PaginationOffset<ComplexKey>,
		searchString: String?,
		sorting: Sorting<PatientLogic.Companion.PatientSearchField>
	): Flow<PaginationElement> {
		throw BridgeException()
	}

	override suspend fun modifyPatient(patient: Patient): Patient? {
		throw BridgeException()
	}

	override suspend fun modifyPatientReferral(
		patient: Patient,
		referralId: String?,
		start: Instant?,
		end: Instant?,
	): Patient? {
		throw BridgeException()
	}

	override fun modifyPatients(patients: Collection<Patient>): Flow<Patient> {
		throw BridgeException()
	}

	override fun matchEntitiesBy(filter: AbstractFilter<*>): Flow<String> = flow {
		patientFilterMapper.mapOrNull(filter)?.also {
			emitAll(rawPatientApi.matchPatientsBy(it).successBody().asFlow())
		} ?: throw IllegalArgumentException("Unsupported filter ${filter::class.simpleName}")
	}

}
