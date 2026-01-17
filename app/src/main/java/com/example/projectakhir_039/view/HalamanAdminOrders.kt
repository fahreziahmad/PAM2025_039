package com.example.projectakhir_039.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_039.data.models.Order
import com.example.projectakhir_039.data.models.formatRupiah
import com.example.projectakhir_039.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class) // Menghilangkan error "experimental API"
@Composable
fun HalamanAdminOrders(
    orderViewModel: OrderViewModel = viewModel(), // FIX: Import viewModel
    onBack: () -> Unit
) {
    // FIX: Import collectAsState
    val orders by orderViewModel.allOrders.collectAsState()

    // Memuat data saat halaman dibuka
    LaunchedEffect(Unit) {
        orderViewModel.loadAllOrdersForAdmin()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Pesanan Masuk", fontWeight = FontWeight.Bold) } // FIX: Import FontWeight
            )
        }
    ) { padding ->
        // Memberikan padding dari Scaffold agar tidak tertutup TopBar
        Column(modifier = Modifier.padding(padding).fillMaxSize()) { // FIX: Import Modifier
            if (orders.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("Belum ada pesanan masuk", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tentukan tipe 'order' secara eksplisit agar parameter terbaca
                    items(orders) { order: Order ->
                        Card(
                            modifier = Modifier.fillMaxWidth(), // FIX: Import Modifier
                            shape = RoundedCornerShape(16.dp), // FIX: Import RoundedCornerShape
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)) // FIX: Import CardDefaults
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // FIX: Parameter orderNumber, totalPrice, dll sekarang dikenali
                                Text("No. Pesanan: ${order.orderNumber}", fontWeight = FontWeight.ExtraBold)
                                Text("Total Bayar: ${formatRupiah(order.totalPrice)}", color = Color(0xFFD81B60))
                                Text("Metode: ${order.paymentMethod}")
                                Text("Alamat: ${order.shippingAddress}")
                                Text("Status: ${order.status}", color = Color.Blue, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}