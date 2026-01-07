package com.example.projectakhir_039

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir_039.ui.theme.ProjectAkhir_039Theme
import com.example.projectakhir_039.uicontroller.PetalNavigasi
import com.example.projectakhir_039.uicontroller.Route
import com.example.projectakhir_039.view.* // Mengimpor semua halaman dari folder view

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAkhir_039Theme {
                val navController = rememberNavController()
                val petalNav = PetalNavigasi(navController)

                NavHost(
                    navController = navController,
                    startDestination = Route.Login.path
                ) {
                    // 1. Halaman Login
                    composable(Route.Login.path) {
                        HalamanLogin(petalNav = petalNav)
                    }

                    // 2. Halaman Home
                    composable(Route.Home.path) {
                        HalamanHome(navController = navController)
                    }

                    // 3. Halaman Cart (Memperbaiki crash saat klik keranjang)
                    composable(Route.Cart.path) {
                        HalamanCart(onBack = { navController.popBackStack() })
                    }

                    // 4. Halaman Detail (Wajib ada agar klik produk tidak crash)
                    composable(
                        route = "detail/{productId}",
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("productId") ?: 0
                        HalamanDetail(productId = id, navController = navController)
                    }

                    // 5. Halaman Edit
                    composable(
                        route = "edit/{productId}",
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("productId") ?: 0
                        HalamanEdit(productId = id, navController = navController)
                    }

                    // 6. Halaman Entry (Tambah Produk)
                    composable("entry") {
                        HalamanEntry(
                            onBackClick = { navController.popBackStack() },
                            onSaveSuccess = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}