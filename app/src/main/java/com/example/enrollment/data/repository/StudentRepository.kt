package com.example.enrollment.data.repository

import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.model.academic.CourseResponse
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
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import retrofit2.Response

class StudentRepository(private val authPreferences: AuthPreferences) {

    private val apiService: ApiService by lazy {
        ApiClient.create { authPreferences.authToken.first() }.create(ApiService::class.java)
    }

    // Profile
    suspend fun getProfile(): Result<ProfileResponse> {
        return handleApiCall { apiService.getProfile() }
    }

    suspend fun updateProfile(request: ProfileUpdateRequest): Result<Unit> {
        return handleApiCall({ apiService.updateProfile(request) }) { Unit }
    }

    // Courses
    suspend fun getCourses(): Result<List<CourseResponse>> {
        return handleApiCall { apiService.getCourses() }
    }

    // Enrollments
    suspend fun enroll(request: EnrollmentRequest): Result<EnrollmentResponse> {
        return handleApiCall { apiService.enroll(request) }
    }

    suspend fun getEnrollments(): Result<List<EnrollmentResponse>> {
        return handleApiCall { apiService.getMyEnrollments() }
    }

    // Class Schedule
    suspend fun getClassSchedule(): Result<List<CourseResponse>> {
        return handleApiCall { apiService.getClassSchedule() }
    }

    // Payments
    suspend fun getPayments(): Result<List<PaymentResponse>> {
        return handleApiCall { apiService.getStudentPayments() }
    }

    suspend fun checkoutPayment(request: CheckoutRequest): Result<CheckoutResponse> {
        return handleApiCall { apiService.studentCheckoutPayment(request) }
    }

    suspend fun verifyPayment(request: VerifyRequest): Result<VerifyResponse> {
        return handleApiCall { apiService.studentVerifyPayment(request) }
    }

    // Scores
    suspend fun getScores(): Result<List<EnrollmentCourseResponse>> {
        return handleApiCall { apiService.getStudentScores() }
    }

    // Attendance
    suspend fun getAttendance(): Result<List<EnrollmentCourseResponse>> {
        return handleApiCall { apiService.getStudentAttendance() }
    }

    // Student Card
    suspend fun getStudentCard(): Result<StudentCardResponse> {
        return handleApiCall { apiService.getCard() }
    }

    suspend fun uploadStudentCard(frontImage: MultipartBody.Part, backImage: MultipartBody.Part): Result<StudentCardResponse> {
        return handleApiCall { apiService.uploadCard(frontImage, backImage) }
    }

    // News
    suspend fun getNews(): Result<List<News>> {
        return handleApiCall { apiService.getNews() }
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