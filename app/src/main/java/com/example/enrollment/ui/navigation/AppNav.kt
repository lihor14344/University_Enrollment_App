package com.example.enrollment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.enrollment.ui.screens.*

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
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
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

   

        composable("enrollments") {
            EnrollmentsScreen(navController)
        }



        composable("payments") {
            PaymentsScreen(navController)
        }

        composable("scores") {
            ScoresScreen(navController)
        }

        composable("attendance") {
            AttendanceScreen(navController)
        }

        composable("student_card") {
            StudentCardScreen(navController)
        }

        composable("news") {
            NewsScreen(navController)
        }

        composable("qr_scanner") {
            QRCodeScanner(navController) { scannedData ->
                // Handle scanned QR code data
                // This would typically navigate back to payment screen with the data
            }
        }
        }


    }





