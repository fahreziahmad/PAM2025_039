package com.example.projectakhir_039.data.repository

import com.example.projectakhir_039.data.api.ApiClient
import com.example.projectakhir_039.data.models.Product

class ProductRepository {
    // Pastikan memanggil ApiClient.apiService secara langsung
    private val apiService = ApiClient.apiService

    suspend fun getProducts(): List<Product> {
        return try {
            apiService.getProducts()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Jika error (server mati/RTO), kirim list kosong agar app tidak crash
        }
    }

    suspend fun searchProducts(query: String): List<Product> {
        return try {
            apiService.searchProducts(query)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}