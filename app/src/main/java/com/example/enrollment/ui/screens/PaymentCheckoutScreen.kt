package com.example.enrollment.ui.screens

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
import com.example.enrollment.model.payment.CheckoutRequest
import com.example.enrollment.ui.viewmodel.StudentViewModel
import com.example.enrollment.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCheckoutScreen(navController: NavController) {
    val authPreferences = remember { AuthPreferences(navController.context) }
    val studentRepository = remember { StudentRepository(authPreferences) }
    val authRepository = remember { AuthRepository(authPreferences) }
    val viewModel = remember { StudentViewModel(studentRepository, authRepository) }

    // For demo purposes, use sample selected courses
    val selectedCourses = listOf(
        CourseResponse(id = 1, major_id = 1, code = "MAD101", name = "Mobile Application Development", credit = 3, price = 150.0),
        CourseResponse(id = 2, major_id = 1, code = "DBS201", name = "Database Systems", credit = 3, price = 120.0)
    )

    val totalAmount = selectedCourses.sumOf { it.price ?: 0.0 }
    val checkoutState by viewModel.checkoutState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment Checkout") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Selected courses list
            Text("Selected Courses", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedCourses) { course ->
                    CourseCheckoutItem(course)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total and payment options
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Amount:", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "$${"%.2f".format(totalAmount)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF1E40AF)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Payment Method", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Payment method options
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Handle card payment */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("💳 Card")
                        }
                        OutlinedButton(
                            onClick = {
                                navController.navigate("qr_scanner")
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("📱 QR Code")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Create checkout request
                            val checkoutRequest = CheckoutRequest(
                                amount = totalAmount,
                                description = "Course enrollment payment",
                                courses = selectedCourses.map { it.id }
                            )
                            viewModel.checkoutPayment(checkoutRequest)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = checkoutState !is UiState.Loading
                    ) {
                        if (checkoutState is UiState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White
                            )
                        } else {
                            Text("Complete Payment")
                        }
                    }
                }
            }

            // Handle checkout result
            when (checkoutState) {
                is UiState.Success -> {
                    LaunchedEffect(Unit) {
                        // Navigate to success screen
                        navController.navigate("payment_success")
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = "Payment failed: ${(checkoutState as UiState.Error).message}",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
fun CourseCheckoutItem(course: CourseResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = course.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${course.credit} credits", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "$${course.price}",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF1E40AF)
            )
        }
    }
}