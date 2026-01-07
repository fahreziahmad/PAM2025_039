package com.example.projectakhir_039.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_039.R
import com.example.projectakhir_039.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow(listOf(
        Product(id = 1, name = "Sport Shoes", price = 250.0, imageResId = R.drawable.shoes_1, stock = 10, description = "Shoes for running", rating = 4.6, category = "Running"),
        Product(id = 2, name = "Nike Air Max", price = 250.0, imageResId = R.drawable.shoes_2, stock = 5, description = "Comfortable shoes", rating = 4.8, category = "Casual")
    ))
    val products: StateFlow<List<Product>> = _products

    fun getProductById(id: Int): Product? = _products.value.find { it.id == id }

    fun addProduct(product: Product) {
        viewModelScope.launch { _products.value = _products.value + product }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            _products.value = _products.value.map { if (it.id == product.id) product else it }
        }
    }

    fun searchProducts(query: String) {
        // Implementasi filter jika diperlukan
    }
}