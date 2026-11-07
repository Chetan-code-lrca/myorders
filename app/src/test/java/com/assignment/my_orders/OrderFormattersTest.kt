package com.assignment.my_orders

import com.assignment.myorders.model.OrderFormatters
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OrderFormattersTest {
    @Test
    fun formatsWholeRupeeAmountsWithoutDecimalZeros() {
        val result = OrderFormatters.formatAmount(229.0)

        assertTrue(result.contains("₹"))
        assertFalse(result.endsWith(".0"))
        assertFalse(result.endsWith(".00"))
    }

    @Test
    fun preservesMeaningfulFractionalRupees() {
        val result = OrderFormatters.formatAmount(229.5)

        assertTrue(result.contains("₹"))
        assertTrue(result.contains("229.5"))
    }

    @Test
    fun supportsIndianDigitGrouping() {
        val result = OrderFormatters.formatAmount(150000.0)

        assertTrue(result.contains("₹"))
        assertTrue(result.contains("1,50,000"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsNegativeAmounts() {
        OrderFormatters.formatAmount(-1.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsNonFiniteAmounts() {
        OrderFormatters.formatAmount(Double.NaN)
    }
}
