package org.taktik.icure.asynclogic.bridge.mappers

import com.fasterxml.jackson.databind.ObjectMapper
import com.icure.cardinal.sdk.model.EncryptedForm
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.taktik.icure.entities.Form
import org.taktik.icure.services.external.rest.v2.dto.FormDto
import org.taktik.icure.services.external.rest.v2.mapper.FormV2Mapper

@Service
class FormMapper(
	@Qualifier("legacyObjectMapper") objectMapper: ObjectMapper,
	formMapper: FormV2Mapper
) : AbstractEntityMapper<Form, EncryptedForm, FormDto>(
	objectMapper,
	EncryptedForm::class,
	FormDto::class.java,
	formMapper::map,
	formMapper::map
)