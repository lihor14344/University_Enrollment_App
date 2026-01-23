package com.example.enrollment.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.enrollment.R
import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.data.repository.AuthRepository
import com.example.enrollment.data.repository.StudentRepository
import com.example.enrollment.ui.viewmodel.StudentViewModel
import com.example.enrollment.ui.viewmodel.StudentViewModelFactory
import com.example.enrollment.ui.viewmodel.UiState
import com.example.enrollment.model.profile.ProfileResponse




@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val authPreferences = remember { AuthPreferences(context) }
    val authRepository = remember { AuthRepository(authPreferences) }
    val studentRepository = remember { StudentRepository(authPreferences) }
    val studentViewModel: StudentViewModel = viewModel(factory = StudentViewModelFactory(studentRepository, authRepository))
    val profileState by studentViewModel.profileState.collectAsState()
    val prefs = remember { AuthPreferences(context) }

    LaunchedEffect(Unit) {
        studentViewModel.loadProfile()
    }

    // Auto logout on 401
    LaunchedEffect(profileState) {
        if (profileState is UiState.Error && (profileState as UiState.Error).message.contains("Unauthorized")) {
            prefs.clearAuthToken()
            navController.navigate("auth") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /* ---------- TOP BAR ---------- */
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.rupp_logo),
//                        contentDescription = "Logo",
//                        modifier = Modifier.size(36.dp)
//                    )
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    Text(
//                        text = "Rupper application",
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = Color(0xFF1A237E),
//                        modifier = Modifier.weight(1f)
//                    )
//
//                    Text(
//                        text = "Ratha Phaeron",
//                        fontSize = 12.sp,
//                        color = Color.Gray
//                    )
//                    Text(
//                        text = "Ratha Phaeron",
//                        fontSize = 12.sp,
//                        color = Color.Gray,
//                        modifier = Modifier.clickable {
//                            navController.navigate("profile") // ✅ Navigate to UserProfileScreen
//                        }
//                    )
//
//                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rupp_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Rupper application",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1A237E),
                        modifier = Modifier.weight(1f)
                    )

                    // ✅ Profile area
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            navController.navigate("profile")
                        }
                    ) {
                        // Optional: small avatar circle
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            when (profileState) {
                                is UiState.Success -> {
                                    val profile = (profileState as UiState.Success<ProfileResponse>).data
                                    val user = profile.user
                                    val name = user?.name ?: "User"
                                    val initial = name.firstOrNull()?.toString() ?: "U"
                                    Text(initial, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                else -> Text("U", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        when (profileState) {
                            is UiState.Success -> {
                                val profile = (profileState as UiState.Success<ProfileResponse>).data
                                val user = profile.user
                                val name = user?.name ?: "User"
                                Text(
                                    text = name,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            is UiState.Loading -> {
                                Text(
                                    text = "Loading...",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            is UiState.Error -> {
                                Text(
                                    text = (profileState as UiState.Error).message,
                                    fontSize = 12.sp,
                                    color = Color.Red
                                )
                            }
                            else -> {
                                Text(
                                    text = "User",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                /* ---------- BANNER IMAGE ---------- */
                Image(
                    painter = painterResource(id = R.drawable.rupp_banner),
                    contentDescription = "Campus",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                /* ---------- MENU GRID ---------- */
                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Connect My Class to actual screen
                        HomeMenuItem("My Class", R.drawable.ic_class) {
                            navController.navigate("my_class")
                        }
                        HomeMenuItem("Enrollment", R.drawable.ic_enroll) {
                            navController.navigate("enrollments")
                        }
                        HomeMenuItem("News", R.drawable.ic_news) {
                            navController.navigate("news")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        HomeMenuItem("Card", R.drawable.ic_card) {
                            navController.navigate("student_card")
                        }
                        HomeMenuItem("Enrollments", R.drawable.ic_enroll) {
                            navController.navigate("enrollments")
                        }
                        HomeMenuItem("Payments", R.drawable.ic_payment) {
                            navController.navigate("payments")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        HomeMenuItem("Courses", R.drawable.ic_class) {
                            navController.navigate("courses")
                        }
                        HomeMenuItem("Payments", R.drawable.ic_enroll) {
                            navController.navigate("payments")
                        }
                        HomeMenuItem("Logout", R.drawable.ic_news) {
                            prefs.clearAuthToken()
                            navController.navigate("auth") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun HomeMenuItem(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A237E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}