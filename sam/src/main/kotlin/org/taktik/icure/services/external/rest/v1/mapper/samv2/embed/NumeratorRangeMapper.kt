/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.services.external.rest.v1.mapper.samv2.embed

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.taktik.icure.entities.samv2.embed.NumeratorRange
import org.taktik.icure.services.external.rest.v1.dto.samv2.embed.NumeratorRangeDto
import org.taktik.icure.services.external.rest.v2.mapper.samv2.BigDecimalMapper

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = [BigDecimalMapper::class])
interface NumeratorRangeMapper {
	fun map(numeratorRangeDto: NumeratorRangeDto): NumeratorRange
	fun map(numeratorRange: NumeratorRange): NumeratorRangeDto
}
