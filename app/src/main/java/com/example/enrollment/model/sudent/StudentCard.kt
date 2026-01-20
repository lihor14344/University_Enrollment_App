package com.example.enrollment.model.student

data class StudentCard(
    val id: Int,
    val user_id: Int,
    val front_image: String,
    val back_image: String,
    val status: String
)
