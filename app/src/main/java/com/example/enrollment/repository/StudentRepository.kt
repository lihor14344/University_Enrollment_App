package com.example.enrollment.repository

import com.example.enrollment.model.profile.ProfileResponse
import com.example.enrollment.model.student.EnrollmentCourseResponse
import com.example.enrollment.model.student.EnrollmentResponse
import com.example.enrollment.model.student.StudentCardResponse
import com.example.enrollment.network.ApiClient
import retrofit2.Response

class StudentRepository {
    private val apiService = ApiClient.retrofit.create(com.example.enrollment.network.ApiService::class.java)

    suspend fun getProfile(): Response<ProfileResponse> {
        return apiService.getProfile()
    }

    suspend fun getEnrollments(): Response<List<EnrollmentResponse>> {
        return apiService.getMyEnrollments()
    }

    suspend fun getEnrollmentCourses(): Response<List<EnrollmentCourseResponse>> {
        return apiService.getEnrollmentCourses()
    }

    suspend fun getStudentCard(): Response<StudentCardResponse> {
        return apiService.getCard()
    }
}