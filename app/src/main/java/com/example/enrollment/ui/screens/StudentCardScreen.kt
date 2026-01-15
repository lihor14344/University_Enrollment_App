package com.example.enrollment.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.*
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StudentCardUploadScreen(navController: NavController) {
    var state by remember { mutableStateOf(UploadState.UPLOAD) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E40AF))
                .padding(horizontal = 16.dp, vertical = 12.dp)
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
                text = "Student Card",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedContent(targetState = state, transitionSpec = {
                fadeIn().togetherWith(fadeOut())
            }) { currentState ->
                if (currentState == UploadState.UPLOAD) {
                    UploadContent { state = UploadState.SUCCESS }
                } else {
                    SuccessContent {
                        state = UploadState.UPLOAD
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun UploadContent(onSubmit: () -> Unit) {
    // State to store front and back image URIs
    var frontImageUri by remember { mutableStateOf<Uri?>(null) }
    var backImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(40.dp))
                .background(Color.White)
                .padding(24.dp)
        ) {
            // Photo Guidelines
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF3B82F6)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "⚠ Photo Guidelines",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text("• Take photos in good lighting", color = Color.White.copy(0.9f), fontSize = 12.sp)
                        Text("• Ensure all text is clearly readable", color = Color.White.copy(0.9f), fontSize = 12.sp)
                        Text("• Avoid glare and shadows", color = Color.White.copy(0.9f), fontSize = 12.sp)
                        Text("• Place card on flat, contrasting surface", color = Color.White.copy(0.9f), fontSize = 12.sp)
                        Text("• Upload both front and back sides", color = Color.White.copy(0.9f), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Front Side Card
            UploadSideCard(title = "Front Side", imageUri = frontImageUri) {
                frontImageUri = it
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Back Side Card
            UploadSideCard(title = "Back Side", imageUri = backImageUri) {
                backImageUri = it
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button (disabled if images not uploaded)
            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = frontImageUri != null && backImageUri != null,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Submit for Review",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun UploadSideCard(
    title: String,
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    LocalContext.current

    // Launchers
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { _ ->
        // TODO: convert bitmap to Uri if needed
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E40AF)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color(0xFFBFDBFE), RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFE0F2FE))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                // Show uploaded image with "Change" button
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "$title Preview",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onImageSelected(Uri.EMPTY) }, // Reset image
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                    ) {
                        Text("Change Image", color = Color.White)
                    }
                }
            } else {
                // Show upload buttons
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.Image, contentDescription = "Image", tint = Color(0xFF60A5FA), modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Upload $title of your student card", fontSize = 12.sp, color = Color(0xFF6B7280))
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { cameraLauncher.launch() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                    ) {
                        Text("Take Photo", color = Color.White, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Choose from Gallery", color = Color(0xFF3B82F6), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SuccessContent(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(Color(0xFF16A34A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Check, contentDescription = "Success", tint = Color.White, modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Card Submitted!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E40AF))
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Your student card has been submitted for review. You will be notified once it's approved.",
            fontSize = 14.sp,
            color = Color(0xFF1E40AF).copy(alpha = 0.7f),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "Back to Home",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}

enum class UploadState {
    UPLOAD, SUCCESS
}
