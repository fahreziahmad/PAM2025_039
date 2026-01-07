package com.example.projectakhir_039.data.models


data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val phone: String? = null,
    val address: String? = null,
    val createdAt: String = ""
)