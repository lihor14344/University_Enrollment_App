package com.example.enrollment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enrollment.data.repository.AuthRepository
import com.example.enrollment.data.repository.StudentRepository
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel(
    private val studentRepository: StudentRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    // Profile
    private val _profileState = MutableStateFlow<UiState<ProfileResponse>>(UiState.Loading)
    val profileState: StateFlow<UiState<ProfileResponse>> = _profileState

    // Courses
    private val _coursesState = MutableStateFlow<UiState<List<CourseResponse>>>(UiState.Loading)
    val coursesState: StateFlow<UiState<List<CourseResponse>>> = _coursesState

    // Enrollments
    private val _enrollmentsState = MutableStateFlow<UiState<List<EnrollmentResponse>>>(UiState.Loading)
    val enrollmentsState: StateFlow<UiState<List<EnrollmentResponse>>> = _enrollmentsState

    private val _enrollState = MutableStateFlow<UiState<EnrollmentResponse>>(UiState.Idle)
    val enrollState: StateFlow<UiState<EnrollmentResponse>> = _enrollState

    // Class Schedule
    private val _classScheduleState = MutableStateFlow<UiState<List<CourseResponse>>>(UiState.Loading)
    val classScheduleState: StateFlow<UiState<List<CourseResponse>>> = _classScheduleState

    // Payments
    private val _paymentsState = MutableStateFlow<UiState<List<PaymentResponse>>>(UiState.Loading)
    val paymentsState: StateFlow<UiState<List<PaymentResponse>>> = _paymentsState

    private val _checkoutState = MutableStateFlow<UiState<CheckoutResponse>>(UiState.Idle)
    val checkoutState: StateFlow<UiState<CheckoutResponse>> = _checkoutState

    private val _verifyState = MutableStateFlow<UiState<VerifyResponse>>(UiState.Idle)
    val verifyState: StateFlow<UiState<VerifyResponse>> = _verifyState

    // Scores
    private val _scoresState = MutableStateFlow<UiState<List<EnrollmentCourseResponse>>>(UiState.Loading)
    val scoresState: StateFlow<UiState<List<EnrollmentCourseResponse>>> = _scoresState

    // Attendance
    private val _attendanceState = MutableStateFlow<UiState<List<EnrollmentCourseResponse>>>(UiState.Loading)
    val attendanceState: StateFlow<UiState<List<EnrollmentCourseResponse>>> = _attendanceState

    // Student Card
    private val _studentCardState = MutableStateFlow<UiState<StudentCardResponse>>(UiState.Loading)
    val studentCardState: StateFlow<UiState<StudentCardResponse>> = _studentCardState

    // News
    private val _newsState = MutableStateFlow<UiState<List<News>>>(UiState.Loading)
    val newsState: StateFlow<UiState<List<News>>> = _newsState

    // Profile operations
    fun loadProfile() {
        _profileState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getProfile()
            _profileState.value = result.toUiState()
        }
    }

    fun updateProfile(request: ProfileUpdateRequest) {
        viewModelScope.launch {
            val result = studentRepository.updateProfile(request)
            // Reload profile after update
            if (result.isSuccess) {
                loadProfile()
            }
        }
    }

    // Courses operations
    fun loadCourses() {
        _coursesState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getCourses()
            _coursesState.value = result.toUiState()
        }
    }

    // Enrollment operations
    fun loadEnrollments() {
        _enrollmentsState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getEnrollments()
            _enrollmentsState.value = result.toUiState()
        }
    }

    fun enroll(request: EnrollmentRequest) {
        _enrollState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.enroll(request)
            _enrollState.value = result.toUiState()
            if (result.isSuccess) {
                loadEnrollments() // Refresh enrollments
            }
        }
    }

    // Class Schedule operations
    fun loadClassSchedule() {
        _classScheduleState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getClassSchedule()
            _classScheduleState.value = result.toUiState()
        }
    }

    // Payment operations
    fun loadPayments() {
        _paymentsState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getPayments()
            _paymentsState.value = result.toUiState()
        }
    }

    fun checkoutPayment(request: CheckoutRequest) {
        _checkoutState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.checkoutPayment(request)
            _checkoutState.value = result.toUiState()
        }
    }

    fun verifyPayment(request: VerifyRequest) {
        _verifyState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.verifyPayment(request)
            _verifyState.value = result.toUiState()
            if (result.isSuccess) {
                loadPayments() // Refresh payments
            }
        }
    }

    // Scores operations
    fun loadScores() {
        _scoresState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getScores()
            _scoresState.value = result.toUiState()
        }
    }

    // Attendance operations
    fun loadAttendance() {
        _attendanceState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getAttendance()
            _attendanceState.value = result.toUiState()
        }
    }

    // Student Card operations
    fun loadStudentCard() {
        _studentCardState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getStudentCard()
            _studentCardState.value = result.toUiState()
        }
    }

    // News operations
    fun loadNews() {
        _newsState.value = UiState.Loading
        viewModelScope.launch {
            val result = studentRepository.getNews()
            _newsState.value = result.toUiState()
        }
    }

    // Logout
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    // Helper function to convert Result to UiState
    private fun <T> Result<T>.toUiState(): UiState<T> {
        return fold(
            onSuccess = { UiState.Success(it) },
            onFailure = { UiState.Error(it.message ?: "Unknown error") }
        )
    }
}

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}