package org.taktik.icure.entities.samv2.embed

import org.taktik.icure.be.samv2v6.entities.DosageParameterType
import java.math.BigDecimal

data class BoundedParameter(
    val dosageParameter: DosageParameter? = null,
    val lowerBound: BigDecimal? = null,
    val upperBound: BigDecimal? = null
)
