package com.example.enrollment.model.student

data class Attendance(
    val id: Int,
    val courseName: String,
    val date: String,
    val status: String // e.g., "present", "absent"
)