package com.example.enrollment.model.academic

data class CourseResponse(
    val id: Int,
    val major_id: Int,
    val code: String,
    val name: String,
    val credit: Int
)
