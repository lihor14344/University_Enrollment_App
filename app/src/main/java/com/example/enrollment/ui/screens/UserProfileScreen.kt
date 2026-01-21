@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.enrollment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.example.enrollment.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.enrollment.viewmodel.StudentViewModel
import com.example.enrollment.viewmodel.ProfileState
import com.example.enrollment.viewmodel.UpdateProfileState




@Composable
fun UserProfileScreen(onBack: () -> Boolean, onLogout: () -> Unit) {
    val studentViewModel: StudentViewModel = viewModel()
    val profileState by studentViewModel.profileState.collectAsState()
    val updateProfileState by studentViewModel.updateProfileState.collectAsState()
    val scrollState = rememberScrollState()
    var selectedTab by remember { mutableStateOf("personal") } // "personal" or "settings"

    LaunchedEffect(Unit) {
        studentViewModel.fetchProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF18181B))
            .verticalScroll(scrollState)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(375.dp)
                .background(Color.White)
                .padding(bottom = 16.dp)
        ) {
            // === Header ===
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E40AF))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBack() }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // === Profile Header ===
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3B82F6))
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Box(
//                    modifier = Modifier
//                        .size(96.dp)
//                        .clip(CircleShape)
//                        .background(Color(0xFFA3A3E0))
//                        .border(4.dp, Color.White.copy(alpha = 0.2f), CircleShape)
//                )

                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.White.copy(alpha = 0.2f), CircleShape),
                    contentScale = ContentScale.Crop
                )


                Spacer(modifier = Modifier.height(8.dp))
                Text("Phearom Ratha", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("ID: 03085830", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFF22C55E), shape = RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 2.dp)
                ) {
                    Text("Active", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileStat(title = "Year", value = "4", modifier = Modifier.weight(1f))
                    ProfileStat(title = "GPA", value = "3.75", hasBorder = true, modifier = Modifier.weight(1f))
                    ProfileStat(title = "Gen", value = "09", modifier = Modifier.weight(1f))
                }
            }

            // === Tabs with full-height highlight ===
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                TabButton(
                    title = "Personal Info",
                    isSelected = selectedTab == "personal",
                    modifier = Modifier.weight(1f)
                ) { selectedTab = "personal" }

                TabButton(
                    title = "Settings",
                    isSelected = selectedTab == "settings",
                    modifier = Modifier.weight(1f)
                ) { selectedTab = "settings" }
            }

            // === Content ===
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (selectedTab == "personal") {
                    // Personal Info
                    InfoCard(
                        title = "Personal Information",
                        items = listOf(
                            InfoItem("Email", "ratha.phearom@rupp.edu.kh", InfoType.EMAIL),
                            InfoItem("Phone", "+855 12 345 678", InfoType.PHONE),
                            InfoItem("Date of Birth", "May 15, 2002", InfoType.DATE_OF_BIRTH)
                        )
                    )

                    InfoCard(
                        title = "Academic Information",
                        items = listOf(
                            InfoItem("Faculty", "Faculty of Science", InfoType.FACULTY),
                            InfoItem("Major", "Computer Science", InfoType.MAJOR),
                            InfoItem("Enrollment Date", "September 1, 2021", InfoType.ENROLLMENT_DATE)
                        )
                    )

                    PaymentCard(lastDate = "01-05-2021", lastAmount = "$300")
                } else if (selectedTab == "settings") {
                    // === Settings UI ===
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SettingsCard(
                            icon = Icons.Filled.Notifications,
                            title = "Notification",
                            subtitle = "Manage notification preferences"
                        )
                        SettingsCard(
                            icon = Icons.Filled.Shield,
                            title = "Privacy & Security",
                            subtitle = "Password, Authentication"
                        )
                        SettingsCard(
                            icon = Icons.Filled.Settings,
                            title = "App Setting",
                            subtitle = "Language, Theme, Appearance"
                        )
                        SettingsCard(
                            icon = Icons.AutoMirrored.Filled.HelpOutline,
                            title = "Help & Support",
                            subtitle = "FAQs, Contact support"
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = { onLogout() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A0E54)),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text("Log out", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// === Profile Stat ===
@Composable
fun ProfileStat(title: String, value: String, hasBorder: Boolean = false, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.3f))
            .then(if (hasBorder) Modifier.border(1.dp, Color.White.copy(alpha = 0.1f)) else Modifier)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        Text(title.uppercase(), fontSize = 10.sp, color = Color.White.copy(alpha = 0.8f))
    }
}

