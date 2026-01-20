package com.example.enrollment.model.student

data class StudentRequest(
    val user_id: Int,
    val student_code: String,
    val full_name: String,
    val major_id: Int
)
