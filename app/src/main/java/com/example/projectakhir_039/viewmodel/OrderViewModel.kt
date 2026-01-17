package com.example.projectakhir_039.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_039.data.api.ApiClient
import com.example.projectakhir_039.data.models.Order
import com.example.projectakhir_039.data.models.OrderItem
import com.example.projectakhir_039.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val repository = OrderRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders
    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems: StateFlow<List<OrderItem>> = _orderItems
    private val _allOrders = MutableStateFlow<List<Order>>(emptyList())
    val allOrders: StateFlow<List<Order>> = _allOrders

    fun loadOrderItems(orderId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiClient.apiService.getOrderItems(orderId)
                if (response.status == "success") {
                    _orderItems.value = response.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAllOrdersForAdmin() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getAllOrders()
                if (response.status == "success") {
                    _allOrders.value = response.data
                }
            } catch (e: Exception) {
                Log.e("OrderVM", "Error load admin orders: ${e.message}")
            }
        }
    }

    fun loadOrders(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getOrders(userId)

            // DEBUG: Cek di Logcat (pencarian: "ORDER_DEBUG")
            println("ORDER_DEBUG: Jumlah data yang datang: ${result.size}")

            _orders.value = result
            _isLoading.value = false
        }
    }
    // Perbaikan fungsi processCheckout di OrderViewModel.kt
    fun processCheckout(userId: Int, total: Double, method: String, address: String) {
        viewModelScope.launch {
            try {
                // FIX: Tambahkan parameter 'address' di akhir pemanggilan ini
                val response = ApiClient.apiService.checkout(userId, total, method, address)

                if (response.status == "success") {
                    // Berhasil membuat pesanan, data sudah tersimpan di tabel orders
                }
            } catch (e: Exception) {
                // Tangani error koneksi atau server
            }
        }
    }

    // Di dalam OrderViewModel.kt
    // Di dalam class OrderViewModel
    fun performCheckout(
        userId: Int,
        total: Double,
        method: String,
        address: String, // TAMBAHKAN parameter ini
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // FIX ERROR BARIS 70: Kirim parameter 'address' ke apiService
                val response = ApiClient.apiService.checkout(
                    userId = userId,
                    total = total,
                    method = method,
                    address = address // Tambahkan baris ini
                )
                onResult(response.status)
            } catch (e: Exception) {
                onResult("error")
            } finally {
                _isLoading.value = false
            }
        }
    }
}