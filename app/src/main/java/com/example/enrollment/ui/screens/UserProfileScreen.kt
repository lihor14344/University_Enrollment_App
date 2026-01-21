@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.enrollment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.enrollment.data.AuthPreferences
import com.example.enrollment.data.repository.AuthRepository
import com.example.enrollment.data.repository.StudentRepository
import com.example.enrollment.model.profile.ProfileResponse
import com.example.enrollment.ui.viewmodel.StudentViewModel
import com.example.enrollment.ui.viewmodel.UiState




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(onBack: () -> Unit, onLogout: () -> Unit) {
    val authPreferences = remember { AuthPreferences(navController = null) } // We don't need navController here
    val studentRepository = remember { StudentRepository(authPreferences) }
    val authRepository = remember { AuthRepository(authPreferences) }
    val viewModel = remember { StudentViewModel(studentRepository, authRepository) }

    val profileState by viewModel.profileState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
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
            when (profileState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    val profile = (profileState as UiState.Success<ProfileResponse>).data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Profile Info
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Name: ${profile.name}", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Email: ${profile.email}", style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Phone: ${profile.phone ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Logout Button
                        Button(
                            onClick = { onLogout() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Logout", color = Color.White)
                        }
                    }
                }
                is UiState.Error -> {
                    val error = (profileState as UiState.Error).message
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error: $error", color = Color.Red)
                        Button(onClick = { viewModel.loadProfile() }) {
                            Text("Retry")
                        }
                    }
                }
                UiState.Idle -> {}
            }
        }
    }
}


