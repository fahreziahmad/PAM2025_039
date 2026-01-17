package com.example.projectakhir_039.data.models


import com.google.gson.annotations.SerializedName

// Model untuk menangkap balasan dari get_order_items.php
data class OrderItemResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<OrderItem>
)