package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.api.raw.RawFormApi
import com.icure.utils.InternalIcureApi
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.taktik.icure.asynclogic.FormLogic
import org.taktik.icure.asynclogic.bridge.mappers.FormMapper
import org.taktik.icure.entities.Form
import org.taktik.icure.entities.embed.Delegation
import org.taktik.icure.entities.requests.BulkShareOrUpdateMetadataParams
import org.taktik.icure.entities.requests.EntityBulkShareResult
import org.taktik.icure.exceptions.BridgeException

@OptIn(InternalIcureApi::class)
@Service
class FormLogicBridge(
    private val rawFormApi: RawFormApi,
    private val formMapper: FormMapper
) : GenericLogicBridge<Form>(), FormLogic {

    override suspend fun createForm(form: Form): Form? =
        rawFormApi.createForm(form.let(formMapper::map))
            .successBody()
            .let(formMapper::map)

    override suspend fun addDelegation(formId: String, delegation: Delegation): Form? {
        throw BridgeException()
    }

    override suspend fun addDelegations(formId: String, delegations: List<Delegation>): Form? {
        throw BridgeException()
    }

    override fun listFormsByLogicalUuid(formUuid: String, descending: Boolean): Flow<Form> {
        throw BridgeException()
    }

    override fun listFormsByUniqueId(lid: String, descending: Boolean): Flow<Form> {
        throw BridgeException()
    }

    override fun bulkShareOrUpdateMetadata(requests: BulkShareOrUpdateMetadataParams): Flow<EntityBulkShareResult<Form>> {
        throw BridgeException()
    }

    override suspend fun getForm(id: String): Form? {
        throw BridgeException()
    }

    override fun getForms(selectedIds: Collection<String>): Flow<Form> {
        throw BridgeException()
    }

    override fun listByHcPartyAndParentId(hcPartyId: String, formId: String): Flow<Form> {
        throw BridgeException()
    }

    @Deprecated("This method cannot include results with secure delegations, use listFormIdsByDataOwnerPatientOpeningDate instead")
    override fun listFormsByHCPartyAndPatient(
        hcPartyId: String,
        secretPatientKeys: List<String>,
        healthElementId: String?,
        planOfActionId: String?,
        formTemplateId: String?
    ): Flow<Form> {
        throw BridgeException()
    }

    override fun listFormIdsByDataOwnerPatientOpeningDate(
        dataOwnerId: String,
        secretForeignKeys: Set<String>,
        startDate: Long?,
        endDate: Long?,
        descending: Boolean
    ): Flow<String> {
        throw BridgeException()
    }

    override suspend fun modifyForm(form: Form): Form? {
        throw BridgeException()
    }
}
