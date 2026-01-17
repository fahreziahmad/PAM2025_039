package com.example.projectakhir_039.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.projectakhir_039.R
import com.example.projectakhir_039.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanProfil(
    onBack: () -> Unit,
    userVM: UserViewModel = viewModel() // ViewModel pusat untuk data user
) {
    // Mengamati data user terbaru secara real-time
    val user by userVM.currentUser.collectAsState()
    val context = LocalContext.current

    // Launcher untuk update foto profil (menyimpan URI ke user.phone sementara)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { userVM.updateProfilePhoto(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF800000), Color(0xFF4B0082)) // Gradien merah marun ke ungu
                )
            )
    ) {
        // --- Header ---
        CenterAlignedTopAppBar(
            title = { Text("Profil Saya", color = Color.White, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBackIosNew, "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Foto Profil & Tombol Edit ---
        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.BottomEnd
        ) {
            val painter = if (!user?.phone.isNullOrEmpty() && user?.phone?.startsWith("content://") == true) {
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(user?.phone)
                        .crossfade(true)
                        .build()
                )
            } else {
                painterResource(id = R.drawable.splash) // Placeholder gambar default
            }

            Image(
                painter = painter,
                contentDescription = "Foto Profil",
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )

            // Badge Tombol Kamera
            Surface(
                modifier = Modifier
                    .size(38.dp)
                    .offset(x = (-4).dp, y = (-4).dp)
                    .clickable { launcher.launch("image/*") },
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Icon(
                    Icons.Default.PhotoCamera,
                    contentDescription = "Edit",
                    tint = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- Identitas User (Otomatis dari Database) ---
        Text(
            text = user?.name ?: "Guest", // Default jika data belum masuk
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = user?.email ?: "email@mail.com",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // --- Kartu Detail Informasi ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                ProfileItem(Icons.Default.Person, "Nama", user?.name ?: "-")
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = Color.LightGray)

                ProfileItem(Icons.Default.Email, "Email", user?.email ?: "-")
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = Color.LightGray)

                ProfileItem(Icons.Default.AdminPanelSettings, "Role", user?.role ?: "pelanggan")
            }
        }
    }
}
