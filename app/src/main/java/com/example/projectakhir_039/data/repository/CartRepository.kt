package com.example.projectakhir_039.data.repository

import com.example.projectakhir_039.data.models.CartItem // Perbaikan import

class CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    fun getCartItems(userId: Int): List<CartItem> = cartItems.filter { it.userId == userId }

    fun addToCart(cartItem: CartItem) {
        val existing = cartItems.find { it.productId == cartItem.productId }
        if (existing != null) {
            val index = cartItems.indexOf(existing)
            cartItems[index] = existing.copy(quantity = existing.quantity + cartItem.quantity)
        } else {
            cartItems.add(cartItem.copy(id = cartItems.size + 1))
        }
    }

    fun removeFromCart(itemId: Int) { cartItems.removeIf { it.id == itemId } }
    fun getCartTotal(userId: Int): Double = cartItems.sumOf { it.price * it.quantity }
}