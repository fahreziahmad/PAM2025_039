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
