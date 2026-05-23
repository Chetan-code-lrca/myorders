package com.assignment.myorders.model

data class Order(
    val id: String,
    val vehicleType: String,
    val date: String,
    val orderId: String,
    val pickupAddress: String,
    val dropAddress: String,
    val amount: Double,
    val status: OrderStatus
)