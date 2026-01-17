package com.example.projectakhir_039.data.models

data class Order(
    val id: Int = 0,
    val userId: Int = 0,
    val totalPrice: Double = 0.0,
    val status: String = "Pending",
    val paymentMethod: String = "COD",
    val createdAt: String = "",
    val shippingAddress: String = "",
    val items: List<CartItem> = emptyList(), // Sesuai permintaan compiler
    val orderNumber: String = ""
)