package com.example.projectakhir_039.data.repository

import com.example.projectakhir_039.R
import com.example.projectakhir_039.data.api.ApiClient
import com.example.projectakhir_039.data.models.CartItem
import com.example.projectakhir_039.data.models.CartResponse
import com.example.projectakhir_039.data.models.LoginResponse

class CartRepository {

    // 1. Ambil daftar item keranjang dengan Mapping Gambar agar tidak crash
    suspend fun getCartItems(userId: Int): List<CartItem> {
        return try {
            val response = ApiClient.apiService.getCart(userId)

            // PERBAIKAN FATAL: Memetakan nama sepatu ke drawable agar imageResId tidak 0
            response.map { item ->
                item.copy(
                    imageResId = when {
                        item.name.contains("Adidas", true) -> R.drawable.shoes_3
                        item.name.contains("Nike", true) -> R.drawable.shoe_0_5
                        item.name.contains("Puma", true) -> R.drawable.shoes_2
                        item.name.contains("futsal", true) -> R.drawable.shoe_0_1
                        else -> R.drawable.shoes_1 // Gambar default
                    }
                )
            }
        } catch (e: Exception) {
            emptyList() // Jika gagal koneksi, kembalikan list kosong
        }
    }

    // 2. Tambah produk ke keranjang
    suspend fun addToCart(item: CartItem): CartResponse {
        return try {
            ApiClient.apiService.addToCart(
                nama_sepatu = item.name,
                harga = item.price,
                ukuran = item.ukuran, // Pastikan model CartItem sudah punya field ukuran
                jumlah = item.quantity
            )
        } catch (e: Exception) {
            CartResponse("error", "Koneksi gagal: ${e.message}")
        }
    }
