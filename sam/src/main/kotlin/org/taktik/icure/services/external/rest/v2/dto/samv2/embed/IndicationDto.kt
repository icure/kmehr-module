package org.taktik.icure.services.external.rest.v2.dto.samv2.embed

data class IndicationDto(
    val code: String? = null,
    val name: SamTextDto? = null,
    val snomedCT: String? = null,
)
