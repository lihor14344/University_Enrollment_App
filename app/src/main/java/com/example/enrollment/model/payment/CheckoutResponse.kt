package com.example.enrollment.model.payment

data class CheckoutResponse(
    val qrCode: String,
    val md5: String
)
