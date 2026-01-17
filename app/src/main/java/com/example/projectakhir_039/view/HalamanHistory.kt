package com.example.projectakhir_039.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Import wajib untuk List
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import com.example.projectakhir_039.data.models.Order
import com.example.projectakhir_039.viewmodel.OrderViewModel
import com.example.projectakhir_039.viewmodel.AuthViewModel // Gunakan AuthViewModel untuk ambil User ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHistory(
    onBack: () -> Unit,
    orderVM: OrderViewModel = viewModel(),
    authVM: AuthViewModel = viewModel()
) {
    // Mengambil riwayat pesanan berdasarkan ID user yang login
    LaunchedEffect(Unit) {
        // Sesuaikan cara pengambilan ID user dari AuthViewModel Anda
        orderVM.loadOrders(userId = 1)
    }

    val orders by orderVM.orders.collectAsState()
    val isLoading by orderVM.isLoading.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Riwayat Pesanan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF8F9FA))) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.Red)
            } else if (orders.isEmpty()) {
                // Tampilan jika riwayat masih kosong
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.ReceiptLong, null, Modifier.size(80.dp), tint = Color.LightGray)
                    Spacer(Modifier.height(16.dp))
                    Text("Belum ada transaksi sepatu", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(orders) { order ->
                        OrderHistoryCard(order = order)
                    }
                }
            }
        }
    }
}
