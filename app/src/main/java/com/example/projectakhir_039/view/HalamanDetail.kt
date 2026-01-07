package com.example.projectakhir_039.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectakhir_039.R

@Composable
fun HalamanDetail(productId: Int, navController: NavController) {
    // State untuk interaksi sederhana
    var quantity by remember { mutableStateOf(2) }
    var selectedSize by remember { mutableStateOf(40) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- Bagian Atas: Gambar & Sidebar ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
        ) {
            // Background Gradient Biru ke Ungu (seperti di gambar)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 60.dp, end = 40.dp, top = 20.dp, bottom = 20.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFD81B60), Color(0xFF42A5F5))
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
            )

            // Gambar Sepatu (Pastikan ada file gambar di res/drawable)
            Image(
                painter = painterResource(id = R.drawable.shoe_0_1), // Ganti dengan id gambar kamu
                contentDescription = "Nike Shoe",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                contentScale = ContentScale.Fit
            )

            // Ikon Navigasi Atas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.background(Color.White.copy(alpha = 0.5f), CircleShape)
                ) {
                    // PERBAIKAN DI SINI
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        modifier = Modifier.scale(0.8f)
                    )
                }
                IconButton(
                    onClick = { /* Handle Wishlist */ },
                    modifier = Modifier.background(Color.White.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }

            // Sidebar: Size Selector (Kiri)
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf(38, 39, 40, 41, 42).forEach { size ->
                    SizeItem(size = size, isSelected = selectedSize == size) { selectedSize = size }
                }
            }

            // Sidebar: Color Selector (Kanan)
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf(Color.Blue, Color.Red, Color.DarkGray, Color.Cyan, Color.Blue).forEach { color ->
                    ColorCircle(color = color)
                }
            }
        }

        // --- Bagian Bawah: Informasi Produk ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nike Basketball Shoes",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$272.99",
                color = Color(0xFFD81B60),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Quantity Selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                    Text("-", fontSize = 24.sp, color = Color(0xFFD81B60), fontWeight = FontWeight.Bold)
                }
                Text(text = quantity.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                IconButton(onClick = { quantity++ }) {
                    Text("+", fontSize = 24.sp, color = Color(0xFF42A5F5), fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = "They are a cross between the Air Force 1, the Air Jordan and the Nike Terminator. The first model came out in 1985 and was called Dunk in honour of the players' feats.",
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Footer: Total & Buy Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Price", color = Color.Gray, fontSize = 14.sp)
                    Text("$49.00", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                }
                Button(
                    onClick = { /* Logic Checkout */ },
                    modifier = Modifier
                        .height(50.dp)
                        .width(180.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFFD81B60), Color(0xFF42A5F5))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Buy Now", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun SizeItem(size: Int, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(35.dp)
            .border(
                1.dp,
                if (isSelected) Color.Transparent else Color.LightGray,
                CircleShape
            )
            .background(
                if (isSelected) Color(0xFF9575CD) else Color.Transparent,
                CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = size.toString(),
            fontSize = 12.sp,
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ColorCircle(color: Color) {
    Box(
        modifier = Modifier
            .size(25.dp)
            .background(color, CircleShape)
            .border(2.dp, Color.White, CircleShape)
    )
}