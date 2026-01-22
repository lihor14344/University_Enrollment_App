package com.example.enrollment.data

import com.example.enrollment.model.academic.CourseResponse
import com.example.enrollment.model.common.News
import com.example.enrollment.model.payment.PaymentResponse
import com.example.enrollment.model.profile.ProfileResponse
import com.example.enrollment.model.student.EnrollmentCourseResponse
import com.example.enrollment.model.student.EnrollmentResponse

/**
 * Static fallback data used when API is not available
 * This helps the app work in offline mode or when the server is down
 */
object StaticData {

    // Sample profile data
    val sampleProfile = ProfileResponse(
        id = 1,
        name = "John Doe",
        email = "john.doe@student.rupp.edu.kh",
        phone = "+855 12 345 678",
        student_id = "2021001",
        major = "Computer Science"
    )

    // Sample courses
    val sampleCourses = listOf(
        CourseResponse(
            id = 1,
            name = "Mobile Application Development",
            description = "Learn to build Android apps using Kotlin",
            credits = 3,
            code = "MAD101"
        ),
        CourseResponse(
            id = 2,
            name = "Database Systems",
            description = "Introduction to database design and SQL",
            credits = 3,
            code = "DBS201"
        ),
        CourseResponse(
            id = 3,
            name = "Web Development",
            description = "Frontend and backend web development",
            credits = 3,
            code = "WEB301"
        )
    )

    // Sample enrollments
    val sampleEnrollments = listOf(
        EnrollmentResponse(
            id = 1,
            student_id = 1,
            course_id = 1,
            status = "enrolled",
            created_at = "2024-01-15",
            course = sampleCourses[0]
        ),
        EnrollmentResponse(
            id = 2,
            student_id = 1,
            course_id = 2,
            status = "enrolled",
            created_at = "2024-01-16",
            course = sampleCourses[1]
        )
    )

    // Sample payments
    val samplePayments = listOf(
        PaymentResponse(
            id = 1,
            student_id = 1,
            amount = 150.0,
            status = "completed",
            description = "Course enrollment fee",
            created_at = "2024-01-15"
        ),
        PaymentResponse(
            id = 2,
            student_id = 1,
            amount = 200.0,
            status = "pending",
            description = "Library fee",
            created_at = "2024-01-20"
        )
    )

    // Sample scores
    val sampleScores = listOf(
        EnrollmentCourseResponse(
            id = 1,
            enrollment_id = 1,
            score = 85.5,
            grade = "A",
            attendance_percentage = 95.0,
            present_days = 19,
            total_days = 20,
            course = sampleCourses[0]
        ),
        EnrollmentCourseResponse(
            id = 2,
            enrollment_id = 2,
            score = 78.0,
            grade = "B+",
            attendance_percentage = 88.0,
            present_days = 18,
            total_days = 20,
            course = sampleCourses[1]
        )
    )

    // Sample news
    val sampleNews = listOf(
        News(
            id = 1,
            title = "New Semester Registration Opens",
            content = "Registration for the new semester is now open. Students can enroll in courses through the portal.",
            created_at = "2024-01-20"
        ),
        News(
            id = 2,
            title = "Library Extended Hours",
            content = "The university library will remain open until midnight during exam week.",
            created_at = "2024-01-18"
        ),
        News(
            id = 3,
            title = "Career Fair Next Week",
            content = "Join us for the annual career fair featuring top companies from various industries.",
            created_at = "2024-01-16"
        )
    )

    // Sample class schedule
    val sampleClassSchedule = sampleCourses // Same as courses for simplicity
}