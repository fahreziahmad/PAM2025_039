package com.example.projectakhir_039.viewmodel

import android.net.Uri // PERBAIKAN: Gunakan android.net.Uri (Standar Android)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.projectakhir_039.data.models.User

class UserViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    // Simulasi data user yang sesuai dengan database Anda
                    val user = User(
                        id = 1,
                        name = "Fahrezi",
                        email = email,
                        password = password,
                        phone = "08123456789",
                        role = "user", // Sesuai dengan kolom di phpMyAdmin
                        address = "123 Main Street, Jakarta"
                    )
                    _currentUser.value = user
                    _isLoggedIn.value = true
                    _error.value = null
                } else {
                    _error.value = "Email and password cannot be empty"
                }
            } catch (e: Exception) {
                _error.value = "Login failed: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

