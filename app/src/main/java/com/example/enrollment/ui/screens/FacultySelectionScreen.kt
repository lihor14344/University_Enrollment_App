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
import com.example.enrollment.model.academic.FacultyResponse
import com.example.enrollment.ui.viewmodel.StudentViewModel
import com.example.enrollment.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultySelectionScreen(navController: NavController) {
    val authPreferences = remember { AuthPreferences(navController.context) }
    val studentRepository = remember { StudentRepository(authPreferences) }
    val authRepository = remember { AuthRepository(authPreferences) }
    val viewModel = remember { StudentViewModel(studentRepository, authRepository) }

    // For demo purposes, we'll use static data
    val faculties = listOf(
        FacultyResponse(id = 1, faculty_name = "Faculty of Science", faculty_code = "SCI"),
        FacultyResponse(id = 2, faculty_name = "Faculty of Engineering", faculty_code = "ENG"),
        FacultyResponse(id = 3, faculty_name = "Faculty of Business", faculty_code = "BUS")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Faculty") },
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
            items(faculties) { faculty ->
                FacultyItem(faculty) {
                    // Navigate to major selection with faculty ID
                    navController.navigate("major_selection/${faculty.id}")
                }
            }
        }
    }
}

@Composable
fun FacultyItem(faculty: FacultyResponse, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = faculty.faculty_name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Code: ${faculty.faculty_code}", style = MaterialTheme.typography.bodySmall)
        }
    }
}