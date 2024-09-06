package org.taktik.icure.services.external.rest.v2.dto.samv2.embed

import org.taktik.icure.be.samv2v6.entities.Text255Type
import org.taktik.icure.be.samv2v6.entities.TextType

data class DosageParameterDto(
    val code: String? = null,
    val name: SamTextDto? = null,
    val definition: SamTextDto? = null,
    val standardUnit: String? = null,
    val snomedCT: String? = null
)
