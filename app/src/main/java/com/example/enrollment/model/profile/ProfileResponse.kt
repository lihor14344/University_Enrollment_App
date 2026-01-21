package com.example.enrollment.model.profile

import com.example.enrollment.model.user.User
import com.example.enrollment.model.student.Student
import com.example.enrollment.model.academicInfo.AcademicInformation
import com.example.enrollment.model.academicInfo.PersonalInformation

data class ProfileResponse(
    val user: User,
    val student: Student,
    val personalInformation: PersonalInformation?,
    val academicInformation: AcademicInformation?
)
