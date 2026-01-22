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
import com.example.enrollment.model.student.EnrollmentResponse
import com.example.enrollment.ui.viewmodel.StudentViewModel
import com.example.enrollment.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnrollmentsScreen(navController: NavController) {
    val authPreferences = remember { AuthPreferences(navController.context) }
    val studentRepository = remember { StudentRepository(authPreferences) }
    val authRepository = remember { AuthRepository(authPreferences) }
    val viewModel = remember { StudentViewModel(studentRepository, authRepository) }

    val enrollmentsState by viewModel.enrollmentsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadEnrollments()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Enrollments") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {

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
            when (enrollmentsState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    val enrollments = (enrollmentsState as UiState.Success<List<EnrollmentResponse>>).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(enrollments) { enrollment ->
                            EnrollmentItem(enrollment)
                        }
                    }
                }
                is UiState.Error -> {
                    val error = (enrollmentsState as UiState.Error).message
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error: $error", color = Color.Red)
                        Button(onClick = { viewModel.loadEnrollments() }) {
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
fun EnrollmentItem(enrollment: EnrollmentResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

    }
}