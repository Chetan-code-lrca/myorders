package com.assignment.my_orders

import com.assignment.myorders.model.Order
import com.assignment.myorders.model.OrderFilters
import com.assignment.myorders.model.OrderStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class OrderFiltersTest {
    private val orders = listOf(
        Order(
            id = "1",
            vehicleType = "Four Wheeler",
            date = "05 Feb, 4:46 PM",
            orderId = "#ORD12345",
            pickupAddress = "Chetak Circle, Udaipur",
            dropAddress = "Fatehsagar Lake, Udaipur",
            amount = 229.0,
            status = OrderStatus.CANCELLED
        ),
        Order(
            id = "2",
            vehicleType = "Two Wheeler",
            date = "04 Feb, 2:30 PM",
            orderId = "#ORD12344",
            pickupAddress = "Panchwati, Udaipur",
            dropAddress = "Maharana Pratap Airport, Dabok",
            amount = 85.0,
            status = OrderStatus.COMPLETED
        ),
        Order(
            id = "3",
            vehicleType = "Four Wheeler",
            date = "03 Feb, 11:00 AM",
            orderId = "#ORD12343",
            pickupAddress = "Gulab Bagh, Udaipur",
            dropAddress = "Sajjangarh Monsoon Palace",
            amount = 560.0,
            status = OrderStatus.BOOKED_AGAIN
        )
    )

    @Test
    fun blankQueryReturnsAllOrdersInOriginalOrder() {
        assertEquals(orders, OrderFilters.filter(orders, query = "   "))
    }

    @Test
    fun statusFilterReturnsOnlyMatchingOrders() {
        assertEquals(
            listOf(orders[1]),
            OrderFilters.filter(orders, status = OrderStatus.COMPLETED)
        )
    }

    @Test
    fun searchMatchesOrderIdCaseInsensitively() {
        assertEquals(listOf(orders[0]), OrderFilters.filter(orders, query = "ord12345"))
    }

    @Test
    fun searchMatchesPickupAndDropAddresses() {
        assertEquals(listOf(orders[0]), OrderFilters.filter(orders, query = "CHETAK circle"))
        assertEquals(listOf(orders[1]), OrderFilters.filter(orders, query = "airport, dabok"))
    }

    @Test
    fun statusAndSearchFiltersCompose() {
        assertEquals(
            listOf(orders[1]),
            OrderFilters.filter(
                orders,
                status = OrderStatus.COMPLETED,
                query = "udaipur"
            )
        )
        assertEquals(
            emptyList<Order>(),
            OrderFilters.filter(
                orders,
                status = OrderStatus.CANCELLED,
                query = "airport"
            )
        )
    }
}
