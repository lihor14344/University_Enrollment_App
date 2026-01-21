package com.example.enrollment.repository

import com.example.enrollment.model.common.ApiMessage
import com.example.enrollment.model.payment.CheckoutRequest
import com.example.enrollment.model.payment.CheckoutResponse
import com.example.enrollment.model.payment.VerifyRequest
import com.example.enrollment.model.payment.VerifyResponse
import com.example.enrollment.model.profile.ProfileResponse
import com.example.enrollment.model.profile.ProfileUpdateRequest
import com.example.enrollment.model.student.EnrollmentCourseRequest
import com.example.enrollment.model.student.EnrollmentCourseResponse
import com.example.enrollment.model.student.EnrollmentRequest
import com.example.enrollment.model.student.EnrollmentResponse
import com.example.enrollment.model.student.StudentCardResponse
import com.example.enrollment.network.ApiClient
import okhttp3.MultipartBody
import retrofit2.Response

class StudentRepository {
    private val apiService = ApiClient.retrofit.create(com.example.enrollment.network.ApiService::class.java)

    suspend fun getProfile(): Response<ProfileResponse> {
        return apiService.getProfile()
    }

    suspend fun updateProfile(profileUpdate: ProfileUpdateRequest): Response<ApiMessage> {
        return apiService.updateProfile(profileUpdate)
    }

    suspend fun getEnrollments(): Response<List<EnrollmentResponse>> {
        return apiService.getMyEnrollments()
    }

    suspend fun enroll(enrollmentRequest: EnrollmentRequest): Response<EnrollmentResponse> {
        return apiService.enroll(enrollmentRequest)
    }

    suspend fun getEnrollmentCourses(): Response<List<EnrollmentCourseResponse>> {
        return apiService.getEnrollmentCourses()
    }

    suspend fun createEnrollmentCourse(enrollmentCourseRequest: EnrollmentCourseRequest): Response<EnrollmentCourseResponse> {
        return apiService.createEnrollmentCourse(enrollmentCourseRequest)
    }

    suspend fun getStudentCard(): Response<StudentCardResponse> {
        return apiService.getCard()
    }

    suspend fun uploadStudentCard(frontImage: MultipartBody.Part, backImage: MultipartBody.Part): Response<StudentCardResponse> {
        return apiService.uploadCard(frontImage, backImage)
    }

    suspend fun checkoutPayment(checkoutRequest: CheckoutRequest): Response<CheckoutResponse> {
        return apiService.studentCheckoutPayment(checkoutRequest)
    }

    suspend fun verifyPayment(verifyRequest: VerifyRequest): Response<VerifyResponse> {
        return apiService.studentVerifyPayment(verifyRequest)
    }

    suspend fun getPayments(): Response<List<com.example.enrollment.model.payment.PaymentResponse>> {
        return apiService.getStudentPayments()
    }
}