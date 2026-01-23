package com.example.enrollment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.*
import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.ui.Auth.AuthScreen
import com.example.enrollment.ui.screens.*

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val authPreferences = remember { AuthPreferences(navController.context) }
    val isLoggedIn = authPreferences.getCurrentToken() != null

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "home" else "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable("auth") {
            AuthScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("profile") {
            UserProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate("auth") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

   

        composable("enrollments") {
            EnrollmentsScreen(navController)
        }

        composable("my_class") {
            MyClassScreen(navController)
        }

        composable("courses") {
            PlaceholderScreen(navController, "Courses")
        }

        composable("payments") {
            PaymentsScreen(navController)
        }

        composable("student_card") {
            StudentCardScreen(navController)
        }

        composable("news") {
            NewsScreen(navController)
        }

        composable("faculty_selection") {
            FacultySelectionScreen(navController)
        }

        composable("major_selection/{facultyId}") { backStackEntry ->
            val facultyId = backStackEntry.arguments?.getString("facultyId")?.toIntOrNull() ?: 1
            MajorSelectionScreen(navController, facultyId)
        }

        composable("course_selection/{majorId}") { backStackEntry ->
            val majorId = backStackEntry.arguments?.getString("majorId")?.toIntOrNull() ?: 1
            CourseSelectionScreen(navController, majorId)
        }

        composable("payment_checkout") {
            PaymentCheckoutScreen(navController)
        }

        composable("payment_success") {
            PaymentSuccessScreen(navController)
        }

        composable("qr_scanner") {
            QRCodeScanner(navController) { scannedData ->
                // Handle scanned QR code data
                // This would typically navigate back to payment screen with the data
            }
        }
        }


    }





