package com.example.projectakhir_039.data.models


data class CartItem(
    val id: Int,
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val userId: Int,
    val selectedSize: String = "40"
)