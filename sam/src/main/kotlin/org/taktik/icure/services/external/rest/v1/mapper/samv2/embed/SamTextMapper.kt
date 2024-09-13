/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.services.external.rest.v1.mapper.samv2.embed

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.taktik.icure.entities.samv2.embed.SamText
import org.taktik.icure.services.external.rest.v1.dto.samv2.embed.SamTextDto
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
abstract class SamTextMapper {
	abstract fun map(samTextDto: SamTextDto): SamText
	abstract fun map(samText: SamText): SamTextDto
	fun mapToString(samText: SamText): String = samText.let { it.fr ?: it.nl ?: it.en ?: it.de ?: "" }
	fun mapFromString(string: String): SamText = string.let { SamText(it, it, it, it) }
}
