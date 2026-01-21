package com.example.enrollment.model.auth

import com.example.enrollment.model.user.User

data class RegisterResponse(
    val status: Boolean,
    val message: String,
    val data: User
)
