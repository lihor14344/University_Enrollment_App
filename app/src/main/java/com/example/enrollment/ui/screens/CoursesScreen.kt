package com.example.enrollment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun CoursesScreen(navController: NavController) {
    val authPreferences = remember { AuthPreferences(navController.context) }
    val studentRepository = remember { StudentRepository(authPreferences) }
    val authRepository = remember { AuthRepository(authPreferences) }
    val viewModel = remember { StudentViewModel(studentRepository, authRepository) }

    val coursesState by viewModel.coursesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCourses()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Courses") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (coursesState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    val courses = (coursesState as UiState.Success<List<CourseResponse>>).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(courses) { course ->
                            CourseItem(course)
                        }
                    }
                }
                is UiState.Error -> {
                    val error = (coursesState as UiState.Error).message
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error: $error", color = Color.Red)
                        Button(onClick = { viewModel.loadCourses() }) {
                            Text("Retry")
                        }
                    }
                }
                UiState.Idle -> {}
            }
        }
    }
}

@Composable
fun CourseItem(course: CourseResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = course.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = course.description ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Credits: ${course.credits}", style = MaterialTheme.typography.bodySmall)
        }
    }
}