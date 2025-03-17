package org.taktik.icure.services.external.rest.v2.dto.samv2.embed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class StandardDosageDto(
    val from: Long? = null,
    val to: Long? = null,
    val code: Int? = null,
    val targetGroup: String? = null,
    val kidneyFailureClass: Int? = null,
    val liverFailureClass: Int? = null,
    val treatmentDurationType: String? = null,
    val temporalityDuration: QuantityDto? = null,
    val temporalityUserProvided: Boolean? = null,
    val temporalityNote: SamTextDto? = null,
    val quantity: BigDecimal? = null,
    val quantityDenominator: BigDecimal? = null,
    val quantityMultiplicator: String? = null,
    val quantityRangeLower: BigDecimal? = null,
    val quantityRangeLowerDenominator: BigDecimal? = null,
    val quantityRangeUpper: BigDecimal? = null,
    val quantityRangeUpperDenominator: BigDecimal? = null,
    val administrationFrequencyQuantity: Int? = null,
    val administrationFrequencyIsMax: Boolean? = null,
    val administrationFrequencyTimeframe: QuantityDto? = null,
    val maximumAdministrationQuantity: BigDecimal? = null,
    val maximumAdministrationQuantityDenominator: BigDecimal? = null,
    val maximumAdministrationQuantityMultiplicator: String? = null,
    val maximumDailyQuantity: BigDecimal? = null,
    val maximumDailyQuantityDenominator: BigDecimal? = null,
    val maximumDailyQuantityMultiplicator: String? = null,
    val textualDosage: SamTextDto? = null,
    val supplementaryInfo: SamTextDto? = null,
    val routeSpecification: SamTextDto? = null,
    val indication: List<IndicationDto>? = null,
    val parameterBounds: List<BoundedParameterDto>? = null,
    val routeOfAdministration: RouteOfAdministrationDto? = null
) : Serializable
