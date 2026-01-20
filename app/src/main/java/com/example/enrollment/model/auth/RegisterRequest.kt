package com.example.enrollment.model.auth

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role_id: Int? = null
)
