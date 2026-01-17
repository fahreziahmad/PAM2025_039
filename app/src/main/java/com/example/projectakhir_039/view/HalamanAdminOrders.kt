package com.example.projectakhir_039.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_039.data.models.Order
import com.example.projectakhir_039.data.models.formatRupiah
import com.example.projectakhir_039.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanAdminOrders(
    orderViewModel: OrderViewModel = viewModel(),
    onBack: () -> Unit // Parameter ini digunakan untuk navigasi kembali
) {
    val orders by orderViewModel.allOrders.collectAsState()

    LaunchedEffect(Unit) {
        orderViewModel.loadAllOrdersForAdmin()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Pesanan Masuk", fontWeight = FontWeight.Bold) },
                // TAMBAHKAN TOMBOL KEMBALI DI SINI
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF5F5F5))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Kembali",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (orders.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada pesanan masuk", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(orders) { order: Order ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("No. Pesanan: ${order.orderNumber}", fontWeight = FontWeight.ExtraBold)
                                Text("Total Bayar: ${formatRupiah(order.totalPrice)}", color = Color(0xFFD81B60), fontWeight = FontWeight.Bold)
                                Text("Metode: ${order.paymentMethod}", fontSize = 14.sp)
                                Text("Alamat: ${order.shippingAddress}", fontSize = 14.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Status: ${order.status}",
                                    color = if (order.status.lowercase() == "pending") Color.Blue else Color.Green,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}