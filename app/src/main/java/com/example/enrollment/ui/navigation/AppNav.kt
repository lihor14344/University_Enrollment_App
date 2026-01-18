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

        composable("placeholder/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            PlaceholderScreen(navController, title)
        }
        composable("my_class") {
            MyClassScreen(navController)
        }
        composable("enrollment") {
            EnrollmentScreen(navController)
        }
        composable("news") {
            NewsScreen(navController)
        }
        composable("student_card") {
            StudentCardUploadScreen(navController)
        }
        composable("profile") {
            UserProfileScreen(
                onBack = { navController.popBackStack() }, // Go back to HomeScreen
                onLogout = {
                    // Handle logout (e.g., navigate to auth)
                    navController.navigate("auth") {
                        popUpTo("home") { inclusive = true } // Clear HomeScreen from backstack
                    }
                }
            )
        }


    }
}




