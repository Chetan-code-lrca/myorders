package com.assignment.myorders.model

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Formats order amounts as Indian rupees with deterministic lakh/crore grouping.
 */
object OrderFormatters {
    fun formatAmount(amount: Double): String {
        require(amount.isFinite() && amount >= 0.0) {
            "Order amount must be a finite, non-negative value."
        }

        val normalized = BigDecimal.valueOf(amount)
            .setScale(2, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()

        val parts = normalized.split('.', limit = 2)
        val integerPart = parts[0]
        val fractionPart = parts.getOrNull(1)
        val groupedInteger = groupIndianDigits(integerPart)
        val decimalPart = fractionPart?.takeIf { it.isNotEmpty() }?.let { ".$it" }.orEmpty()

        return "₹$groupedInteger$decimalPart"
    }

    private fun groupIndianDigits(value: String): String {
        val negative = value.startsWith("-")
        val digits = value.removePrefix("-")
        if (digits.length <= 3) return value

        val lastThree = digits.takeLast(3)
        var remaining = digits.dropLast(3)
        val groups = mutableListOf<String>()
        while (remaining.length > 2) {
            groups.add(0, remaining.takeLast(2))
            remaining = remaining.dropLast(2)
        }
        if (remaining.isNotEmpty()) groups.add(0, remaining)

        val grouped = (groups + lastThree).joinToString(",")
        return if (negative) "-$grouped" else grouped
    }
}
