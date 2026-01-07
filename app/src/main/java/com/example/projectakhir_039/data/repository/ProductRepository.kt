package com.example.projectakhir_039.data.repository

import com.example.projectakhir_039.data.api.ApiClient
import com.example.projectakhir_039.data.models.Product

class ProductRepository {
    private val api = ApiClient.instance
    private var localProducts: List<Product> = emptyList()

    // 1. Memperbaiki error 'getProducts'
    suspend fun getProducts(): List<Product> {
        return try {
            val response = api.getProducts()
            if (response.isSuccessful) {
                localProducts = response.body() ?: emptyList()
                localProducts
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // 2. Memperbaiki error 'searchProducts'
    fun searchProducts(query: String): List<Product> {
        return localProducts.filter { it.name.contains(query, ignoreCase = true) }
    }

    // 3. Memperbaiki error 'getProductById'
    fun getProductById(id: Int): Product? {
        return localProducts.find { it.id == id }
    }

    // 4. Memperbaiki error 'addProduct' (Untuk HalamanEntry)
    suspend fun addProduct(product: Product): Boolean {
        // Logika kirim ke API XAMPP bisa ditambahkan di sini
        return true
    }

    // 5. Memperbaiki error 'updateProduct' (Untuk HalamanEdit)
    suspend fun updateProduct(product: Product): Boolean {
        // Logika update ke API XAMPP bisa ditambahkan di sini
        return true
    }
}