package org.taktik.icure.services.external.rest.v2.dto.samv2.embed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ChapterParagraphDto(
    val chapter: String? = null,
    val paragraph: String? = null
)
