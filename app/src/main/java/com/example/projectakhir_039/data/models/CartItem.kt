package com.example.projectakhir_039.data.models

data class CartItem(
    val id: Int,
    val productId: Int,
    val name: String, // Pastikan ini 'name'
    val price: Double,
    val quantity: Int,
    val imageResId: Int,
    val userId: Int
)