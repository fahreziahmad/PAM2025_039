package com.example.projectakhir_039.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.projectakhir_039.data.models.Product
import com.example.projectakhir_039.R
import com.example.projectakhir_039.data.models.formatRupiah

@Composable
fun ProductCard(
    product: Product,
    role: String, // Parameter peran (admin/pelanggan)
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAction: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Sinkronisasi Gambar: Memprioritaskan URL dari MySQL
            // ProductCard.kt
            AsyncImage(
                // PERBAIKAN: Gunakan properti imageResId dari objek produk tersebut
                model = if (!product.image_url.isNullOrEmpty()) product.image_url else product.imageResId,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit,
                // Gambar error juga bisa dibuat berbeda atau tetap placeholder
                error = painterResource(id = R.drawable.shoes_4)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                fontSize = 14.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatRupiah(product.price), // Tampilan baru: Rp 150.000
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                // LOGIKA IKON KATALOG: Menampilkan ShoppingCart untuk Pelanggan
                IconButton(
                    onClick = onAction,
                    modifier = Modifier
                        .background(Color.Black, RoundedCornerShape(8.dp))
                        .size(32.dp)
                ) {
                    Icon(
                        // Berubah otomatis berdasarkan variabel 'role'
                        imageVector = if (role == "admin") Icons.Default.Edit else Icons.Default.ShoppingCart,
                        contentDescription = "Action Icon",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}