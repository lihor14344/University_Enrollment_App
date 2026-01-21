package com.example.enrollment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.enrollment.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class StudentViewModel : ViewModel() {
    private val repository = StudentRepository()

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState: StateFlow<ProfileState> = _profileState

    private val _enrollmentsState = MutableStateFlow<EnrollmentsState>(EnrollmentsState.Idle)
    val enrollmentsState: StateFlow<EnrollmentsState> = _enrollmentsState

    private val _enrollmentCoursesState = MutableStateFlow<EnrollmentCoursesState>(EnrollmentCoursesState.Idle)
    val enrollmentCoursesState: StateFlow<EnrollmentCoursesState> = _enrollmentCoursesState

    private val _studentCardState = MutableStateFlow<StudentCardState>(StudentCardState.Idle)
    val studentCardState: StateFlow<StudentCardState> = _studentCardState

    private val _updateProfileState = MutableStateFlow<UpdateProfileState>(UpdateProfileState.Idle)
    val updateProfileState: StateFlow<UpdateProfileState> = _updateProfileState

    private val _enrollState = MutableStateFlow<EnrollState>(EnrollState.Idle)
    val enrollState: StateFlow<EnrollState> = _enrollState

    private val _createEnrollmentCourseState = MutableStateFlow<CreateEnrollmentCourseState>(CreateEnrollmentCourseState.Idle)
    val createEnrollmentCourseState: StateFlow<CreateEnrollmentCourseState> = _createEnrollmentCourseState

    private val _uploadCardState = MutableStateFlow<UploadCardState>(UploadCardState.Idle)
    val uploadCardState: StateFlow<UploadCardState> = _uploadCardState

    private val _checkoutState = MutableStateFlow<CheckoutState>(CheckoutState.Idle)
    val checkoutState: StateFlow<CheckoutState> = _checkoutState

    private val _verifyPaymentState = MutableStateFlow<VerifyPaymentState>(VerifyPaymentState.Idle)
    val verifyPaymentState: StateFlow<VerifyPaymentState> = _verifyPaymentState

    private val _paymentsState = MutableStateFlow<PaymentsState>(PaymentsState.Idle)
    val paymentsState: StateFlow<PaymentsState> = _paymentsState

    fun fetchProfile() {
        _profileState.value = ProfileState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getProfile()
                if (response.isSuccessful) {
                    _profileState.value = ProfileState.Success(response.body()!!)
                } else {
                    _profileState.value = ProfileState.Error("Failed to fetch profile: ${response.message()}")
                }
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error("Network error: ${e.message}")
            }
        }
    }

    fun fetchEnrollments() {
        _enrollmentsState.value = EnrollmentsState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getEnrollments()
                if (response.isSuccessful) {
                    _enrollmentsState.value = EnrollmentsState.Success(response.body()!!)
                } else {
                    _enrollmentsState.value = EnrollmentsState.Error("Failed to fetch enrollments: ${response.message()}")
                }
            } catch (e: Exception) {
                _enrollmentsState.value = EnrollmentsState.Error("Network error: ${e.message}")
            }
        }
    }

    fun fetchEnrollmentCourses() {
        _enrollmentCoursesState.value = EnrollmentCoursesState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getEnrollmentCourses()
                if (response.isSuccessful) {
                    _enrollmentCoursesState.value = EnrollmentCoursesState.Success(response.body()!!)
                } else {
                    _enrollmentCoursesState.value = EnrollmentCoursesState.Error("Failed to fetch enrollment courses: ${response.message()}")
                }
            } catch (e: Exception) {
                _enrollmentCoursesState.value = EnrollmentCoursesState.Error("Network error: ${e.message}")
            }
        }
    }

    fun fetchStudentCard() {
        _studentCardState.value = StudentCardState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getStudentCard()
                if (response.isSuccessful) {
                    _studentCardState.value = StudentCardState.Success(response.body()!!)
                } else {
                    _studentCardState.value = StudentCardState.Error("Failed to fetch student card: ${response.message()}")
                }
            } catch (e: Exception) {
                _studentCardState.value = StudentCardState.Error("Network error: ${e.message}")
            }
        }
    }

    fun updateProfile(profileUpdate: ProfileUpdateRequest) {
        _updateProfileState.value = UpdateProfileState.Loading
        viewModelScope.launch {
            try {
                val response = repository.updateProfile(profileUpdate)
                if (response.isSuccessful) {
                    _updateProfileState.value = UpdateProfileState.Success(response.body()!!)
                } else {
                    _updateProfileState.value = UpdateProfileState.Error("Failed to update profile: ${response.message()}")
                }
            } catch (e: Exception) {
                _updateProfileState.value = UpdateProfileState.Error("Network error: ${e.message}")
            }
        }
    }

    fun enroll(enrollmentRequest: EnrollmentRequest) {
        _enrollState.value = EnrollState.Loading
        viewModelScope.launch {
            try {
                val response = repository.enroll(enrollmentRequest)
                if (response.isSuccessful) {
                    _enrollState.value = EnrollState.Success(response.body()!!)
                } else {
                    _enrollState.value = EnrollState.Error("Failed to enroll: ${response.message()}")
                }
            } catch (e: Exception) {
                _enrollState.value = EnrollState.Error("Network error: ${e.message}")
            }
        }
    }

    fun createEnrollmentCourse(enrollmentCourseRequest: EnrollmentCourseRequest) {
        _createEnrollmentCourseState.value = CreateEnrollmentCourseState.Loading
        viewModelScope.launch {
            try {
                val response = repository.createEnrollmentCourse(enrollmentCourseRequest)
                if (response.isSuccessful) {
                    _createEnrollmentCourseState.value = CreateEnrollmentCourseState.Success(response.body()!!)
                } else {
                    _createEnrollmentCourseState.value = CreateEnrollmentCourseState.Error("Failed to create enrollment course: ${response.message()}")
                }
            } catch (e: Exception) {
                _createEnrollmentCourseState.value = CreateEnrollmentCourseState.Error("Network error: ${e.message}")
            }
        }
    }

    fun uploadStudentCard(frontImage: MultipartBody.Part, backImage: MultipartBody.Part) {
        _uploadCardState.value = UploadCardState.Loading
        viewModelScope.launch {
            try {
                val response = repository.uploadStudentCard(frontImage, backImage)
                if (response.isSuccessful) {
                    _uploadCardState.value = UploadCardState.Success(response.body()!!)
                } else {
                    _uploadCardState.value = UploadCardState.Error("Failed to upload card: ${response.message()}")
                }
            } catch (e: Exception) {
                _uploadCardState.value = UploadCardState.Error("Network error: ${e.message}")
            }
        }
    }

    fun checkoutPayment(checkoutRequest: CheckoutRequest) {
        _checkoutState.value = CheckoutState.Loading
        viewModelScope.launch {
            try {
                val response = repository.checkoutPayment(checkoutRequest)
                if (response.isSuccessful) {
                    _checkoutState.value = CheckoutState.Success(response.body()!!)
                } else {
                    _checkoutState.value = CheckoutState.Error("Failed to checkout: ${response.message()}")
                }
            } catch (e: Exception) {
                _checkoutState.value = CheckoutState.Error("Network error: ${e.message}")
            }
        }
    }

    fun verifyPayment(verifyRequest: VerifyRequest) {
        _verifyPaymentState.value = VerifyPaymentState.Loading
        viewModelScope.launch {
            try {
                val response = repository.verifyPayment(verifyRequest)
                if (response.isSuccessful) {
                    _verifyPaymentState.value = VerifyPaymentState.Success(response.body()!!)
                } else {
                    _verifyPaymentState.value = VerifyPaymentState.Error("Failed to verify payment: ${response.message()}")
                }
            } catch (e: Exception) {
                _verifyPaymentState.value = VerifyPaymentState.Error("Network error: ${e.message}")
            }
        }
    }

    fun fetchPayments() {
        _paymentsState.value = PaymentsState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getPayments()
                if (response.isSuccessful) {
                    _paymentsState.value = PaymentsState.Success(response.body()!!)
                } else {
                    _paymentsState.value = PaymentsState.Error("Failed to fetch payments: ${response.message()}")
                }
            } catch (e: Exception) {
                _paymentsState.value = PaymentsState.Error("Network error: ${e.message}")
            }
        }
    }
}

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(val profile: ProfileResponse) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

