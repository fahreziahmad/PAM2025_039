package com.example.projectakhir_039.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectakhir_039.R
import com.example.projectakhir_039.uicontroller.PetalNavigasi
import com.example.projectakhir_039.uicontroller.Route

@Composable
fun HalamanLogin(petalNav: PetalNavigasi) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo atau Gambar Sepatu Ikonik
        Image(
            painter = painterResource(id = R.drawable.shoe_0_1), // Pastikan ada di drawable
            contentDescription = null,
            modifier = Modifier.size(220.dp)
        )

        Text(
            text = "Start Your Journey",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "Find your best shoes here",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Input Email
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input Password
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Tombol Login dengan Gradient (Senada dengan tombol Buy Now)
        Button(
            onClick = {
                // Navigasi ke Halaman Home
                petalNav.navController.navigate(Route.Home.path)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
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
                Text("Login", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}