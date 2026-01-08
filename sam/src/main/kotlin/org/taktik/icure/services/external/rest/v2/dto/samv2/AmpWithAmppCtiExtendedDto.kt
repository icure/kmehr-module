package org.taktik.icure.services.external.rest.v2.dto.samv2

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AmpWithAmppCtiExtendedDto(
    val amp: AmpDto,
    val ctiExtended: String
) : Serializable
