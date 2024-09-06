package org.taktik.icure.services.external.rest.v2.dto.samv2.embed

import org.taktik.icure.be.samv2v6.entities.DosageParameterType
import java.math.BigDecimal

data class BoundedParameterDto(
    val dosageParameter: DosageParameterDto? = null,
    val lowerBound: BigDecimal? = null,
    val upperBound: BigDecimal? = null
)
