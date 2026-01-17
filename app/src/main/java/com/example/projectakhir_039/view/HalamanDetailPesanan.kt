package com.example.projectakhir_039.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // IMPORT WAJIB UNTUK LIST
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_039.data.models.OrderItem // Pastikan model ini sudah Anda buat
import com.example.projectakhir_039.viewmodel.OrderViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetailPesanan(
    orderId: Int,
    onBack: () -> Unit,
    orderVM: OrderViewModel = viewModel()
) {
    // Memuat rincian barang berdasarkan ID pesanan saat halaman dibuka
    LaunchedEffect(orderId) {
        orderVM.loadOrderItems(orderId)
    }

    val orderItems by orderVM.orderItems.collectAsState()
    val isLoading by orderVM.isLoading.collectAsState()

    // Hitung total harga secara dinamis dari list item
    val totalBill = orderItems.sumOf { it.price * it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rincian Pesanan #$orderId", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF8F9FA))) {
            if (isLoading) {
                // Tampilan loading saat mengambil data dari PHP
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFC62828))
            } else if (orderItems.isEmpty()) {
                // Tampilan jika data tidak ditemukan atau kosong
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.ReceiptLong, null, Modifier.size(64.dp), tint = Color.LightGray)
                    Text("Detail barang tidak ditemukan", color = Color.Gray)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // 1. Kartu Info Pengiriman
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalShipping, null, tint = Color(0xFFC62828))
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text("Status Pengiriman", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Pesanan Anda sedang diproses", color = Color.Gray, fontSize = 12.sp)
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    Text("Daftar Barang:", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    Spacer(Modifier.height(8.dp))

                    // 2. Daftar Sepatu yang Dibeli
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(orderItems) { item ->
                            OrderItemRow(item)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // 3. Panel Ringkasan Pembayaran (Sticky Footer)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        tonalElevation = 4.dp,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total Tagihan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(
                                    text = "Rp ${String.format("%,.0f", totalBill)}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp,
                                    color = Color(0xFFC62828) // Warna merah e-Tiket
                                )
                            }
                            Text(
                                "Harga sudah termasuk PPN",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItemRow(item: OrderItem) {
    ListItem(
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        headlineContent = { Text(item.name, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
        supportingContent = { Text("${item.quantity} pasang", fontSize = 13.sp, color = Color.Gray) },
        trailingContent = {
            Text(
                "Rp ${String.format("%,.0f", item.price * item.quantity)}",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    )
    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
}