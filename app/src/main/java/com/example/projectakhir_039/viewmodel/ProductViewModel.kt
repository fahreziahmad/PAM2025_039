package com.example.projectakhir_039.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_039.R
import com.example.projectakhir_039.data.api.ApiClient
import com.example.projectakhir_039.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    // 1. State UI
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // --- FITUR UTAMA: AMBIL DATA DARI DATABASE (XAMPP) ---

    fun loadProducts() {
        loadProductsByCategory("All")
    }

    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiClient.apiService.getProducts(category)

                // Tetap menggunakan logika pemetaan gambar Anda
                val mappedProducts = response.map { product ->
                    product.copy(
                        imageResId = when {
                            product.name.contains("Adidas", true) -> R.drawable.shoes_3
                            product.name.contains("Nike", true) -> R.drawable.shoe_0_5
                            product.name.contains("Puma", true) -> R.drawable.shoes_2
                            product.name.contains("futsal", true) -> R.drawable.shoe_0_1
                            product.name.contains("Adidas", true) -> R.drawable.shoe_0_2
                            product.name.contains("Nike", true) -> R.drawable.shoes_2
                            product.name.contains("Puma", true) -> R.drawable.shoe_0_3
                            product.name.contains("futsal", true) -> R.drawable.shoes_3
                            else -> R.drawable.shoes_1
                        }
                    )
                }
                _products.value = mappedProducts
            } catch (e: Exception) {
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // --- FITUR BARU: TAMBAH KE KERANJANG (MENGHUBUNGKAN KE TOMBOL BUY NOW) ---

    // Cari fungsi addToCart di dalam ProductViewModel.kt dan ubah menjadi ini:
    fun addToCart(
        namaSepatu: String,
        harga: Double,
        ukuran: Int,
        jumlah: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Memanggil ApiClient dengan nama parameter yang sudah kita samakan di ApiService
                val response = ApiClient.apiService.addToCart(
                    nama_sepatu = namaSepatu, // Ini harus sesuai dengan di ApiService
                    harga = harga,
                    ukuran = ukuran,
                    jumlah = jumlah
                )

                if (response.status == "success") {
                    onSuccess()
                } else {
                    onError(response.message ?: "Gagal menambahkan ke keranjang")
                }
            } catch (e: Exception) {
                Log.e("ProductVM", "Error addToCart: ${e.message}")
                onError(e.message ?: "Terjadi kesalahan koneksi ke server")
            }
        }
    }

    // --- FITUR PENCARIAN (Bekerja pada list lokal) ---
    fun searchProducts(query: String) {
        if (query.isEmpty()) {
            loadProducts()
        } else {
            val currentList = _products.value
            _products.value = currentList.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
            }
        }
    }

    // --- FITUR CRUD (Create, Update, Delete) ---

    fun addProduct(product: Product, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiClient.apiService.addProduct(
                    name = product.name,
                    price = product.price,
                    description = product.description,
                    category = product.category,
                    stock = product.stock
                )

                if (response.status == "success") {
                    loadProducts()
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            } catch (e: Exception) {
                Log.e("ProductVM", "Gagal menyimpan: ${e.message}")
                onComplete(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProduct(product: Product, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiClient.apiService.updateProduct(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    description = product.description,
                    stock = product.stock,
                    category = product.category
                )
                if (response.status == "success") {
                    loadProducts()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("ProductVM", "Update Gagal: ${e.message}")
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiClient.apiService.deleteProduct(productId)
                if (response.status == "success") {
                    loadProducts()
                }
            } catch (e: Exception) {
                Log.e("ProductVM", "Hapus Gagal: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductById(id: Int): Product? {
        val foundProduct = _products.value.find { it.id == id }
        Log.d("ProductVM", "Mencari ID: $id, Hasil: ${foundProduct?.name ?: "Tidak Ditemukan"}")
        return foundProduct
    }
}