package com.example.enrollment.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.enrollment.R




@Composable
fun HomeScreen(navController: NavController) {

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
                            Text("R", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Ratha Phaeron",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
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
                        HomeMenuItem("Profile", R.drawable.ic_profile) {
                            navController.navigate("profile")
                        }
                        HomeMenuItem("Courses", R.drawable.ic_course) {
                            navController.navigate("courses")
                        }
                        HomeMenuItem("Enrollments", R.drawable.ic_enroll) {
                            navController.navigate("enrollments")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        HomeMenuItem("Class Schedule", R.drawable.ic_class) {
                            navController.navigate("class_schedule")
                        }
                        HomeMenuItem("Payments", R.drawable.ic_payment) {
                            navController.navigate("payments")
                        }
                        HomeMenuItem("Scores", R.drawable.ic_score) {
                            navController.navigate("scores")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        HomeMenuItem("Student Card", R.drawable.ic_card) {
                            navController.navigate("student_card")
                        }
                        HomeMenuItem("News", R.drawable.ic_news) {
                            navController.navigate("news")
                        }
                        // Empty space for alignment
                        Spacer(modifier = Modifier.size(90.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logout button
                    Button(
                        onClick = {
                            // Handle logout - navigate to auth and clear backstack
                            navController.navigate("auth") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Logout", color = Color.White)
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

