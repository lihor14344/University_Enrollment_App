package com.example.enrollment.model.payment

data class CheckoutRequest(
    val amount: Double,
    val description: String,
    val courses: List<Int>
)
