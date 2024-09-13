package org.taktik.icure.entities.samv2.embed

data class Indication(
    val code: String? = null,
    val name: SamText? = null,
    val snomedCT: String? = null,
)
