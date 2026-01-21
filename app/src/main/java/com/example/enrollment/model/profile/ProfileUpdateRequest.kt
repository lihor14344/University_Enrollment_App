package com.example.enrollment.model.profile

data class ProfileUpdateRequest(
    val name: String,
    val email: String,
    val phone: String,
    val dob: String,
    val gender: String,
    val address: String
)
