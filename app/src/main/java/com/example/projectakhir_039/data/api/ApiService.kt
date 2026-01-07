package com.example.projectakhir_039.data.api

import com.example.projectakhir_039.data.models.* import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Di dalam interface ApiService
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>
    // Auth Endpoints
    @POST("login.php")
    suspend fun login(@Body credentials: Map<String, String>): Response<User>

    @POST("register.php")
    suspend fun register(@Body user: User): Response<User>

    // Product Endpoints
    @GET("products.php")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products.php")
    suspend fun getProductById(@Query("id") id: Int): Response<Product>

    // Cart Endpoints
    @GET("cart.php")
    suspend fun getCart(@Query("userId") userId: Int): Response<List<CartItem>>

    @POST("cart.php")
    suspend fun addToCart(@Body cartItem: CartItem): Response<CartItem>
}