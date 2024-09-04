package org.taktik.icure.entities.samv2.embed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class StandardDosage(
    val code: Int? = null,
    val targetGroup: String? = null,
    val kidneyFailureClass: Int? = null,
    val liverFailureClass: Int? = null,
    val treatmentDurationType: String? = null,
    val temporalityDuration: Quantity? = null,
    val temporalityUserProvided: Boolean? = null,
    val temporalityNote: SamText? = null,
    val quantity: BigDecimal? = null,
    val quantityDenominator: BigDecimal? = null,
    val quantityMultiplicator: String? = null,
    val quantityRangeLower: BigDecimal? = null,
    val quantityRangeLowerDenominator: BigDecimal? = null,
    val quantityRangeUpper: BigDecimal? = null,
    val quantityRangeUpperDenominator: BigDecimal? = null,
    val administrationFrequencyQuantity: Int? = null,
    val administrationFrequencyIsMax: Boolean? = null,
    val administrationFrequencyTimeframe: Quantity? = null,
    val maximumAdministrationQuantity: BigDecimal? = null,
    val maximumAdministrationQuantityDenominator: BigDecimal? = null,
    val maximumAdministrationQuantityMultiplicator: String? = null,
    val maximumDailyQuantity: BigDecimal? = null,
    val maximumDailyQuantityDenominator: BigDecimal? = null,
    val maximumDailyQuantityMultiplicator: String? = null,
    val textualDosage: SamText? = null,
    val supplementaryInfo: SamText? = null,
    val routeSpecification: SamText? = null,
    val indication: List<Indication>? = null,
    val parameterBounds: List<BoundedParameter>? = null,
    val routeOfAdministration: RouteOfAdministration? = null
) : Serializable
