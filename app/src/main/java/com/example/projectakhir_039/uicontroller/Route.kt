package com.example.projectakhir_039.uicontroller

sealed class Route(val path: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object Cart : Route("cart")
    object Detail : Route("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}