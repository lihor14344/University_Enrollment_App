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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.data.repository.AuthRepository
import com.example.enrollment.data.repository.StudentRepository
import com.example.enrollment.model.academic.MajorResponse
import com.example.enrollment.ui.viewmodel.StudentViewModel
import com.example.enrollment.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MajorSelectionScreen(navController: NavController, facultyId: Int) {
    val authPreferences = remember { AuthPreferences(navController.context) }
    val studentRepository = remember { StudentRepository(authPreferences) }
    val authRepository = remember { AuthRepository(authPreferences) }
    val viewModel = remember { StudentViewModel(studentRepository, authRepository) }

    // For demo purposes, we'll use static data filtered by faculty
    val majors = when (facultyId) {
    1 -> listOf( // Faculty of Science
        MajorResponse(id = 1, major = "Computer Science", department_id = 1),
        MajorResponse(id = 2, major = "Information Technology", department_id = 1),
        MajorResponse(id = 3, major = "Mathematics", department_id = 1)
    )
    2 -> listOf( // Faculty of Engineering
        MajorResponse(id = 4, major = "Civil Engineering", department_id = 2),
        MajorResponse(id = 5, major = "Mechanical Engineering", department_id = 2),
        MajorResponse(id = 6, major = "Electrical Engineering", department_id = 2)
    )
    3 -> listOf( // Faculty of Business
        MajorResponse(id = 7, major = "Business Administration", department_id = 3),
        MajorResponse(id = 8, major = "Accounting", department_id = 3),
        MajorResponse(id = 9, major = "Finance", department_id = 3)
    )
    else -> emptyList()
}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Major") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(majors) { major ->
                MajorItem(major) {
                    // Navigate to course selection with major ID
                    navController.navigate("course_selection/${major.id}")
                }
            }
        }
    }
}

@Composable
fun MajorItem(major: MajorResponse, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = major.major, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "ID: ${major.id}", style = MaterialTheme.typography.bodySmall)
        }
    }
}