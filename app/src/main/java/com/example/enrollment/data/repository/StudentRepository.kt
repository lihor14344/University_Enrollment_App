package com.example.enrollment.data.repository

import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.model.academic.CourseResponse
import com.example.enrollment.model.common.DashboardResponse
import com.example.enrollment.model.common.News
import com.example.enrollment.model.payment.CheckoutRequest
import com.example.enrollment.model.payment.CheckoutResponse
import com.example.enrollment.model.payment.PaymentResponse
import com.example.enrollment.model.payment.VerifyRequest
import com.example.enrollment.model.payment.VerifyResponse
import com.example.enrollment.model.profile.ProfileResponse
import com.example.enrollment.model.profile.ProfileUpdateRequest
import com.example.enrollment.model.student.EnrollmentCourseResponse
import com.example.enrollment.model.student.EnrollmentRequest
import com.example.enrollment.model.student.EnrollmentResponse
import com.example.enrollment.model.student.StudentCardResponse
import com.example.enrollment.network.ApiClient
import com.example.enrollment.network.ApiService
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import retrofit2.Response

class StudentRepository(private val authPreferences: AuthPreferences) {

    private val apiService: ApiService by lazy {
        ApiClient.create(authPreferences.getCurrentToken()).create(ApiService::class.java)
    }

    // Personal Information
    suspend fun getPersonalInformation(): Result<ProfileResponse> {
        return handleApiCall(apiCall = { apiService.getProfile() })
    }

    // Profile
    suspend fun getProfile(): Result<ProfileResponse> {
        return handleApiCall(apiCall = { apiService.getProfile() })
    }

    suspend fun updateProfile(request: ProfileUpdateRequest): Result<Unit> {
        return handleApiCall(apiCall = { apiService.updateProfile(request) }, transform = { Unit })
    }


    // Enrollments
    suspend fun enroll(request: EnrollmentRequest): Result<EnrollmentResponse> {
        return handleApiCall { apiService.createStudentEnrollment(request) }
    }

    suspend fun getEnrollments(): Result<List<EnrollmentResponse>> {
        return handleApiCall(apiCall = { apiService.getStudentEnrollments() })
    }


    suspend fun checkoutPayment(request: CheckoutRequest): Result<CheckoutResponse> {
        return handleApiCall(apiCall = { apiService.checkoutStudentPayment(request) })
    }

    suspend fun verifyPayment(request: VerifyRequest): Result<VerifyResponse> {
        return handleApiCall(apiCall = { apiService.verifyStudentPayment(request) })
    }

    // Student Card
    suspend fun getStudentCard(): Result<StudentCardResponse> {
        return handleApiCall(apiCall = { apiService.getCard() })
    }

    suspend fun uploadStudentCard(frontImage: MultipartBody.Part, backImage: MultipartBody.Part): Result<StudentCardResponse> {
        return handleApiCall(apiCall = { apiService.uploadCard(frontImage, backImage) })
    }

    // Payments
    suspend fun getPayments(): Result<List<PaymentResponse>> {
        return handleApiCall(apiCall = { apiService.getStudentPayments() })
    }

    // News
    suspend fun getNews(): Result<List<News>> {
        return handleApiCall(apiCall = { apiService.getNews() })
    }

    private suspend fun <T> handleApiCall(
        apiCall: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("API call failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun <T, R> handleApiCall(
        apiCall: suspend () -> Response<T>,
        transform: (T) -> R
    ): Result<R> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(transform(it)) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("API call failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}