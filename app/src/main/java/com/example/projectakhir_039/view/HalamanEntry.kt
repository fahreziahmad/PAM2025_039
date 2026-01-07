package com.example.projectakhir_039.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_039.R
import com.example.projectakhir_039.data.models.Product
import com.example.projectakhir_039.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntry(
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    // State untuk input form
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Produk Baru") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
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
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Sepatu") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Harga ($)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Kategori") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stok") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Deskripsi") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Membuat objek produk baru sesuai dengan model Product.kt terbaru
                    val newProduct = Product(
                        id = 0,
                        name = name,
                        price = price.toDoubleOrNull() ?: 0.0,
                        // Memberikan gambar default dari drawable karena ini produk baru
                        imageResId = R.drawable.shoes_1,
                        stock = stock.toIntOrNull() ?: 0,
                        description = description,
                        rating = 0.0, // Produk baru mulai dengan rating 0
                        imageUrl = imageUrl,
                        category = category
                    )

                    // Memanggil fungsi addProduct yang sudah kita tambahkan di ViewModel
                    viewModel.addProduct(newProduct)

                    // Menjalankan aksi setelah berhasil simpan (misal navigasi balik)
                    onSaveSuccess()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Simpan Produk")
            }
        }
    }
}