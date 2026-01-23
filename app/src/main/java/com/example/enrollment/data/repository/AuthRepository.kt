package com.example.enrollment.data.repository

import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.model.auth.LoginRequest
import com.example.enrollment.model.auth.LoginResponse
import com.example.enrollment.network.ApiClient
import com.example.enrollment.network.ApiService
import retrofit2.Response

class AuthRepository(
    private val authPreferences: AuthPreferences
) {

    private val apiService: ApiService by lazy {
        ApiClient.create(authPreferences.getCurrentToken())
            .create(ApiService::class.java)
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            val response: Response<LoginResponse> = apiService.studentLogin(loginRequest)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    // Save token for future requests
                    authPreferences.saveAuthToken(body.token)
                    Result.success(body)
                } else {
                    Result.failure(Exception("Login response body is null"))
                }
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
        return authPreferences.getCurrentToken()
    }
}
