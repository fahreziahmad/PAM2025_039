package com.example.projectakhir_039
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.projectakhir_039.ui.theme.ProjectAkhir_039Theme
import com.example.projectakhir_039.uicontroller.Route
import com.example.projectakhir_039.uicontroller.rememberPetalNavigasi
import com.example.projectakhir_039.view.*
import com.example.projectakhir_039.viewmodel.AuthViewModel
import com.example.projectakhir_039.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_039.viewmodel.CartViewModel
import com.example.projectakhir_039.viewmodel.ProductViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAkhir_039Theme {
                val navController = rememberNavController()
                // Shared UserViewModel agar data user yang login bisa diakses di Profil
                val userViewModel: UserViewModel = viewModel()
                // 1. INISIALISASI DI SINI agar bisa dipakai oleh semua rute
                val productViewModel: ProductViewModel = viewModel()
                val cartViewModel: CartViewModel = viewModel() // Buat satu kali di sini

                NavHost(
                    navController = navController,
                    startDestination = Route.Login.path
                ) {
                    // 1. Rute Login
                    composable(Route.Login.path) {
                        val authVM: AuthViewModel = viewModel()
                        HalamanLogin(
                            petalNav = rememberPetalNavigasi(navController),
                            authVM = authVM,
                            onSuccess = { user -> // PERBAIKAN: Terima objek 'user' lengkap dari HalamanLogin
                                // Simpan data user (termasuk email) ke ViewModel pusat agar bisa dibaca di Profil
                                userViewModel.updateUserData(user)

                                // Navigasi ke Home menggunakan data dari objek user tersebut
                                navController.navigate("home/${user.role}/${user.name}") {
                                    popUpTo(Route.Login.path) { inclusive = true }
                                }
                            },
                            onRegisterClick = { navController.navigate(Route.Register.path) }
                        )
                    }

                    // 2. Rute Register - DISESUAIKAN PARAMETERNYA
                    composable(Route.Register.path) {
                        HalamanRegister(
                            onNavigateToLogin = { navController.popBackStack() }
                        )
                    }
                    // MainActivity.kt
                    composable(
                        route = "detail/{productId}",
                        arguments = listOf(navArgument("productId") { type = NavType.IntType }) // Pastikan tipe data INT
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0

                        HalamanDetail(
                            productId = productId,
                            navController = navController,
                            productViewModel = productViewModel // Gunakan ViewModel yang sama agar data sinkron
                        )
                    }

                    // 3. Rute Home
                    composable(
                        route = "home/{role}/{name}",
                        arguments = listOf(
                            navArgument("role") { type = NavType.StringType },
                            navArgument("name") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val role = backStackEntry.arguments?.getString("role") ?: "pelanggan"
                        val name = backStackEntry.arguments?.getString("name") ?: "User"
                        HalamanHome(navController = navController, role = role, userName = name)
                    }

                    // 4. Rute Katalog
                    composable("katalog") {
                        HalamanKatalog(navController = navController, productViewModel = productViewModel)
                    }

                    // 5. Rute Profil - TAMBAHAN BARU
                    composable(Route.Profile.path) {
                        HalamanProfil(
                            onBack = { navController.popBackStack() },
                            userVM = userViewModel
                        )
                    }
                    // MainActivity.kt
                    composable("home/{role}") { backStackEntry ->
                        val role = backStackEntry.arguments?.getString("role") ?: "pelanggan"
                        HalamanHome(
                            navController = navController,
                            role = role, // Mengirimkan peran ke HalamanHome
                            productViewModel = productViewModel
                        )
                    }

                    // 6. Rute Cart
                    composable(Route.Cart.path) {
                        HalamanCart(onBack = { navController.popBackStack() })
                    }

                    // 7. Rute Tracking
                    composable(Route.Tracking.path) {
                        HalamanLacakPesanan(navController = navController)
                    }

                    // 8. Rute Entry (Tambah Produk)
                    composable(Route.Entry.path) {
                        HalamanEntry(
                            navController = navController,
                            // PERBAIKAN: Tambahkan parameter onBackClick yang diminta
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onSaveSuccess = {
                                // Setelah berhasil simpan, kembali ke halaman sebelumnya
                                navController.popBackStack()
                            },
                            viewModel = productViewModel
                        )
                    }

                    // 9. Rute Detail Produk
                    composable(
                        route = "detail/{productId}",
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        HalamanDetail(productId = productId, navController = navController)
                    }

                    // 10. Rute Edit Produk
                    composable(
                        route = "edit/{productId}",
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        HalamanEdit(productId = productId, navController = navController)
                    }
                    composable("halaman_admin_orders") {
                        HalamanAdminOrders(
                            onBack = { navController.popBackStack() }
                        )
                    }


                }
            }
        }
    }
}