package org.taktik.icure.decimal

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.math.BigDecimal

class BigDecimalWithScaleIndependentEqualityTest : StringSpec({
    val mapper = ObjectMapper()

    "serialization should be same as BigDecimal" {
        val bd = BigDecimal("123.450")
        val wrapper = BigDecimalWithScaleIndependentEquality(bd)

        val bdJson = mapper.writeValueAsString(bd)
        val wrapperJson = mapper.writeValueAsString(wrapper)

        wrapperJson shouldBe bdJson
    }

    "equals should be scale independent" {
        val v1 = BigDecimalWithScaleIndependentEquality(BigDecimal("10.0"))
        val v2 = BigDecimalWithScaleIndependentEquality(BigDecimal("10.00"))
        val v3 = BigDecimalWithScaleIndependentEquality(BigDecimal("10.1"))

        v1 shouldBe v2
        v1.hashCode() shouldBe v2.hashCode()
        v1 shouldNotBe v3
    }

    "zeros with different scales should be equal and have same hashcode" {
        val z1 = BigDecimalWithScaleIndependentEquality(BigDecimal.ZERO)
        val z2 = BigDecimalWithScaleIndependentEquality(BigDecimal("0.0000"))
        val z3 = BigDecimalWithScaleIndependentEquality(BigDecimal("0.0"))

        z1 shouldBe z2
        z1.hashCode() shouldBe z2.hashCode()

        z2 shouldBe z3
        z2.hashCode() shouldBe z3.hashCode()

        z1 shouldBe z3
        z1.hashCode() shouldBe z3.hashCode()
    }

    "inequality tests" {
        val v1 = BigDecimalWithScaleIndependentEquality(BigDecimal("10.000001"))
        val v2 = BigDecimalWithScaleIndependentEquality(BigDecimal("10.000002"))
        val v3 = BigDecimalWithScaleIndependentEquality(BigDecimal("10.1"))

        v1 shouldNotBe v2
        v2 shouldNotBe v3
    }

    "check equality and hashcode consistency for various scaled values" {
        listOf(
            "1" to "1.0",
            "1.00" to "1.0000",
            "0" to "0.0",
            "-1.23" to "-1.2300",
            "50.0" to "5E1"
        ).forEach { (s1, s2) ->
            val v1 = BigDecimalWithScaleIndependentEquality(BigDecimal(s1))
            val v2 = BigDecimalWithScaleIndependentEquality(BigDecimal(s2))

            v1 shouldBe v2
            v1.hashCode() shouldBe v2.hashCode()
        }
    }
})
