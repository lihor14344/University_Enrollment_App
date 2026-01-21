package com.example.enrollment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enrollment.model.profile.ProfileResponse
import com.example.enrollment.model.student.EnrollmentCourseResponse
import com.example.enrollment.model.student.EnrollmentResponse
import com.example.enrollment.model.student.StudentCardResponse
import com.example.enrollment.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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