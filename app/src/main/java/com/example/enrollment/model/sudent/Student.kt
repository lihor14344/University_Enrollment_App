package com.example.enrollment.model.student

data class Student(
    val id: Int,
    val user_id: Int,
    val student_code: String,
    val full_name: String,
    val major_id: Int? = null
)
