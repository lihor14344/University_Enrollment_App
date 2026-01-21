package com.example.enrollment.model.common

import com.example.enrollment.model.academicInfo.News

data class DashboardResponse(
    val totalEnrollments: Int,
    val totalPayments: Int,
    val pendingPayments: Int,
    val recentNews: List<News>
)
