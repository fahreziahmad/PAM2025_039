package com.example.projectakhir_039.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
// PENTING: Jangan hapus import di bawah ini agar 'by' tidak error
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projectakhir_039.data.models.Product
import com.example.projectakhir_039.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEdit(
    productId: Int,
    navController: NavHostController,
    productViewModel: ProductViewModel = viewModel()
) {
    // Mencari data produk berdasarkan ID yang dikirim lewat navigasi
    val product = productViewModel.getProductById(productId)

    // State untuk menampung inputan user
    var name by remember { mutableStateOf(product?.name ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var stock by remember { mutableStateOf(product?.stock?.toString() ?: "0") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Product") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Input Nama
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input Harga
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price ($)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            // Input Stok
            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Input Deskripsi
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Simpan Perubahan
            Button(
                onClick = {
                    // Membuat objek produk yang sudah diperbarui
                    val updatedProduct = product?.copy(
                        name = name,
                        price = price.toDoubleOrNull() ?: 0.0,
                        description = description,
                        stock = stock.toIntOrNull() ?: 0
                    )

                    if (updatedProduct != null) {
                        // Memanggil fungsi update di ViewModel
                        productViewModel.updateProduct(updatedProduct)
                        // Kembali ke halaman sebelumnya setelah berhasil
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Update Product")
            }
        }
    }
}