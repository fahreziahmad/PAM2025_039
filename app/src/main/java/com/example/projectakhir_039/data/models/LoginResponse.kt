package com.example.projectakhir_039.data.models

data class LoginResponse(
    val status: String,
    val message: String,
    val role: String? = null
)