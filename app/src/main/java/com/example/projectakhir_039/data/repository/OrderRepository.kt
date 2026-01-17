package com.example.projectakhir_039.data.repository

import com.example.projectakhir_039.data.api.ApiClient
import com.example.projectakhir_039.data.models.LoginResponse
import com.example.projectakhir_039.data.models.Order

class OrderRepository {
    // Di dalam class OrderRepository
    suspend fun getOrders(userId: Int): List<Order> {
        return try {
            ApiClient.apiService.getOrderHistory(userId)
        } catch (e: Exception) {
            emptyList()
        }
    }
    // Tambahkan parameter 'address: String' pada definisi fungsi
    suspend fun performCheckout(userId: Int, total: Double, method: String, address: String): LoginResponse {
        return try {
            // FIX: Masukkan variabel 'address' ke dalam pemanggilan apiService.checkout
            ApiClient.apiService.checkout(userId, total, method, address)
        } catch (e: Exception) {
            LoginResponse("error", "Koneksi Gagal: ${e.message}")
        }
    }
}