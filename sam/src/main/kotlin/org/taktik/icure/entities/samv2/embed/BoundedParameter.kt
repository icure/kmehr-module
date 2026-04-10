package org.taktik.icure.entities.samv2.embed

import org.taktik.icure.be.samv2v6.entities.DosageParameterType
import org.taktik.icure.decimal.BigDecimalWithScaleIndependentEquality

data class BoundedParameter(
    val dosageParameter: DosageParameter? = null,
    val lowerBound: BigDecimalWithScaleIndependentEquality? = null,
    val upperBound: BigDecimalWithScaleIndependentEquality? = null
)
