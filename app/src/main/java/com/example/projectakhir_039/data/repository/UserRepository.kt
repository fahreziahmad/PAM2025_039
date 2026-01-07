package com.example.projectakhir_039.data.repository

import com.example.projectakhir_039.data.models.User

class UserRepository {
    // Tambahkan ini agar AuthViewModel tidak error
    suspend fun registerUser(user: User): Boolean {
        // Implementasi koneksi API nanti di sini
        return true
    }
}