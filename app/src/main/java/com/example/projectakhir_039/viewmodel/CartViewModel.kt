package com.example.projectakhir_039.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_039.data.models.CartItem
import com.example.projectakhir_039.data.repository.CartRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val repository = CartRepository()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    // State untuk memantau apakah sedang loading atau ada error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val totalPrice: StateFlow<Double> = _cartItems
        .map { items -> items.sumOf { it.price * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val cartItemCount: StateFlow<Int> = _cartItems
        .map { items -> items.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // READ: Memuat data dengan penanganan error agar tidak "mentall"
    fun loadCart(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Pastikan repository.getCartItems sudah menggunakan try-catch di dalamnya
                val items = repository.getCartItems(userId)
                _cartItems.value = items
            } catch (e: Exception) {
                Log.e("CartVM", "Gagal memuat keranjang: ${e.message}")
                _cartItems.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // UPDATE: Menggunakan cartId agar sinkron dengan database phpMyAdmin
    fun updateQuantity(cartId: Int, delta: Int, userId: Int = 1) {
        viewModelScope.launch {
            try {
                val currentList = _cartItems.value.toMutableList()
                val itemIndex = currentList.indexOfFirst { it.id == cartId }

                if (itemIndex != -1) {
                    val item = currentList[itemIndex]
                    val newQty = item.quantity + delta

                    if (newQty > 0) {
                        val updatedItem = item.copy(quantity = newQty)
                        currentList[itemIndex] = updatedItem
                        repository.updateCartItem(updatedItem)
                    } else {
                        repository.removeFromCart(item.id)
                        currentList.removeAt(itemIndex)
                    }
                    _cartItems.value = currentList
                }
            } catch (e: Exception) {
                Log.e("CartVM", "Gagal update jumlah: ${e.message}")
            }
        }
    }
