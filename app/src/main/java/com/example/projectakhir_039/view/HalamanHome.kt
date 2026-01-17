package com.example.projectakhir_039.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.projectakhir_039.R
import com.example.projectakhir_039.data.models.Product
import com.example.projectakhir_039.uicontroller.Route
import com.example.projectakhir_039.viewmodel.CartViewModel
import com.example.projectakhir_039.viewmodel.ProductViewModel
import com.example.projectakhir_039.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    navController: NavHostController,
    role: String = "pelanggan",
    userName: String = "Admin",
    productViewModel: ProductViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    // 1. Memicu pengambilan data dari database MySQL setiap kali halaman dimuat
    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }

    // 2. MENGAMATI STATE SECARA REAKTIF: UI akan otomatis berubah saat data di MySQL berubah
    val products by productViewModel.products.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val context = LocalContext.current


    val cartCount = cartItems.sumOf { it.quantity }
    var searchQuery by remember { mutableStateOf("") }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val currentUser by userViewModel.currentUser.collectAsState()
    Scaffold(
        topBar = {
            HomeHeader(
                name = userName,
                role = role,
                cartCount = cartCount,
                onCartClick = {
                    // PERBAIKAN: Menambah logika untuk Admin
                    if (role == "pelanggan") {
                        navController.navigate(Route.Cart.path)
                    } else if (role == "admin") {
                        // Admin akan diarahkan ke halaman daftar pesanan pelanggan
                        navController.navigate("halaman_admin_orders")
                    }
                },
                onLogoutClick = { showLogoutDialog = true }
            )
        },
        floatingActionButton = {
            if (role == "admin") {
                FloatingActionButton(
                    onClick = { navController.navigate(Route.Entry.path) },
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Produk")
                }
            }
        },
        bottomBar = { BottomNavBar(navController, role) }
