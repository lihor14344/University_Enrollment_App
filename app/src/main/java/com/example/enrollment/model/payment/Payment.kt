package com.example.enrollment.model.payment

data class Payment(
    val id: Int,
    val student_id: Int,
    val amount: Double,
    val payment_method: String,
    val status: String,
    val semester: String,
    val year: Int
)
