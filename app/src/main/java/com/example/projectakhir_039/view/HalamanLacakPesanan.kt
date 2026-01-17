package com.example.projectakhir_039.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projectakhir_039.data.models.Order


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanLacakPesanan(navController: NavHostController) {
    // 1. Definisikan variabel 'orders' di sini agar tidak 'unresolved'
    val orders = listOf(
        Order(
            id = 1,
            userId = 1,
            totalPrice = 250.0,
            status = "Diproses",
            paymentMethod = "COD",
            createdAt = "2026-01-07"
        ),
        Order(
            id = 2,
            userId = 1,
            totalPrice = 500.0,
            status = "Dikirim",
            paymentMethod = "Transfer Bank",
            createdAt = "2026-01-06"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lacak Pesanan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 2. Sekarang 'orders' sudah bisa dikenali
            items(orders) { order ->
                OrderCard(order)
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Order #${order.id}", fontWeight = FontWeight.Bold)

                val statusColor = when (order.status) {
                    "Pending" -> Color.Red
                    "Diproses" -> Color(0xFF42A5F5)
                    "Dikirim" -> Color(0xFFFFA000)
                    else -> Color.Green
                }

                Surface(color = statusColor.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                    Text(
                        text = order.status,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Pastikan memanggil 'totalPrice' dan 'paymentMethod' (tanpa underscore)
            Text("Total: $${order.totalPrice}", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Text("Metode: ${order.paymentMethod}", color = Color.Gray, fontSize = 14.sp)
            Text("Tanggal: ${order.createdAt}", color = Color.Gray, fontSize = 12.sp)
        }
    }
}