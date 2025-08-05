package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.cardinal.sdk.api.raw.RawContactApi
import com.icure.cardinal.sdk.filters.ContactFilters
import com.icure.utils.InternalIcureApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.couchdb.entity.ComplexKey
import org.taktik.icure.asynclogic.ContactLogic
import org.taktik.icure.asynclogic.bridge.mappers.ContactFilterMapper
import org.taktik.icure.asynclogic.bridge.mappers.ContactMapper
import org.taktik.icure.asynclogic.bridge.mappers.ServiceFilterMapper
import org.taktik.icure.asynclogic.bridge.mappers.ServiceMapper
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.domain.filter.AbstractFilter
import org.taktik.icure.domain.filter.chain.FilterChain
import org.taktik.icure.entities.Contact
import org.taktik.icure.entities.data.LabelledOccurence
import org.taktik.icure.entities.embed.Delegation
import org.taktik.icure.entities.embed.Service
import org.taktik.icure.entities.requests.BulkShareOrUpdateMetadataParams
import org.taktik.icure.entities.requests.EntityBulkShareResult
import org.taktik.icure.exceptions.BridgeException
import org.taktik.icure.pagination.PaginationElement
import org.springframework.stereotype.Service as SpringService

@OptIn(InternalIcureApi::class)
@SpringService
class ContactLogicBridge(
    private val sdk: CardinalBaseApis,
    private val rawContactApi: RawContactApi,
    private val contactMapper: ContactMapper,
    private val serviceMapper: ServiceMapper,
    private val contactFilterMapper: ContactFilterMapper,
    private val serviceFilterMapper: ServiceFilterMapper
) : GenericLogicBridge<Contact>(), ContactLogic {

    override suspend fun createContact(contact: Contact): Contact? =
        rawContactApi.createContact(contactMapper.map(contact))
            .successBody()
            .let(contactMapper::map)

    override fun createContacts(contacts: Flow<Contact>): Flow<Contact> {
        throw BridgeException()
    }

    override suspend fun addDelegation(contactId: String, delegation: Delegation): Contact? {
        throw BridgeException()
    }

    override suspend fun addDelegations(contactId: String, delegations: List<Delegation>): Contact? {
        throw BridgeException()
    }

    override fun bulkShareOrUpdateMetadata(requests: BulkShareOrUpdateMetadataParams): Flow<EntityBulkShareResult<Contact>> {
        throw BridgeException()
    }

    override fun filterContacts(
        paginationOffset: PaginationOffset<Nothing>,
        filter: FilterChain<Contact>
    ): Flow<ViewQueryResultEvent> {
        throw BridgeException()
    }

    override fun filterServices(
        paginationOffset: PaginationOffset<Nothing>,
        filter: FilterChain<Service>
    ): Flow<Service> {
        throw BridgeException()
    }

    override fun listContactsByOpeningDate(
        hcPartyId: String,
        startOpeningDate: Long,
        endOpeningDate: Long,
        offset: PaginationOffset<ComplexKey>
    ): Flow<PaginationElement> {
        throw BridgeException()
    }

    override fun findContactsByIds(selectedIds: Collection<String>): Flow<ViewQueryResultEvent> {
        throw BridgeException()
    }

    override suspend fun getContact(id: String): Contact? =
        sdk.contact.getContact(id)?.let(contactMapper::map)

    override fun getContacts(selectedIds: Collection<String>): Flow<Contact> {
        throw BridgeException()
    }

    override suspend fun getServiceCodesOccurences(
        hcPartyId: String,
        codeType: String,
        minOccurences: Long
    ): List<LabelledOccurence> {
        throw BridgeException()
    }

    override fun getServices(selectedServiceIds: Collection<String>): Flow<Service> = flow {
        emitAll(sdk.contact
            .getServices(selectedServiceIds.toList())
            .map(serviceMapper::map)
            .asFlow()
        )
    }

    override fun getServicesLinkedTo(
        ids: List<String>,
        linkType: String?
    ): Flow<Service> {
        throw BridgeException()
    }

    override fun listContactIdsByDataOwnerPatientOpeningDate(
        dataOwnerId: String,
        secretForeignKeys: Set<String>,
        startDate: Long?,
        endDate: Long?,
        descending: Boolean
    ): Flow<String> {
        throw BridgeException()
    }

    override fun listContactsByExternalId(externalId: String): Flow<Contact> {
        throw BridgeException()
    }

    @Deprecated("This method cannot include results with secure delegations, use listContactIdsByDataOwnerPatientOpeningDate instead")
    override fun listContactsByHCPartyAndPatient(hcPartyId: String, secretPatientKeys: List<String>): Flow<Contact> = flow {
        val contactFilter = ContactFilters.byPatientSecretIdsOpeningDateForDataOwner(
            dataOwnerId = hcPartyId,
            secretIds = secretPatientKeys
        )
        val contactIds = sdk.contact.matchContactsBy(contactFilter)
        if (contactIds.isNotEmpty()) {
            emitAll(sdk.contact
                .getContacts(contactIds)
                .map(contactMapper::map)
                .asFlow()
            )
        }
    }

    override fun listContactsByHcPartyAndFormId(hcPartyId: String, formId: String): Flow<Contact> {
        throw BridgeException()
    }

    override fun listContactsByHcPartyAndFormIds(hcPartyId: String, ids: List<String>): Flow<Contact> {
        throw BridgeException()
    }

    override fun listContactsByHcPartyServiceId(hcPartyId: String, serviceId: String): Flow<Contact> {
        throw BridgeException()
    }

    override fun listServicesByAssociationId(associationId: String): Flow<Service> {
        throw BridgeException()
    }

    override fun listServicesByHcPartyAndHealthElementIds(
        hcPartyId: String,
        healthElementIds: List<String>
    ): Flow<Service> {
        throw BridgeException()
    }

    override fun modifyEntities(entities: Flow<Contact>): Flow<Contact> = flow {
        emitAll(
            rawContactApi.modifyContacts(
                entities.map(contactMapper::map).toList()
            ).successBody().map(contactMapper::map).asFlow()
        )
    }

    override fun matchEntitiesBy(filter: AbstractFilter<*>): Flow<String> = flow {
        contactFilterMapper.mapOrNull(filter)?.also {
            emitAll(rawContactApi.matchContactsBy(it).successBody().asFlow())
        } ?: serviceFilterMapper.mapOrNull(filter)?.also {
            emitAll(rawContactApi.matchServicesBy(it).successBody().asFlow())
        } ?: throw IllegalArgumentException("Unsupported filter ${filter::class.simpleName}")
    }

    override fun modifyEntities(entities: Collection<Contact>): Flow<Contact> = modifyEntities(entities.asFlow())
}
