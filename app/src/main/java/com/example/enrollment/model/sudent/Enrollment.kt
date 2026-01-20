package com.example.enrollment.model.student

data class Enrollment(
    val id: Int,
    val student_id: Int,
    val major_id: Int,
    val semester: String,
    val year: Int,
    val status: String
)
