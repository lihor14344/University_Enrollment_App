package com.example.enrollment.network

import android.credentials.RegisterCredentialDescriptionRequest
import com.example.enrollment.model.*
import com.example.enrollment.model.academic.CourseRequest
import com.example.enrollment.model.academic.CourseResponse
import com.example.enrollment.model.academic.DepartmentRequest
import com.example.enrollment.model.academic.DepartmentResponse
import com.example.enrollment.model.academic.FacultyRequest
import com.example.enrollment.model.academic.FacultyResponse
import com.example.enrollment.model.academic.MajorRequest
import com.example.enrollment.model.academic.MajorResponse
import com.example.enrollment.model.auth.LoginRequest
import com.example.enrollment.model.auth.LoginResponse
import com.example.enrollment.model.auth.RegisterResponse
import com.example.enrollment.model.common.ApiMessage
import com.example.enrollment.model.common.News
import com.example.enrollment.model.payment.CheckoutRequest
import com.example.enrollment.model.payment.CheckoutResponse
import com.example.enrollment.model.payment.PaymentResponse
import com.example.enrollment.model.payment.VerifyRequest
import com.example.enrollment.model.payment.VerifyResponse
import com.example.enrollment.model.profile.ProfileResponse
import com.example.enrollment.model.profile.ProfileUpdateRequest
import com.example.enrollment.model.student.EnrollmentCourseRequest
import com.example.enrollment.model.student.EnrollmentCourseResponse
import com.example.enrollment.model.student.EnrollmentRequest
import com.example.enrollment.model.student.EnrollmentResponse
import com.example.enrollment.model.student.StudentCardRequest
import com.example.enrollment.model.student.StudentCardResponse
import com.example.enrollment.model.student.StudentRequest
import com.example.enrollment.model.student.StudentResponse
import com.example.enrollment.model.user.RoleRequest
import com.example.enrollment.model.user.RoleResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // PUBLIC ROUTES
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterCredentialDescriptionRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("student-login")
    suspend fun studentLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>


    // ADMIN ROUTES (requires auth:sanctum and is_admin)

    // ROLES
    @GET("roles")
    suspend fun getRoles(): Response<List<RoleResponse>>

    @POST("roles")
    suspend fun createRole(@Body roleRequest: RoleRequest): Response<RoleResponse>

    @GET("roles/{id}")
    suspend fun getRole(@Path("id") id: Int): Response<RoleResponse>

    @PUT("roles/{id}")
    suspend fun updateRole(@Path("id") id: Int, @Body roleRequest: RoleRequest): Response<RoleResponse>

    @DELETE("roles/{id}")
    suspend fun deleteRole(@Path("id") id: Int): Response<ApiMessage>

    // STUDENTS
    @GET("students")
    suspend fun getStudents(): Response<List<StudentResponse>>

    @POST("students")
    suspend fun createStudent(@Body studentRequest: StudentRequest): Response<StudentResponse>

    @GET("students/{id}")
    suspend fun getStudent(@Path("id") id: Int): Response<StudentResponse>

    @PUT("students/{id}")
    suspend fun updateStudent(@Path("id") id: Int, @Body studentRequest: StudentRequest): Response<StudentResponse>

    @DELETE("students/{id}")
    suspend fun deleteStudent(@Path("id") id: Int): Response<ApiMessage>

    // ENROLLMENTS
    @GET("enrollments")
    suspend fun getEnrollments(): Response<List<EnrollmentResponse>>

    @POST("enrollments")
    suspend fun createEnrollment(@Body enrollmentRequest: EnrollmentRequest): Response<EnrollmentResponse>

    @GET("enrollments/{id}")
    suspend fun getEnrollment(@Path("id") id: Int): Response<EnrollmentResponse>

    @PUT("enrollments/{id}")
    suspend fun updateEnrollment(@Path("id") id: Int, @Body enrollmentRequest: EnrollmentRequest): Response<EnrollmentResponse>

    @DELETE("enrollments/{id}")
    suspend fun deleteEnrollment(@Path("id") id: Int): Response<ApiMessage>

    // PAYMENTS
    @GET("payments")
    suspend fun getPayments(): Response<List<PaymentResponse>>

    @GET("my-payments")
    suspend fun getMyPayments(): Response<List<PaymentResponse>>

    @POST("checkout-payment")
    suspend fun checkoutPayment(@Body checkoutRequest: CheckoutRequest): Response<CheckoutResponse>

    @POST("verify-payment")
    suspend fun verifyPayment(@Body verifyRequest: VerifyRequest): Response<VerifyResponse>

    // DEPARTMENTS
    @GET("departments")
    suspend fun getDepartments(): Response<List<DepartmentResponse>>

    @POST("departments")
    suspend fun createDepartment(@Body departmentRequest: DepartmentRequest): Response<DepartmentResponse>

    @GET("departments/{id}")
    suspend fun getDepartment(@Path("id") id: Int): Response<DepartmentResponse>

    @PUT("departments/{id}")
    suspend fun updateDepartment(@Path("id") id: Int, @Body departmentRequest: DepartmentRequest): Response<DepartmentResponse>

    @DELETE("departments/{id}")
    suspend fun deleteDepartment(@Path("id") id: Int): Response<ApiMessage>

    // COURSES
    @GET("courses")
    suspend fun getCourses(): Response<List<CourseResponse>>

    @POST("courses")
    suspend fun createCourse(@Body courseRequest: CourseRequest): Response<CourseResponse>

    @GET("courses/{course}")
    suspend fun getCourse(@Path("course") course: Int): Response<CourseResponse>

    @PUT("courses/{course}")
    suspend fun updateCourse(@Path("course") course: Int, @Body courseRequest: CourseRequest): Response<CourseResponse>

    @DELETE("courses/{course}")
    suspend fun deleteCourse(@Path("course") course: Int): Response<ApiMessage>

    // NEWS (ADMIN)


    // FACULTIES
    @GET("faculties")
    suspend fun getFaculties(): Response<List<FacultyResponse>>

    @POST("faculties")
    suspend fun createFaculty(@Body facultyRequest: FacultyRequest): Response<FacultyResponse>

    @GET("faculties/{faculty}")
    suspend fun getFaculty(@Path("faculty") faculty: Int): Response<FacultyResponse>

    @PUT("faculties/{faculty}")
    suspend fun updateFaculty(@Path("faculty") faculty: Int, @Body facultyRequest: FacultyRequest): Response<FacultyResponse>

    @DELETE("faculties/{faculty}")
    suspend fun deleteFaculty(@Path("faculty") faculty: Int): Response<ApiMessage>

    // MAJORS
    @GET("majors")
    suspend fun getMajors(): Response<List<MajorResponse>>

    @POST("majors")
    suspend fun createMajor(@Body majorRequest: MajorRequest): Response<MajorResponse>

    @GET("majors/{major}")
    suspend fun getMajor(@Path("major") major: Int): Response<MajorResponse>

    @PUT("majors/{major}")
    suspend fun updateMajor(@Path("major") major: Int, @Body majorRequest: MajorRequest): Response<MajorResponse>

    @DELETE("majors/{major}")
    suspend fun deleteMajor(@Path("major") major: Int): Response<ApiMessage>



    @DELETE("personal-informations/{personalInformation}")
    suspend fun deletePersonalInformation(@Path("personalInformation") id: Int): Response<ApiMessage>


    // STUDENT CARDS
    @GET("student-cards")
    suspend fun getStudentCards(): Response<List<StudentCardResponse>>

    @POST("student-cards")
    suspend fun createStudentCard(@Body studentCardRequest: StudentCardRequest): Response<StudentCardResponse>

    @GET("student-cards/{studentCard}")
    suspend fun getStudentCard(@Path("studentCard") id: Int): Response<StudentCardResponse>

    @PUT("student-cards/{studentCard}")
    suspend fun updateStudentCard(@Path("studentCard") id: Int, @Body studentCardRequest: StudentCardRequest): Response<StudentCardResponse>

    @DELETE("student-cards/{studentCard}")
    suspend fun deleteStudentCard(@Path("studentCard") id: Int): Response<ApiMessage>




    // ENROLLMENT
    @POST("student/enroll")
    suspend fun enroll(@Body enrollRequest: EnrollmentRequest): Response<EnrollmentResponse>

    @GET("student/enrollments")
    suspend fun getMyEnrollments(): Response<List<EnrollmentResponse>>

    // PAYMENTS
    @POST("student/payment/checkout")
    suspend fun studentCheckoutPayment(@Body checkoutRequest: CheckoutRequest): Response<CheckoutResponse>

    @POST("student/payment/verify")
    suspend fun studentVerifyPayment(@Body verifyRequest: VerifyRequest): Response<VerifyResponse>

    @GET("student/payments")
    suspend fun getStudentPayments(): Response<List<PaymentResponse>>

    // STUDENT CARD
    @GET("student/card")
    suspend fun getCard(): Response<StudentCardResponse>

    @Multipart
    @POST("student/card")
    suspend fun uploadCard(
        @Part("front_image") frontImage: okhttp3.MultipartBody.Part,
        @Part("back_image") backImage: okhttp3.MultipartBody.Part
    ): Response<StudentCardResponse>

    // PROFILE
    @GET("student/profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @PUT("student/profile")
    suspend fun updateProfile(@Body profileUpdate: ProfileUpdateRequest): Response<ApiMessage>


    // ENROLLMENT COURSES
    @GET("student/enrollment-courses")
    suspend fun getEnrollmentCourses(): Response<List<EnrollmentCourseResponse>>

    @POST("student/enrollment-courses")
    suspend fun createEnrollmentCourse(@Body enrollmentCourseRequest: EnrollmentCourseRequest): Response<EnrollmentCourseResponse>

    @GET("student/enrollment-courses/{enrollmentCourse}")
    suspend fun getEnrollmentCourse(@Path("enrollmentCourse") id: Int): Response<EnrollmentCourseResponse>

    @PUT("student/enrollment-courses/{enrollmentCourse}")
    suspend fun updateEnrollmentCourse(@Path("enrollmentCourse") id: Int, @Body enrollmentCourseRequest: EnrollmentCourseRequest): Response<EnrollmentCourseResponse>

    @DELETE("student/enrollment-courses/{enrollmentCourse}")
    suspend fun deleteEnrollmentCourse(@Path("enrollmentCourse") id: Int): Response<ApiMessage>

    // STUDENT SPECIFIC ENDPOINTS
    @GET("student/class-schedule")
    suspend fun getClassSchedule(): Response<List<CourseResponse>>

    @GET("student/scores")
    suspend fun getStudentScores(): Response<List<EnrollmentCourseResponse>>

    @GET("news")
    suspend fun getNews(): Response<List<News>>
}