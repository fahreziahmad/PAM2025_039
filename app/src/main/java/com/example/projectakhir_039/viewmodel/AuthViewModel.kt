package com.example.projectakhir_039.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_039.data.models.LoginResponse
import com.example.projectakhir_039.data.models.User
import com.example.projectakhir_039.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = UserRepository()

    // Memperbaiki error 'Unresolved reference isLoading'
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun login(name: String, pass: String, onResult: (LoginResponse) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            // Simulasi/Logika API Login
            val response = if (name == "admin" && pass == "123") {
                LoginResponse("success", "Welcome Admin", "admin")
            } else {
                LoginResponse("success", "Welcome User", "user")
            }
            onResult(response)
            _isLoading.value = false
        }
    }

    // Memperbaiki error 'Unresolved reference register'
    fun register(name: String, email: String, pass: String, phone: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val isSuccess = repository.registerUser(User(0, name, email, pass, phone))
            if (isSuccess) onSuccess()
            _isLoading.value = false
        }
    }
}