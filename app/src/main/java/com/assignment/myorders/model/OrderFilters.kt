package com.assignment.myorders.model

import java.util.Locale

/**
 * Filters local order records by optional status and free-text search.
 *
 * Search matches the order ID, pickup address, or drop address. Empty and
 * whitespace-only queries return all orders that match the selected status.
 */
object OrderFilters {
    fun filter(
        orders: List<Order>,
        status: OrderStatus? = null,
        query: String = ""
    ): List<Order> {
        val normalizedQuery = query.trim().lowercase(Locale.ROOT)

        return orders.filter { order ->
            val matchesStatus = status == null || order.status == status
            val matchesQuery = normalizedQuery.isEmpty() ||
                order.orderId.lowercase(Locale.ROOT).contains(normalizedQuery) ||
                order.pickupAddress.lowercase(Locale.ROOT).contains(normalizedQuery) ||
                order.dropAddress.lowercase(Locale.ROOT).contains(normalizedQuery)

            matchesStatus && matchesQuery
        }
    }
}
