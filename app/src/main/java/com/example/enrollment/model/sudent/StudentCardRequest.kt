package com.example.enrollment.model.student

data class StudentCardRequest(
    val front_image: String,
    val back_image: String,
    val status: String
)
