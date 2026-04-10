/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */
package org.taktik.icure.services.external.rest.v2.mapper.samv2

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.taktik.icure.decimal.BigDecimalWithScaleIndependentEquality
import java.math.BigDecimal
import java.time.Instant

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
abstract class BigDecimalMapper {
	fun map(bigDecimal: BigDecimal): BigDecimalWithScaleIndependentEquality =
		BigDecimalWithScaleIndependentEquality(bigDecimal)

	fun map(bigDecimalWithScaleIndependentEquality: BigDecimalWithScaleIndependentEquality): BigDecimal =
		bigDecimalWithScaleIndependentEquality.value

}
