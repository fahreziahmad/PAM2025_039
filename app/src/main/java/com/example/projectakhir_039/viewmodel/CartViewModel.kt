package com.example.projectakhir_039.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projectakhir_039.data.models.CartItem
import com.example.projectakhir_039.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val repository = CartRepository()
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun loadCart(userId: Int) { _cartItems.value = repository.getCartItems(userId) }

    fun addToCart(productId: Int, name: String, price: Double, qty: Int, img: String) {
        repository.addToCart(CartItem(0, productId, name, price, qty, img, 1))
        loadCart(1)
    }

    fun getCartItemCount(): Int = _cartItems.value.sumOf { it.quantity }
    fun getTotalPrice(): Double = repository.getCartTotal(1)
}