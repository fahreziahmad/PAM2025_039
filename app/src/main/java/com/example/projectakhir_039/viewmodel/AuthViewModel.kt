package com.example.projectakhir_039.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_039.data.api.ApiClient
import com.example.projectakhir_039.data.models.LoginResponse
import com.example.projectakhir_039.data.models.User
import com.example.projectakhir_039.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // PERBAIKAN: Menggunakan 'errorMessage' agar sesuai dengan HalamanLogin.kt
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse

    /**
     * Fungsi Login: Mendukung Nama atau Email
     * Menyimpan pesan error ke State agar bisa dibaca UI
     */
    fun login(identity: String, pass: String, onResult: (LoginResponse) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null // Reset error sebelum mencoba login
            try {
                // Memanggil ApiService untuk loginUser
                val response = ApiClient.apiService.loginUser(identity, pass)
                _loginResponse.value = response
                onResult(response)

                // Jika server mengirimkan status gagal, simpan pesannya
                if (response.status != "success") {
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                val errorMsg = "Koneksi Gagal: ${e.message}"
                _errorMessage.value = errorMsg
                onResult(LoginResponse("error", errorMsg))
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Fungsi Register: Mengirim data user lengkap ke database
     */
    fun register(
        name: String,
        email: String,
        password: String,
        phone: String,
        role: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Membuat objek User lengkap dengan email dan role
                val newUser = User(
                    id = 0,
                    name = name,
                    email = email,
                    password = password,
                    phone = phone,
                    role = role
                )

                val isSuccess = repository.registerUser(newUser)

                if (isSuccess) {
                    onSuccess()
                } else {
                    _errorMessage.value = "Registrasi Gagal: Silakan coba lagi"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi Kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}