package com.example.enrollment.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enrollment.model.auth.LoginRequest
import com.example.enrollment.model.auth.LoginResponse
import com.example.enrollment.network.ApiClient
import com.example.enrollment.repository.AuthRepository
import com.example.enrollment.utils.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val context: Context) : ViewModel() {
    private val repository = AuthRepository()
    private val prefs = PreferencesManager(context)

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        // Load token on init
        val token = prefs.getToken()
        ApiClient.setAuthToken(token)
    }

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = repository.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val loginResponse = response.body()!!
                    // Save token
                    prefs.saveToken(loginResponse.token)
                    ApiClient.setAuthToken(loginResponse.token)
                    _loginState.value = LoginState.Success(loginResponse)
                } else {
                    _loginState.value = LoginState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Network error: ${e.message}")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}