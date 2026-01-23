package com.example.enrollment.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.data.repository.AuthRepository
import com.example.enrollment.data.repository.StudentRepository
import com.example.enrollment.model.academic.CourseResponse
import com.example.enrollment.ui.viewmodel.StudentViewModel
import com.example.enrollment.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseSelectionScreen(navController: NavController, majorId: Int) {
    val authPreferences = remember { AuthPreferences(navController.context) }
    val studentRepository = remember { StudentRepository(authPreferences) }
    val authRepository = remember { AuthRepository(authPreferences) }
    val viewModel = remember { StudentViewModel(studentRepository, authRepository) }

    // Selected courses state
    val selectedCourses = remember { mutableStateListOf<CourseResponse>() }

    // For demo purposes, we'll use static data filtered by major
    val courses: List<CourseResponse> = when (majorId) {
        1 -> listOf( // Computer Science
            CourseResponse(id = 1, major_id = 1, code = "MAD101", name = "Mobile Application Development", credit = 3, price = 150.0),
            CourseResponse(id = 2, major_id = 1, code = "DBS201", name = "Database Systems", credit = 3, price = 120.0),
            CourseResponse(id = 3, major_id = 1, code = "WEB301", name = "Web Development", credit = 3, price = 130.0)
        )
        2 -> listOf( // Information Technology
            CourseResponse(id = 4, major_id = 2, code = "NET401", name = "Network Security", credit = 3, price = 140.0),
            CourseResponse(id = 5, major_id = 2, code = "CLD501", name = "Cloud Computing", credit = 3, price = 160.0),
            CourseResponse(id = 6, major_id = 2, code = "DAT601", name = "Data Analytics", credit = 3, price = 135.0)
        )
        else -> listOf(
            CourseResponse(id = 7, major_id = majorId, code = "PRG101", name = "Introduction to Programming", credit = 3, price = 100.0),
            CourseResponse(id = 8, major_id = majorId, code = "DSA201", name = "Data Structures", credit = 3, price = 110.0)
        )
    }

    // Calculate total fee
    val totalFee = selectedCourses.sumOf { it.price ?: 0.0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Courses") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Total Fee: $${"%.2f".format(totalFee)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF1E40AF)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if (selectedCourses.isNotEmpty()) {
                                // Navigate to payment with selected courses
                                navController.navigate("payment_checkout")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = selectedCourses.isNotEmpty()
                    ) {
                        Text("Proceed to Payment (${selectedCourses.size} courses)")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(courses) { course ->
                CourseSelectionItem(
                    course = course,
                    isSelected = selectedCourses.contains(course),
                    onToggleSelection = {
                        if (selectedCourses.contains(course)) {
                            selectedCourses.remove(course)
                        } else {
                            selectedCourses.add(course)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CourseSelectionItem(
    course: CourseResponse,
    isSelected: Boolean,
    onToggleSelection: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleSelection() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE3F2FD) else Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = course.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Credits: ${course.credit}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Price: $${course.price}", style = MaterialTheme.typography.bodyMedium)
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggleSelection() }
            )
        }
    }
}