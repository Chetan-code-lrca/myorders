package com.assignment.myorders.model

import java.text.NumberFormat
import java.util.Locale

/**
 * Formats order amounts as Indian rupees without showing unnecessary trailing
 * decimal zeroes. A new formatter is created per call because NumberFormat is
 * not thread-safe.
 */
object OrderFormatters {
    fun formatAmount(amount: Double): String {
        require(amount.isFinite() && amount >= 0.0) {
            "Order amount must be a finite, non-negative value."
        }

        val formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 2
        }
        return formatter.format(amount)
    }
}
