package org.taktik.icure.asynclogic.bridge.mappers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import com.icure.cardinal.sdk.model.HealthcareParty as SdkHealthcareParty
import org.springframework.stereotype.Service
import org.taktik.icure.entities.HealthcareParty
import org.taktik.icure.services.external.rest.v2.dto.HealthcarePartyDto
import org.taktik.icure.services.external.rest.v2.mapper.HealthcarePartyV2Mapper

@Service
class HealthcarePartyMapper(
	@Qualifier("legacyObjectMapper") objectMapper: ObjectMapper,
	healthcarePartyMapper: HealthcarePartyV2Mapper
) : AbstractEntityMapper<HealthcareParty, SdkHealthcareParty, HealthcarePartyDto>(
	objectMapper,
	SdkHealthcareParty::class,
	HealthcarePartyDto::class.java,
	healthcarePartyMapper::map,
	healthcarePartyMapper::map
)