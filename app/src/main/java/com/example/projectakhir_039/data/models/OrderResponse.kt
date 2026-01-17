package com.example.projectakhir_039.data.models

import com.google.gson.annotations.SerializedName

// Model ini digunakan untuk menangkap respon JSON dari get_orders.php
data class OrderResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<Order> // Mengacu pada model Order yang sudah kita buat
)