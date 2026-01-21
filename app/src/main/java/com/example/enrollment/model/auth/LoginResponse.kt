package com.example.enrollment.model.auth

import com.example.enrollment.model.student.Student
import com.example.enrollment.model.user.User

data class LoginResponse(
    val status: Boolean,
    val message: String,
    val data: LoginData,
    val token: String
)

data class LoginData(
    val user: User,
    val student: Student
)
