package com.example.projectakhir_039.data.models

import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)