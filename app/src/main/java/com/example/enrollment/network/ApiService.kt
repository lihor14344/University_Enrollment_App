package com.example.enrollment.network

import com.example.enrollment.model.auth.LoginRequest
import com.example.enrollment.model.auth.LoginResponse
import com.example.enrollment.model.auth.RegisterRequest
import com.example.enrollment.model.auth.RegisterResponse
import com.example.enrollment.model.common.ApiMessage
import com.example.enrollment.model.common.News
import com.example.enrollment.model.payment.*
import com.example.enrollment.model.profile.ProfileResponse
import com.example.enrollment.model.profile.ProfileUpdateRequest
import com.example.enrollment.model.student.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ================= AUTH =================

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("student-login")
    suspend fun studentLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    // ================= NEWS =================

    @GET("news")
    suspend fun getNews(): Response<List<News>>

    // ================= STUDENT PROFILE =================

    @GET("student/profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @PUT("student/profile")
    suspend fun updateProfile(
        @Body request: ProfileUpdateRequest
    ): Response<ApiMessage>

    // ================= STUDENT ENROLL =================

    @POST("student/enroll")
    suspend fun createStudentEnrollment(
        @Body request: EnrollmentRequest
    ): Response<EnrollmentResponse>

    // ================= STUDENT ENROLLMENTS =================

    @GET("student/enrollments")
    suspend fun getStudentEnrollments(): Response<List<EnrollmentResponse>>

    // ================= STUDENT PAYMENTS =================

    @POST("student/payment/checkout")
    suspend fun checkoutStudentPayment(
        @Body request: CheckoutRequest
    ): Response<CheckoutResponse>

    @POST("student/payment/verify")
    suspend fun verifyStudentPayment(
        @Body request: VerifyRequest
    ): Response<VerifyResponse>

    @GET("student/payments")
    suspend fun getStudentPayments(): Response<List<PaymentResponse>>

    // ================= STUDENT CARD =================

    @GET("student/card")
    suspend fun getCard(): Response<StudentCardResponse>

    @Multipart
    @POST("student/card")
    suspend fun uploadCard(
        @Part frontImage: MultipartBody.Part,
        @Part backImage: MultipartBody.Part
    ): Response<StudentCardResponse>

    // ================= STUDENT ENROLLMENT COURSES =================

    @GET("student/enrollment-courses")
    suspend fun getEnrollmentCourses(
        @Query("enrollment_id") enrollmentId: Int
    ): Response<List<EnrollmentCourseResponse>>

    @POST("student/enrollment-courses")
    suspend fun createEnrollmentCourse(
        @Body request: EnrollmentCourseRequest
    ): Response<EnrollmentCourseResponse>

    @GET("student/enrollment-courses/{id}")
    suspend fun getEnrollmentCourse(
        @Path("id") id: Int
    ): Response<EnrollmentCourseResponse>

    @PUT("student/enrollment-courses/{id}")
    suspend fun updateEnrollmentCourse(
        @Path("id") id: Int,
        @Body request: EnrollmentCourseRequest
    ): Response<EnrollmentCourseResponse>

    @DELETE("student/enrollment-courses/{id}")
    suspend fun deleteEnrollmentCourse(
        @Path("id") id: Int
    ): Response<ApiMessage>
}
