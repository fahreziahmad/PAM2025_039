package com.example.projectakhir_039.data.repository

import com.example.projectakhir_039.data.api.ApiClient
import com.example.projectakhir_039.data.models.User

class UserRepository {
    private val apiService = ApiClient.apiService

    suspend fun registerUser(user: User): Boolean {
        return try {
            // PERBAIKAN: Menambahkan parameter 'role' agar sinkron dengan register.php
            val response = apiService.registerUser(
                name = user.name,
                email = user.email,
                password = user.password,
                phone = user.phone ?: "",
                role = user.role // KIRIM DATA ROLE DI SINI
            )
            response.status == "success"
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}