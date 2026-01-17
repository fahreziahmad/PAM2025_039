package com.example.projectakhir_039.data.api

import com.example.projectakhir_039.data.models.*
import retrofit2.http.*

interface ApiService {

    // --- FITUR PRODUK ---
    @GET("products.php")
    suspend fun getProducts(): List<Product>

    @GET("products.php")
    suspend fun getProducts(
        @Query("category") category: String
    ): List<Product>

    @GET("search_products.php")
    suspend fun searchProducts(
        @Query("query") query: String
    ): List<Product>

    // --- FITUR AUTENTIKASI ---
    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginUser(
        @Field("name") name: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register.php")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("role") role: String
    ): LoginResponse

    // --- FITUR KERANJANG (CART) ---
    @GET("get_cart.php")
    suspend fun getCart(
        @Query("user_id") userId: Int
    ): List<CartItem>

    // FIX: Mengubah 'tambahKeKeranjang' menjadi 'addToCart'
    // agar sesuai dengan panggilan di ProductViewModel
    @FormUrlEncoded
    @POST("add_to_cart.php")
    suspend fun addToCart(
        @Field("nama_sepatu") nama_sepatu: String,
        @Field("harga") harga: Double,
        @Field("ukuran") ukuran: Int,
        @Field("jumlah") jumlah: Int
    ): CartResponse

    @FormUrlEncoded
    @POST("update_cart.php")
    suspend fun updateCart(
        @Field("id") cartId: Int,
        @Field("quantity") quantity: Int
    ): LoginResponse

    @FormUrlEncoded
    @POST("delete_cart.php")
    suspend fun deleteCart(
        @Field("id") cartId: Int
    ): LoginResponse

    // --- FITUR PESANAN & RIWAYAT ---
    @FormUrlEncoded
    @POST("checkout.php")
    suspend fun checkout(
        @Field("user_id") userId: Int,
        @Field("total") total: Double,
        @Field("method") method: String,
        @Field("address") address: String // TAMBAHKAN INI
    ): LoginResponse

    @GET("get_orders.php")
    suspend fun getOrders(
        @Query("user_id") userId: Int
    ): OrderResponse

    @GET("get_order_items.php")
    suspend fun getOrderItems(
        @Query("order_id") orderId: Int
    ): OrderItemResponse

    @GET("get_history.php")
    suspend fun getOrderHistory(
        @Query("user_id") userId: Int
    ): List<Order>

    // --- FITUR PRODUK (ADMIN) ---
    @FormUrlEncoded
    @POST("add_product.php")
    suspend fun addProduct(
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("category") category: String,
        @Field("stock") stock: Int
    ): LoginResponse

    @FormUrlEncoded
    @POST("update_product.php")
    suspend fun updateProduct(
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("stock") stock: Int,
        @Field("category") category: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("delete_product.php")
    suspend fun deleteProduct(
        @Field("id") id: Int
    ): LoginResponse

    @FormUrlEncoded
    @POST("save_product.php")
    suspend fun saveProduct(
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("category") category: String,
        @Field("stock") stock: Int,
        @Field("rating") rating: Double,
        @Field("image_url") imageUrl: String
    ): LoginResponse
    // Di dalam interface ApiService.kt
    @GET("get_all_orders.php")
    suspend fun getAllOrders(): OrderResponse // Menggunakan model OrderResponse yang sudah Anda punya
}