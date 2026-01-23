package com.example.enrollment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enrollment.data.repository.AuthRepository
import com.example.enrollment.data.repository.StudentRepository

class StudentViewModelFactory(
    private val studentRepository: StudentRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(studentRepository, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}