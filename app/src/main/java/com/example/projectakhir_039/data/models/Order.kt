package com.example.projectakhir_039.data.models


data class Order(
    val id: Int,
    val userId: Int,
    val orderNumber: String,
    val totalPrice: Double,
    val status: String = "pending", // pending, processed, shipped, completed
    val shippingAddress: String,
    val paymentMethod: String, // COD, Transfer
    val items: List<CartItem>,
    val createdAt: String = ""
)