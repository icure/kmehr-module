package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.utils.InternalIcureApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.taktik.icure.asynclogic.FormTemplateLogic
import org.taktik.icure.asynclogic.bridge.mappers.FormTemplateMapper
import org.taktik.icure.asynclogic.impl.BridgeAsyncSessionLogic
import org.taktik.icure.config.CardinalSdkConfig
import org.taktik.icure.entities.FormTemplate
import org.taktik.icure.exceptions.BridgeException

@OptIn(InternalIcureApi::class)
@Service
class FormTemplateLogicBridge(
    private val sdk: CardinalBaseApis,
    private val legacyFormTemplateApi: CardinalSdkConfig.FormTemplateLegacyApi,
    private val asyncSessionLogic: BridgeAsyncSessionLogic,
    private val formTemplateMapper: FormTemplateMapper
) : GenericLogicBridge<FormTemplate>(), FormTemplateLogic {

    override suspend fun createFormTemplate(entity: FormTemplate): FormTemplate =
        sdk.form.createFormTemplate(formTemplateMapper.map(entity)).let(formTemplateMapper::map)

    override fun createFormTemplates(
        entities: Collection<FormTemplate>,
        createdEntities: Collection<FormTemplate>
    ): Flow<FormTemplate> {
        throw BridgeException()
    }

    override suspend fun getFormTemplate(formTemplateId: String): FormTemplate? {
        throw BridgeException()
    }

    @Deprecated("This method has unintuitive behaviour, read FormTemplateService.getFormTemplatesByGuid doc for more info")
    override fun getFormTemplatesByGuid(
        userId: String,
        specialityCode: String,
        formTemplateGuid: String
    ): Flow<FormTemplate> = flow {
        if (userId != asyncSessionLogic.getCurrentUserId())
            throw AccessDeniedException("You can only get form templates for the current authenticated user")

        emitAll(
            legacyFormTemplateApi.getFormTemplatesByGuid(formTemplateGuid, specialityCode)
                .map(formTemplateMapper::map)
                .asFlow()
        )
    }

    override fun getFormTemplatesBySpecialty(specialityCode: String, loadLayout: Boolean): Flow<FormTemplate> {
        throw BridgeException()
    }

    override fun getFormTemplatesByUser(userId: String, loadLayout: Boolean): Flow<FormTemplate> {
        throw BridgeException()
    }

    override suspend fun modifyFormTemplate(formTemplate: FormTemplate): FormTemplate? {
        throw BridgeException()
    }
}
