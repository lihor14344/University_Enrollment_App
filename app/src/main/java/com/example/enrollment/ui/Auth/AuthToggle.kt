package com.example.enrollment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun AuthToggle(
    isSignUp: Boolean,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        ToggleButton(
            text = "Sign Up",
            selected = isSignUp,
            onClick = onSignUpClick
        )

        ToggleButton(
            text = "Sign In",
            selected = !isSignUp,
            onClick = onSignInClick
        )
    }
}

@Composable
fun RowScope.ToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(50),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color.White else Color.Transparent
        )
    ) {
        Text(
            text = text,
            color = Color(0xFF1A237E),
            fontWeight = FontWeight.Medium
        )
    }
}
