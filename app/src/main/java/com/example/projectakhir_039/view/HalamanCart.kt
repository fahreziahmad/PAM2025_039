package com.example.projectakhir_039.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_039.R
import com.example.projectakhir_039.data.models.CartItem
import com.example.projectakhir_039.data.models.formatRupiah
import com.example.projectakhir_039.viewmodel.CartViewModel
import com.example.projectakhir_039.viewmodel.OrderViewModel
import com.example.projectakhir_039.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanCart(
    onBack: () -> Unit,
    cartViewModel: CartViewModel = viewModel(),
    orderViewModel: OrderViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentUser by userViewModel.currentUser.collectAsState()
    val currentUserId = currentUser?.id ?: 1

    LaunchedEffect(currentUserId) {
        cartViewModel.loadCart(userId = currentUserId)
    }

    val cartItems by cartViewModel.cartItems.collectAsState()
    val subtotal by cartViewModel.totalPrice.collectAsState()
    val isCheckoutLoading by orderViewModel.isLoading.collectAsState()

    val deliveryFee = if (cartItems.isEmpty()) 0.0 else 10000.0 // Sesuai mata uang Rupiah
    val tax = subtotal * 0.05
    val total = subtotal + deliveryFee + tax

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Order Detail", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFF5F5F5))) {
                        Icon(Icons.Default.ArrowBackIosNew, "Back", modifier = Modifier.size(16.dp))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp)) {
            if (cartItems.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("Your cart is empty", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(vertical = 16.dp)) {
                    items(cartItems) { item ->
                        CartItemCard(
                            item = item,
                            onIncrease = { cartViewModel.updateQuantity(item.id, 1, currentUserId) },
                            onDecrease = { cartViewModel.updateQuantity(item.id, -1, currentUserId) },
                            onDelete = { cartViewModel.removeFromCart(item.id, currentUserId) }
                        )
                    }
                }
            }

            if (cartItems.isNotEmpty()) {
                Column(modifier = Modifier.padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    PriceRow("Subtotal", formatRupiah(subtotal))
                    PriceRow("Delivery Fee", formatRupiah(deliveryFee))
                    PriceRow("Tax (5%)", formatRupiah(tax))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    PriceRow("Total", formatRupiah(total), isTotal = true)
                }

                Button(
                    onClick = {
                        val userAddress = currentUser?.address ?: "Yogyakarta" // Default jika alamat kosong                        val userAddress = currentUser?.address ?: "Yogyakarta"

                        orderViewModel.performCheckout(
                            userId = currentUserId,
                            total = total,
                            method = "COD",
                            address = userAddress // Alamat dikirim ke checkout.php
                        ) { status ->
                            if (status == "success") {
                                Toast.makeText(context, "Berhasil Checkout!", Toast.LENGTH_SHORT).show()
                                onBack()
                            } else {
                                Toast.makeText(context, "Gagal Checkout: $status", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp).padding(bottom = 16.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    enabled = !isCheckoutLoading
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Color(0xFFD81B60), Color(0xFF42A5F5)))), contentAlignment = Alignment.Center) {
                        if (isCheckoutLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        else Text("Check Out", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
