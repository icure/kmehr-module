/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.services.external.rest.v1.mapper.samv2.embed

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.taktik.icure.entities.samv2.embed.ComplexStrength
import org.taktik.icure.entities.samv2.embed.Quantity
import org.taktik.icure.services.external.rest.v1.dto.samv2.embed.QuantityDto
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
abstract class QuantityMapper {
	abstract fun map(quantityDto: QuantityDto): Quantity
	abstract fun map(quantity: Quantity): QuantityDto
	fun mapToStrength(quantity: QuantityDto): ComplexStrength = ComplexStrength(quantity = map(quantity))
	fun mapFromStrength(strength: ComplexStrength): QuantityDto = strength.quantity?.let { map(it) } ?: throw IllegalArgumentException("Quantity is null")
}
