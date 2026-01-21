package com.example.enrollment.ui.Auth

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enrollment.viewmodel.AuthViewModel
import com.example.enrollment.viewmodel.AuthViewModelFactory
import com.example.enrollment.viewmodel.LoginState
import kotlinx.coroutines.launch


@Composable
fun SignInForm(navController: NavHostController, context: Context) {
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(context))
    val loginState by viewModel.loginState.collectAsState()
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Handle login state changes
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                // Store token or user data if needed
                // For now, just navigate to home
                navController.navigate("home") {
                    popUpTo("auth") { inclusive = true }
                }
            }
            is LoginState.Error -> {
                errorMessage = (loginState as LoginState.Error).message
            }
            else -> {}
        }
    }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(12.dp))

        errorMessage?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                errorMessage = null
                scope.launch {
                    viewModel.login(email, password)
                }
            },
            enabled = email.isNotBlank() && password.isNotBlank() && loginState !is LoginState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
        ) {
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
            } else {
                Text("Sign In")
            }
        }
    }
}






