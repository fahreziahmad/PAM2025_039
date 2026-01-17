package com.example.projectakhir_039.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projectakhir_039.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEdit(
    productId: Int,
    navController: NavHostController,
    productViewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current

    // 1. Amati status loading global dari ViewModel
    val isLoading by productViewModel.isLoading.collectAsState()

    // 2. Ambil data produk berdasarkan ID
    val product = productViewModel.getProductById(productId)

    // Memicu refresh jika data belum ada (misal: aplikasi dibuka langsung di halaman ini)
    LaunchedEffect(product) {
        if (product == null) {
            productViewModel.loadProducts()
        }
    }

    // State form tetap menggunakan remember(product) agar sinkron
    var name by remember(product) { mutableStateOf(product?.name ?: "") }
    var price by remember(product) { mutableStateOf(product?.price?.toString() ?: "") }
    var description by remember(product) { mutableStateOf(product?.description ?: "") }
    var stock by remember(product) { mutableStateOf(product?.stock?.toString() ?: "0") }
    var category by remember(product) { mutableStateOf(product?.category ?: "") }

    var showDeleteDialog by remember { mutableStateOf(false) }

    // 3. LOGIKA HANDLING LOADING: Mencegah stuck loading
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading && product == null) {
            // Sedang mengambil data dari MySQL
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (!isLoading && product == null) {
            // Selesai loading tapi data TIDAK DITEMUKAN
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Produk tidak ditemukan atau koneksi gagal", color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Kembali ke Home")
                }
            }
        } else {
            // Tampilkan konten Form jika produk ditemukan
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Edit & Manage Product", fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        },
                        actions = {
                            IconButton(onClick = { showDeleteDialog = true }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Informasi Sepatu", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nama Sepatu") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("Harga ($)") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = stock,
                            onValueChange = { stock = it },
                            label = { Text("Stok") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Deskripsi") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            val validatedPrice = price.toDoubleOrNull()
                            val validatedStock = stock.toIntOrNull()

                            if (validatedPrice != null && validatedStock != null && product != null) {
                                val updatedProduct = product.copy(
                                    name = name,
                                    price = validatedPrice,
                                    description = description,
                                    stock = validatedStock,
                                    category = category
                                )

                                productViewModel.updateProduct(updatedProduct) { isSuccess ->
                                    if (isSuccess) {
                                        Toast.makeText(context, "Update Berhasil!", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    } else {
                                        Toast.makeText(context, "Update Gagal!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Input tidak valid!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                    ) {
                        Text("Simpan Perubahan", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // Dialog Konfirmasi Hapus
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Produk?") },
            text = { Text("Apakah Anda yakin ingin menghapus '${product?.name}'?") },
            confirmButton = {
                Button(
                    onClick = {
                        productViewModel.deleteProduct(productId)
                        showDeleteDialog = false
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Hapus", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") }
            }
        )
    }
}