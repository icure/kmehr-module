package org.taktik.icure.asynclogic.bridge.mappers

import com.fasterxml.jackson.databind.ObjectMapper
import com.icure.cardinal.sdk.model.EncryptedContact
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.taktik.icure.entities.Contact
import org.taktik.icure.services.external.rest.v2.dto.ContactDto
import org.taktik.icure.services.external.rest.v2.mapper.ContactV2Mapper

@Service
class ContactMapper(
	@Qualifier("legacyObjectMapper") objectMapper: ObjectMapper,
	contactMapper: ContactV2Mapper
) : AbstractEntityMapper<Contact, EncryptedContact, ContactDto>(
	objectMapper,
	EncryptedContact::class,
	ContactDto::class.java,
	contactMapper::map,
	contactMapper::map
)