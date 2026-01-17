package com.example.projectakhir_039.uicontroller

sealed class Route(val path: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home/{role}/{name}")
    object Catalog : Route("catalog")
    object Cart : Route("cart")
    object Checkout : Route("checkout")
    object History : Route("history")
    object Profile : Route("profile")
    object Tracking : Route("tracking")
    object Entry : Route("entry")

    // TAMBAHAN: Rute dengan parameter agar tidak hardcoded di MainActivity
    object Detail : Route("detail/{productId}")
    object Edit : Route("edit/{productId}")
    object OrderDetail : Route("order_detail/{orderId}")
}