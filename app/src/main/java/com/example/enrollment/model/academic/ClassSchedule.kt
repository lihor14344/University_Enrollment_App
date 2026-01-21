package com.example.enrollment.model.academic

data class ClassSchedule(
    val id: Int,
    val courseName: String,
    val day: String,
    val time: String,
    val room: String
)