package com.example.projectakhir_039.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projectakhir_039.R
import com.example.projectakhir_039.data.models.formatRupiah
import com.example.projectakhir_039.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun HalamanDetail(
    productId: Int,
    navController: NavController,
    productViewModel: ProductViewModel = viewModel()
) {
    // 1. Amati data produk dan status loading dari ViewModel
    val product = productViewModel.getProductById(productId)
    val isLoading by productViewModel.isLoading.collectAsState()

    // State untuk interaksi UI
    var quantity by remember { mutableIntStateOf(1) }
    var selectedSize by remember { mutableIntStateOf(40) }

    // State untuk proses pengiriman data
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // 2. Memicu pengambilan data jika memori lokal masih kosong
    LaunchedEffect(productId) {
        if (product == null) {
            productViewModel.loadProducts()
        }
    }

    // 3. LOGIKA HANDLING TAMPILAN
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading && product == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFFD81B60)
            )
        } else if (!isLoading && product == null) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sepatu tidak ditemukan!", color = Color.Gray, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Kembali ke Katalog")
                }
            }
        } else if (product != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // --- Bagian Atas: Gambar & Sidebar ---
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.2f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 60.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFFD81B60), Color(0xFF42A5F5))
                                ),
                                shape = RoundedCornerShape(24.dp)
                            )
                    )

                    AsyncImage(
                        model = if (!product.image_url.isNullOrEmpty()) product.image_url else product.imageResId,
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 30.dp)
                            .scale(0.9f),
                        contentScale = ContentScale.Fit,
                        error = painterResource(id = R.drawable.shoes_1)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.background(Color.White.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back", modifier = Modifier.scale(0.7f))
                        }
                        IconButton(
                            onClick = { /* Wishlist */ },
                            modifier = Modifier.background(Color.White.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favorite")
                        }
                    }

                    Column(
                        modifier = Modifier.align(Alignment.CenterStart).padding(start = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf(38, 39, 40, 41, 42).forEach { size ->
                            SizeItem(size = size, isSelected = selectedSize == size) { selectedSize = size }
                        }
                    }

                    Column(
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end = 5.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf(Color.Blue, Color.Red, Color.DarkGray, Color.Cyan).forEach { color ->
                            ColorCircle(color = color)
                        }
                    }
                }

                // --- Bagian Bawah: Informasi Produk Dinamis ---
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = formatRupiah(product.price),
                        color = Color(0xFFD81B60),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text("Total Price", color = Color.Gray, fontSize = 14.sp)
                    Text(
                        text = formatRupiah(product.price * quantity),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                        IconButton(onClick = { if (quantity > 1) quantity-- }) {
                            Text("-", fontSize = 28.sp, color = Color.Gray)
                        }
                        Text(text = quantity.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        IconButton(onClick = { quantity++ }) {
                            Text("+", fontSize = 24.sp, color = Color(0xFF42A5F5))
                        }
                    }

                    Text(text = product.description ?: "No description available.", textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(top = 10.dp), maxLines = 4)

                    Spacer(modifier = Modifier.weight(1f))

                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("Total Price", color = Color.Gray, fontSize = 14.sp)
                            Text(text = formatRupiah(product.price * quantity), fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        }

                        // --- TOMBOL BUY NOW DENGAN LOGIKA DATABASE ---
                        Button(
                            onClick = {
                                scope.launch {
                                    try {
                                        // Memanggil fungsi addToCart dari ViewModel
                                        productViewModel.addToCart(
                                            namaSepatu = product.name,
                                            harga = product.price,
                                            ukuran = selectedSize,
                                            jumlah = quantity,
                                            onSuccess = {
                                                Toast.makeText(context, "Berhasil ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
                                                navController.navigate("halaman_cart")
                                            },
                                            onError = { error ->
                                                Toast.makeText(context, "Gagal: $error", Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error Koneksi: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            modifier = Modifier.height(55.dp).width(180.dp).clip(RoundedCornerShape(25.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues()
                        ) {
                            Box(modifier = Modifier.fillMaxSize().background(brush = Brush.horizontalGradient(colors = listOf(Color(0xFFD81B60), Color(0xFF42A5F5)))), contentAlignment = Alignment.Center) {
                                Text("Buy Now", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
