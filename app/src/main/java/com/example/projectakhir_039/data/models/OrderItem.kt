package com.example.projectakhir_039.data.models

import com.google.gson.annotations.SerializedName

data class OrderItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val price: Double
)