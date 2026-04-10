package org.taktik.icure.decimal

import com.fasterxml.jackson.annotation.JsonValue
import java.math.BigDecimal

class BigDecimalWithScaleIndependentEquality(@JsonValue val value: BigDecimal): Comparable<BigDecimalWithScaleIndependentEquality> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BigDecimalWithScaleIndependentEquality) return false

        return value.compareTo(other.value) == 0
    }

    override fun hashCode(): Int {
        return value.toInt()
    }

    override fun compareTo(other: BigDecimalWithScaleIndependentEquality) = this.value.compareTo(other.value)
    fun toInt() = value.toInt()
}
