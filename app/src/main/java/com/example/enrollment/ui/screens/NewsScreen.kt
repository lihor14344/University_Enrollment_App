package com.example.enrollment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.enrollment.R

// ------------------ DATA MODEL ------------------
data class News(
    val title: String,
    val category: String,
    val date: String,
    val readTime: String,
    val imageRes: Int
)

// ------------------ SAMPLE DATA ------------------
val newsList = listOf(
    News("RUPP 65th Anniversary Celebration", "Events", "Today", "3 min read", R.drawable.rupp_banner),
    News("Semester Registration Announcement", "Announcements", "Yesterday", "2 min read", R.drawable.rupp_banner),
    News("Student Wins Coding Competition", "Achievements", "2 days ago", "4 min read", R.drawable.rupp_banner),
    News("Final Exam Schedule Released", "Academic", "3 days ago", "2 min read", R.drawable.rupp_banner),
    News("Guest Lecture on AI Technology", "Events", "4 days ago", "5 min read", R.drawable.rupp_banner),
    News("Library Extended Hours Notice", "Announcements", "5 days ago", "1 min read", R.drawable.rupp_banner)
)

// ------------------ MAIN SCREEN ------------------
@Composable
fun NewsScreen(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf("All News") }

    val categories = listOf(
        "All News",
        "Announcements",
        "Events",
        "Academic",
        "Achievements"
    )

    val filteredNews = if (selectedCategory == "All News") {
        newsList
    } else {
        newsList.filter { it.category == selectedCategory }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        // ---------- HEADER ----------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E40AF))
                .height(56.dp)
                .padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "University News",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- CATEGORY FILTER ----------
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            categories.forEach { category ->
                Button(
                    onClick = { selectedCategory = category },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == category) Color(0xFF4285F4) else Color.LightGray
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(category, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- NEWS LIST ----------
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(filteredNews) { news ->
                NewsCard(news, navController)
            }
        }
    }
}

// ------------------ NEWS CARD ------------------
@Composable
fun NewsCard(news: News, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // TODO: navigate to NewsDetailScreen later
            }
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                news.category,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = categoryColor(news.category)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                news.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A237E)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text("${news.date} • ${news.readTime}", fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = news.imageRes),
                contentDescription = news.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

// ------------------ CATEGORY COLOR ------------------
fun categoryColor(category: String): Color {
    return when (category) {
        "Announcements" -> Color(0xFF1E88E5) // Blue
        "Events" -> Color(0xFF8E24AA)        // Purple
        "Academic" -> Color(0xFF2E7D32)      // Green
        "Achievements" -> Color(0xFFF9A825) // Yellow
        else -> Color.Gray
    }
}
