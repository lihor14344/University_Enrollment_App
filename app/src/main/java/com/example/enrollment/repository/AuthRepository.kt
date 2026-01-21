package com.example.enrollment.repository

import com.example.enrollment.model.auth.LoginRequest
import com.example.enrollment.model.auth.LoginResponse
import com.example.enrollment.network.ApiClient
import retrofit2.Response

class AuthRepository {
    private val apiService = ApiClient.retrofit.create(com.example.enrollment.network.ApiService::class.java)

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiService.studentLogin(loginRequest)
    }
}