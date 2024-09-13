package org.taktik.icure.entities.samv2.embed

import org.taktik.icure.be.samv2v6.entities.Text255Type
import org.taktik.icure.be.samv2v6.entities.TextType

data class DosageParameter(
    val code: String? = null,
    val name: SamText? = null,
    val definition: SamText? = null,
    val standardUnit: String? = null,
    val snomedCT: String? = null
)
