package com.example.enrollment.model.common



data class DashboardResponse(
    val totalEnrollments: Int,
    val totalPayments: Int,
    val pendingPayments: Int,

)
