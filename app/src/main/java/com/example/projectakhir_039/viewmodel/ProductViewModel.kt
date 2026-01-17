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
