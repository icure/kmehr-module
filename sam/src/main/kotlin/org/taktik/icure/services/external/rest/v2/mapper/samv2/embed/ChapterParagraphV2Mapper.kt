package org.taktik.icure.services.external.rest.v2.mapper.samv2.embed

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.taktik.icure.entities.samv2.embed.ChapterParagraph
import org.taktik.icure.services.external.rest.v2.dto.samv2.embed.ChapterParagraphDto

@Mapper(componentModel = "spring", uses = [], injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface ChapterParagraphV2Mapper  {
    fun map(chapterParagraphDto: ChapterParagraphDto): ChapterParagraph
    fun map(chapterParagraph: ChapterParagraph): ChapterParagraphDto
}
