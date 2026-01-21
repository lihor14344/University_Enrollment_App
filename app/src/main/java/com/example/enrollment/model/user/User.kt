package com.example.enrollment.model.user

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role_id: Int? = null
)
