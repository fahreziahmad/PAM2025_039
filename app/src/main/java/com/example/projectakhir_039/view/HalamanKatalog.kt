package com.example.projectakhir_039.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projectakhir_039.data.models.Product
import com.example.projectakhir_039.viewmodel.ProductViewModel
import com.example.projectakhir_039.viewmodel.CartViewModel
import androidx.compose.material3.TextFieldDefaults
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanKatalog(
    navController: NavHostController,
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    // 1. Data dari ViewModel
    val products by productViewModel.products.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()

    // 2. State UI
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var priceRange by remember { mutableStateOf(0f..500f) }
    val categories = listOf("All", "Nike", "Adidas", "Jordan", "Puma", "Vans")

    // 3. Logika Filter Terpadu (Kategori + Search + Harga)
    val filteredProducts = products.filter { product ->
        val matchesCategory = selectedCategory == "All" || product.name.contains(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isEmpty() || product.name.contains(searchQuery, ignoreCase = true)
        val matchesPrice = product.price >= priceRange.start && product.price <= priceRange.endInclusive

        matchesCategory && matchesSearch && matchesPrice
    }
    LaunchedEffect(Unit) {
        productViewModel.loadProductsByCategory("All")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Shoe Catalog", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Logika sortir */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = { BottomNavBar(navController, role = "pelanggan") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // --- SEARCH BAR MODERN (Material 3) ---
            // --- SEARCH BAR MODERN ---
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    productViewModel.searchProducts(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search your favorite shoes...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                // PERBAIKAN: Menggunakan OutlinedTextFieldDefaults.colors
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- FILTER KATEGORI ---
            Text("Categories", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = {
                            selectedCategory = category
                            // PERBAIKAN: Memanggil fungsi untuk menarik data dari MySQL
                            productViewModel.loadProductsByCategory(category)
                        },
                        label = { Text(category) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.Black,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color.Black
                        )
                    )
                }
            }
            // 3. LOGIKA TAMPILAN: Menangani kondisi loading dan data kosong
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
            } else if (products.isEmpty()) {
                // Menampilkan pesan jika tidak ada sepatu di kategori tersebut
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No shoes found", color = Color.Gray)
                }
            } else {
                // Menampilkan grid produk jika data ditemukan
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            role = "pelanggan", // Menampilkan ikon keranjang
                            onClick = { navController.navigate("detail/${product.id}") },
                            onAction = { /* Logika tambah ke keranjang */ }
                        )
                    }
                }
            }

            // --- PRICE RANGE SELECTOR ---
            Text(
                text = "Price Range: $${priceRange.start.toInt()} - $${priceRange.endInclusive.toInt()}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            RangeSlider(
                value = priceRange,
                onValueChange = { range ->
                    priceRange = range
                    // Sinkron filter harga ke ViewModel jika diperlukan
                },
                valueRange = 0f..500f,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = Color.Black,
                    activeTrackColor = Color.Black,
                    inactiveTrackColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- GRID PRODUK ---
            if (filteredProducts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No shoes found", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(
                            product = product,
                            role = "pelanggan",
                            onClick = { navController.navigate("detail/${product.id}") },
                            onAction = {
                                // Menambah ke keranjang dengan data user default atau asli
                                cartViewModel.addToCart(product.id, product.name, product.price, 1, product.imageResId, 1)
                            }
                        )
                    }
                }
            }
        }
    }
}