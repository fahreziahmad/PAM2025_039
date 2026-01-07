package com.example.projectakhir_039.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
// TAMBAHKAN DUA IMPORT INI UNTUK MEMPERBAIKI ERROR BARIS 19
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_039.viewmodel.AuthViewModel

@Composable
fun HalamanRegister(onSuccess: () -> Unit, authVM: AuthViewModel = viewModel()) {
    // Sekarang isLoading akan dikenali sebagai Boolean
    val isLoading by authVM.isLoading.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        // ... (TextFields tetap sama) ...

        Button(
            onClick = {
                authVM.register(name, email, password, phone) { onSuccess() }
            },
            modifier = Modifier.fillMaxWidth(),
            // Sekarang operator '!' berfungsi karena isLoading bertipe Boolean
            enabled = !isLoading
        ) {
            if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp)) else Text("Register")
        }
    }
}