package com.example.enrollment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.enrollment.ui.navigation.AppNav
import com.example.enrollment.ui.screens.WelcomeScreen
import com.example.enrollment.ui.theme.EnrollmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnrollmentTheme {
                AppNav()
            }
        }
    }
}