// === Tab Button with full-height highlight ===
@Composable
fun TabButton(title: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
            .background(
                color = if (isSelected) Color(0xFFDBEAFE) else Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (isSelected) Color(0xFF4F46E5) else Color(0xFF9CA3AF),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

// === Settings Card ===
@Composable
fun SettingsCard(icon: ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0F2FE), RoundedCornerShape(16.dp))
            .padding(12.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.White, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = title, tint = Color(0xFF4F46E5))
        }
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF4F46E5))
            Text(subtitle, fontSize = 10.sp, color = Color(0xFF6B7280))
        }
    }
}

// === Icons ===
enum class InfoType {
    EMAIL, PHONE, DATE_OF_BIRTH, FACULTY, MAJOR, ENROLLMENT_DATE, LAST_PAYMENT_DATE, LAST_PAYMENT_AMOUNT
}

@Composable
fun InfoIcon(type: InfoType) {
    val icon = when (type) {
        InfoType.EMAIL -> Icons.Filled.Email
        InfoType.PHONE -> Icons.Filled.Phone
        InfoType.DATE_OF_BIRTH -> Icons.Filled.CalendarToday
        InfoType.FACULTY -> Icons.Filled.AccountBalance
        InfoType.MAJOR -> Icons.Filled.Book
        InfoType.ENROLLMENT_DATE -> Icons.Filled.Schedule
        InfoType.LAST_PAYMENT_DATE -> Icons.Filled.CalendarMonth
        InfoType.LAST_PAYMENT_AMOUNT -> Icons.Filled.AttachMoney
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = type.name, tint = Color(0xFF4F46E5))
    }
}

// === InfoCard ===
data class InfoItem(val label: String, val value: String, val iconType: InfoType)

@Composable
fun InfoCard(title: String, items: List<InfoItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0F2FE).copy(alpha = 0.6f), shape = RoundedCornerShape(24.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF4F46E5))

        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoIcon(item.iconType)
                Column {
                    Text(item.label.uppercase(), fontSize = 10.sp, color = Color(0xFF9CA3AF), fontWeight = FontWeight.SemiBold)
                    Text(item.value, fontSize = 12.sp, color = Color(0xFF4F46E5), fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// === Payment Card ===
@Composable
fun PaymentCard(lastDate: String, lastAmount: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0F2FE).copy(alpha = 0.6f), shape = RoundedCornerShape(24.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Payments", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF4F46E5))

        // Last Payment Date
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoIcon(InfoType.LAST_PAYMENT_DATE)
            Column {
                Text("Last Payment Date".uppercase(), fontSize = 10.sp, color = Color(0xFF9CA3AF), fontWeight = FontWeight.SemiBold)
                Text(lastDate, fontSize = 12.sp, color = Color(0xFF4F46E5), fontWeight = FontWeight.Medium)
            }
        }

        // Last Payment Amount
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoIcon(InfoType.LAST_PAYMENT_AMOUNT)
            Column {
                Text("Last Payment Amount".uppercase(), fontSize = 10.sp, color = Color(0xFF9CA3AF), fontWeight = FontWeight.SemiBold)
                Text(lastAmount, fontSize = 12.sp, color = Color(0xFF4F46E5), fontWeight = FontWeight.Medium)
            }
        }

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
        ) {
            Text("View Full Payment History", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}


