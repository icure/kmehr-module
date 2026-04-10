package org.taktik.icure.entities.samv2.embed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.taktik.icure.decimal.BigDecimalWithScaleIndependentEquality
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class StandardDosage(
    val from: Long? = null,
    val to: Long? = null,
    val code: Int? = null,
    val targetGroup: String? = null,
    val kidneyFailureClass: Int? = null,
    val liverFailureClass: Int? = null,
    val treatmentDurationType: String? = null,
    val temporalityDuration: Quantity? = null,
    val temporalityUserProvided: Boolean? = null,
    val temporalityNote: SamText? = null,
    val quantity: BigDecimalWithScaleIndependentEquality? = null,
    val quantityDenominator: BigDecimalWithScaleIndependentEquality? = null,
    val quantityMultiplicator: String? = null,
    val quantityRangeLower: BigDecimalWithScaleIndependentEquality? = null,
    val quantityRangeLowerDenominator: BigDecimalWithScaleIndependentEquality? = null,
    val quantityRangeUpper: BigDecimalWithScaleIndependentEquality? = null,
    val quantityRangeUpperDenominator: BigDecimalWithScaleIndependentEquality? = null,
    val administrationFrequencyQuantity: Int? = null,
    val administrationFrequencyIsMax: Boolean? = null,
    val administrationFrequencyTimeframe: Quantity? = null,
    val maximumAdministrationQuantity: BigDecimalWithScaleIndependentEquality? = null,
    val maximumAdministrationQuantityDenominator: BigDecimalWithScaleIndependentEquality? = null,
    val maximumAdministrationQuantityMultiplicator: String? = null,
    val maximumDailyQuantity: BigDecimalWithScaleIndependentEquality? = null,
    val maximumDailyQuantityDenominator: BigDecimalWithScaleIndependentEquality? = null,
    val maximumDailyQuantityMultiplicator: String? = null,
    val textualDosage: SamText? = null,
    val supplementaryInfo: SamText? = null,
    val routeSpecification: SamText? = null,
    val indication: List<Indication>? = null,
    val parameterBounds: List<BoundedParameter>? = null,
    val routeOfAdministration: RouteOfAdministration? = null
) : Serializable
