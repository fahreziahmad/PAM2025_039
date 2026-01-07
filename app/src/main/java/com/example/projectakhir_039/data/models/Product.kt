package com.example.projectakhir_039.data.models

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val imageResId: Int,
    val stock: Int = 0,
    val description: String = "",
    val rating: Double = 0.0,
    val category: String = "", // Pastikan ini ada
    val imageUrl: String = ""
)