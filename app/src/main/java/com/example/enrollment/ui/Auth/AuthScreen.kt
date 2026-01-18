package com.example.enrollment.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.enrollment.ui.Auth.SignInForm
import com.example.enrollment.ui.Auth.SignUpForm

@Composable
fun AuthScreen(navController: NavHostController) {
    var isSignUp by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Back Arrow + Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { /* Optional: handle back */ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1A237E)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (isSignUp) "Create Your Account" else "Log in to your Account",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A237E)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "Go ahead and set up your account.\nSign in/up to enjoy the best experience.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Toggle Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { isSignUp = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSignUp) Color(0xFF1A237E) else Color.LightGray
                        )
                    ) { Text("Sign Up") }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = { isSignUp = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isSignUp) Color(0xFF1A237E) else Color.LightGray
                        )
                    ) { Text("Sign In") }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Forms
                if (isSignUp) {
                    SignUpForm(navController)
                } else {
                    SignInForm(navController)
                }
            }
        }
    }
}





//package com.example.enrollment.ui.screens
//
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import com.example.enrollment.ui.Auth.SignInForm
//import com.example.enrollment.ui.Auth.SignUpForm
//
//@Composable
//fun AuthScreen(navController: NavHostController) {
//    var isSignUp by remember { mutableStateOf(true) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF5F5F5)),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(24.dp),
//            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Column(
//                modifier = Modifier.padding(24.dp)
//            ) {
//                // Row for Back Arrow + Title
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(
//                        onClick = { /* Handle back click */ },
//                        modifier = Modifier.size(32.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color(0xFF1A237E)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    Text(
//                        text = if (isSignUp) "Create Your Account" else "Log in to your Account",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF1A237E)
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Subtitle
//                Text(
//                    text = "Go ahead and set up your account.\nSign in/up to enjoy the best experience.",
//                    fontSize = 14.sp,
//                    color = Color.Gray,
//                    lineHeight = 18.sp,
//                    modifier = Modifier.padding(vertical = 8.dp),
//                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                AuthToggle(
//                    isSignUp = isSignUp,
//                    onSignUpClick = { isSignUp = true },
//                    onSignInClick = { isSignUp = false }
//                )
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                if (isSignUp) {
//                    SignUpForm(navController)
//                } else {
//                    SignInForm(navController)
//                }
//            }
//        }
//    }
//}
//
