package com.example.projectakhir_039.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_039.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanRegister(
    onNavigateToLogin: () -> Unit,
    authVM: AuthViewModel = viewModel()
) {
    // 1. Definisi State untuk menampung input pengguna secara dinamis
    var fullName by remember { mutableStateOf("") }
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val isLoading by authVM.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFB71C1C), Color(0xFF880E4F))
                )
            )
            .verticalScroll(rememberScrollState()) // Agar layar bisa di-scroll pada HP kecil
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Create Your\nAccount",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 38.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(40.dp))

                // 2. Input Full Name
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it }, // Langsung update state
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (fullName.isNotEmpty()) Icon(Icons.Default.CheckCircle, "Valid", tint = Color(0xFF4CAF50))
                    },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Input Email/Phone
                OutlinedTextField(
                    value = emailOrPhone,
                    onValueChange = { emailOrPhone = it },
                    label = { Text("Phone or Gmail") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (emailOrPhone.contains("@") || emailOrPhone.length > 10) {
                            Icon(Icons.Default.CheckCircle, "Valid", tint = Color(0xFF4CAF50))
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Input Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null)
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Input Confirm Password
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // 6. Tombol Sign Up
                Button(
                    onClick = {
                        if (password == confirmPassword && fullName.isNotEmpty() && emailOrPhone.isNotEmpty()) {
                            // MENGGUNAKAN DATA DINAMIS DARI STATE
                            authVM.register(
                                name = fullName,        // Diambil dari state fullName
                                email = emailOrPhone,   // Diambil dari state emailOrPhone
                                password = password,    // Diambil dari state password
                                phone = emailOrPhone,   // Karena UI gabung, kita kirim data yang sama atau sesuaikan backend
                                role = "pelanggan"      // Tambahkan role sesuai kebutuhan register.php
                            ) {
                                onNavigateToLogin()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("SIGN UP", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Already have an account? ", color = Color.Gray)
                    Text(
                        "Sign in",
                        color = Color(0xFFC62828),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigateToLogin() }
                    )
                }
            }
        }
    }
}