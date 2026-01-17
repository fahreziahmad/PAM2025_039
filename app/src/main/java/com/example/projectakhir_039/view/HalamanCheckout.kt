package com.example.projectakhir_039.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projectakhir_039.uicontroller.Route // Import Route Anda
import com.example.projectakhir_039.viewmodel.CartViewModel
import com.example.projectakhir_039.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanCheckout(
    navController: NavHostController,
    cartViewModel: CartViewModel = viewModel(),
    orderViewModel: OrderViewModel = viewModel(),
    userId: Int = 1
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val subtotal by cartViewModel.totalPrice.collectAsState()

    var address by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf("COD") }

    // Perhitungan total menggunakan rumus:
    // $$totalPayment = subtotal + shippingFee$$
    val shippingFee = 10.0
    val totalPayment = subtotal + shippingFee

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Checkout", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Menyelesaikan error scroll
        ) {
            Text("Alamat Pengiriman", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Masukkan alamat lengkap...") }
            )

            Spacer(Modifier.height(16.dp))

            Text("Ringkasan Pesanan", fontWeight = FontWeight.Bold)
            cartItems.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween // Menyelesaikan error Arrangement
                ) {
                    Text("${item.name} (x${item.quantity})")
                    Text("$${String.format("%.2f", item.price * item.quantity)}")
                }
            }

            HorizontalDivider(Modifier.padding(vertical = 8.dp)) // Gunakan HorizontalDivider untuk Material 3

            // Rincian Biaya
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal")
                Text("$${String.format("%.2f", subtotal)}")
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Ongkos Kirim")
                Text("$${String.format("%.2f", shippingFee)}")
            }

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total Pembayaran", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    text = "$${String.format("%.2f", totalPayment)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp // Menyelesaikan error sp
                )
            }

