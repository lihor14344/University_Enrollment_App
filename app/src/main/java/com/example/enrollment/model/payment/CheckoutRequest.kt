package com.example.enrollment.model.payment

data class CheckoutRequest(
    val amount: Double,
    val semester: String,
    val year: Int
)
