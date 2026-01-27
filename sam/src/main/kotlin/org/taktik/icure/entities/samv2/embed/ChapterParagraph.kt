package org.taktik.icure.entities.samv2.embed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ChapterParagraph(
    val chapter: String? = null,
    val paragraph: String? = null
) : Comparable<ChapterParagraph> {
    override fun compareTo(other: ChapterParagraph): Int {
        return if (this == other) {
            0
        } else compareValuesBy(this, other, { it.chapter }, { it.paragraph })
    }
}
