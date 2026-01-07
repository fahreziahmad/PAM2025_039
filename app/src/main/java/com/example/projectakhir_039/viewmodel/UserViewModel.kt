package com.example.projectakhir_039.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
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
                // Simulate login - replace with actual API call
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    val user = User(
                        id = 1,
                        name = "Jammie",
                        email = email,
                        password = password,
                        phone = "08123456789",
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

    fun register(name: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                // Simulate registration - replace with actual API call
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    val user = User(
                        id = 2,
                        name = name,
                        email = email,
                        password = password,
                        phone = phone,
                        address = ""
                    )
                    _currentUser.value = user
                    _isLoggedIn.value = true
                    _error.value = null
                } else {
                    _error.value = "Please fill all required fields"
                }
            } catch (e: Exception) {
                _error.value = "Registration failed: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _currentUser.value = user
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to update profile: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}