sealed class EnrollmentsState {
    object Idle : EnrollmentsState()
    object Loading : EnrollmentsState()
    data class Success(val enrollments: List<EnrollmentResponse>) : EnrollmentsState()
    data class Error(val message: String) : EnrollmentsState()
}

sealed class EnrollmentCoursesState {
    object Idle : EnrollmentCoursesState()
    object Loading : EnrollmentCoursesState()
    data class Success(val courses: List<EnrollmentCourseResponse>) : EnrollmentCoursesState()
    data class Error(val message: String) : EnrollmentCoursesState()
}

sealed class StudentCardState {
    object Idle : StudentCardState()
    object Loading : StudentCardState()
    data class Success(val card: StudentCardResponse) : StudentCardState()
    data class Error(val message: String) : StudentCardState()
}

sealed class UpdateProfileState {
    object Idle : UpdateProfileState()
    object Loading : UpdateProfileState()
    data class Success(val message: ApiMessage) : UpdateProfileState()
    data class Error(val message: String) : UpdateProfileState()
}

sealed class EnrollState {
    object Idle : EnrollState()
    object Loading : EnrollState()
    data class Success(val enrollment: EnrollmentResponse) : EnrollState()
    data class Error(val message: String) : EnrollState()
}

sealed class CreateEnrollmentCourseState {
    object Idle : CreateEnrollmentCourseState()
    object Loading : CreateEnrollmentCourseState()
    data class Success(val course: EnrollmentCourseResponse) : CreateEnrollmentCourseState()
    data class Error(val message: String) : CreateEnrollmentCourseState()
}

sealed class UploadCardState {
    object Idle : UploadCardState()
    object Loading : UploadCardState()
    data class Success(val card: StudentCardResponse) : UploadCardState()
    data class Error(val message: String) : UploadCardState()
}

sealed class CheckoutState {
    object Idle : CheckoutState()
    object Loading : CheckoutState()
    data class Success(val checkout: CheckoutResponse) : CheckoutState()
    data class Error(val message: String) : CheckoutState()
}

sealed class VerifyPaymentState {
    object Idle : VerifyPaymentState()
    object Loading : VerifyPaymentState()
    data class Success(val verify: VerifyResponse) : VerifyPaymentState()
    data class Error(val message: String) : VerifyPaymentState()
}

sealed class PaymentsState {
    object Idle : PaymentsState()
    object Loading : PaymentsState()
    data class Success(val payments: List<com.example.enrollment.model.payment.PaymentResponse>) : PaymentsState()
    data class Error(val message: String) : PaymentsState()
}