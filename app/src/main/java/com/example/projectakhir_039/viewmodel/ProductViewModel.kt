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

