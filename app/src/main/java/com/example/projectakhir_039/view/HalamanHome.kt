package com.example.projectakhir_039.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projectakhir_039.uicontroller.Route
import com.example.projectakhir_039.viewmodel.CartViewModel
import com.example.projectakhir_039.viewmodel.ProductViewModel
import com.example.projectakhir_039.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    navController: NavHostController
) {
    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val products by productViewModel.products.collectAsState()
    val cartCount by remember { derivedStateOf { cartViewModel.getCartItemCount() } }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            HomeHeader(cartCount) { navController.navigate(Route.Cart.path) }
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                SearchBarHome(searchQuery) {
                    searchQuery = it
                    productViewModel.searchProducts(it)
                }
            }

            item { BrandCategories() }

            item { PromoBanner() }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recommendation", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("See all", color = Color(0xFFE91E63), fontSize = 12.sp)
                }
            }

            // Daftar Produk menggunakan gambar dari drawable
            items(products.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowItems.forEach { product ->
                        ProductCard(
                            product = product,
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("detail/${product.id}") },
                            onAddToCart = {
                                cartViewModel.addToCart(
                                    productId = product.id,
                                    name = product.name,
                                    price = product.price,
                                    qty = 1,
                                    img = product.imageUrl
                                )
                            }
                        )
                    }
                    if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(cartCount: Int, onCartClick: () -> Unit) {
    TopAppBar(
        title = {
            Column {
                Text("Welcome Back", fontSize = 12.sp, color = Color.Gray)
                Text("Fahrezi", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        },
        actions = {
            BadgedBox(
                badge = { if (cartCount > 0) Badge { Text(cartCount.toString()) } },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                IconButton(onClick = onCartClick) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
            }
        }
    )
}

@Composable
fun BrandCategories() {
    // List ID gambar logo brand dari folder drawable Anda
    val brandImages = listOf(
        R.drawable.cat1,
        R.drawable.cat2,
        R.drawable.cat3,
        R.drawable.cat4,
        R.drawable.cat5
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(brandImages) { imageRes ->
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    // MENGGUNAKAN GAMBAR LOGO BRAND
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        // MENAMBAHKAN GAMBAR BANNER SEBAGAI BACKGROUND
        Image(
            painter = painterResource(id = R.drawable.banner1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(20.dp).align(Alignment.CenterStart)) {
            Text("New Collection", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Discount 50% for\nfirst transaction", color = Color.LightGray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Shop Now", color = Color.Black, fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun SearchBarHome(query: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("What are you looking for?", fontSize = 14.sp) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, selected = true, onClick = {})
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, null) },
            selected = false,
            onClick = { navController.navigate(Route.Cart.path) }
        )
        NavigationBarItem(icon = { Icon(Icons.Default.FavoriteBorder, null) }, selected = false, onClick = {})
        NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, selected = false, onClick = {})
    }
}