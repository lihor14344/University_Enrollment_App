package com.example.enrollment.data.repository

import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.model.auth.LoginRequest
import com.example.enrollment.model.auth.LoginResponse
import com.example.enrollment.network.ApiClient
import com.example.enrollment.network.ApiService
import kotlinx.coroutines.flow.first
import retrofit2.Response

class AuthRepository(private val authPreferences: AuthPreferences) {

    private val apiService: ApiService by lazy {
        ApiClient.create { authPreferences.authToken.first() }.create(ApiService::class.java)
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            val response = apiService.studentLogin(loginRequest)
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    // Save token
                    authPreferences.saveAuthToken(loginResponse.token)
                    Result.success(loginResponse)
                } ?: Result.failure(Exception("Login response body is null"))
            } else {
                Result.failure(Exception("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        authPreferences.clearAuthToken()
    }

    fun getToken(): String? {
        return authPreferences.authToken.first()
    }
}