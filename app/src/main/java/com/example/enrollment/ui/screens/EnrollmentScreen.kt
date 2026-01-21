package com.example.enrollment.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnrollmentScreen(navController: NavHostController) {

    var selectedYear by remember { mutableStateOf(2) }
    var studentId by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    val faculties = listOf(
        "Faculty of Science",
        "Faculty of Social Sciences and Humanities",
        "Faculty of Engineering",
        "Faculty of Development Studies",
        "Faculty of Education",
        "Institute of Foreign Languages (IFL)",
        "Institute for International Studies and Public Policy"
    )
    var selectedFaculty by remember { mutableStateOf("") }
    var facultyExpanded by remember { mutableStateOf(false) }
    var studentClass by remember { mutableStateOf("") }
    val generations = listOf("05", "06", "07", "08", "09", "10", "11", "12")
    var selectedGeneration by remember { mutableStateOf("09") }
    val facultyMajors = mapOf(
        "Faculty of Science" to listOf(
            "Biology / General Biology",
            "Chemistry",
            "Biochemistry",
            "Physics",
            "Mathematics",
            "Computer Science and Engineering",
            "Environmental Science"
        ),
        "Faculty of Social Sciences and Humanities" to listOf(
            "Geography and Land Management",
            "History",
            "Khmer Literature",
            "Linguistics",
            "Media and Communication",
            "Tourism",
            "Philosophy",
            "Psychology",
            "Sociology",
            "Social Work",
            "International Business Management"
        ),
        "Faculty of Engineering" to listOf(
            "Bio-Engineering",
            "Information Technology Engineering",
            "Telecommunication and Electronic Engineering"
        ),
        "Faculty of Development Studies" to listOf(
            "Community Development",
            "Economic Development",
            "Natural Resource Management and Development",
            "Development Research"
        ),
        "Faculty of Education" to listOf(
            "Educational Studies",
            "Higher Education Management and Development",
            "Lifelong Learning"
        ),
        "Institute of Foreign Languages (IFL)" to listOf(
            "English",
            "French",
            "Japanese",
            "Korean",
            "Chinese",
            "Thai",
            "International Studies"
        ),
        "Institute for International Studies and Public Policy" to listOf(
            "International Relations",
            "Public Policy"
        )
    )

    var selectedMajor by remember { mutableStateOf("") }
    var majorExpanded by remember { mutableStateOf(false) }

    var selectedPayment by remember { mutableStateOf("Semester 1") }

    // Date picker state (MUST be here)
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var enrollmentDate by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF5F7FB))
    ) {

        /* ---------- HEADER ---------- */

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E40AF))
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Enrollment",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* ---------- PAYMENT HISTORY ---------- */

        SectionTitle("Payment History")

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Top row: Year + Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Chip(
                        text = "Year 1 · Full year",
                        color = Color(0xFFE3F2FD),
                        textColor = Color(0xFF1A237E)
                    )

                    Chip(
                        text = "PAID",
                        color = Color(0xFFE8F5E9),
                        textColor = Color(0xFF2E7D32)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Major
                Text(
                    text = "Computer Science and Engineering",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Date + Amount
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "15 Aug 2024",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "$1200",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF4285F4)
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- ACADEMIC YEAR ---------- */
        SectionTitle("Enrolling for Academic Year")

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..4).forEach { year ->
                YearBox(
                    year = year,
                    selected = selectedYear == year
                ) { selectedYear = year }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Student Information")

        OutlinedTextField(
            value = studentId,
            onValueChange = { studentId = it },
            label = { Text("Student ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )


        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- FACULTY / CLASS / GENERATION ---------- */
        SectionTitle("Enrollment Detail")

        Card(
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Faculty", fontSize = 12.sp)

                ExposedDropdownMenuBox(
                    expanded = facultyExpanded,
                    onExpandedChange = { facultyExpanded = !facultyExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedFaculty,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select your faculty") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = facultyExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = facultyExpanded,
                        onDismissRequest = { facultyExpanded = false }
                    ) {
                        faculties.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    selectedFaculty = it
                                    facultyExpanded = false
                                }
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(8.dp))

                Text("Major", fontSize = 12.sp)

                ExposedDropdownMenuBox(
                    expanded = majorExpanded,
                    onExpandedChange = { majorExpanded = !majorExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedMajor,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select your major") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = majorExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = majorExpanded,
                        onDismissRequest = { majorExpanded = false }
                    ) {
                        val majors = facultyMajors[selectedFaculty] ?: emptyList()
                        majors.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    selectedMajor = it
                                    majorExpanded = false
                                }
                            )
                        }
                    }
                }



                Text("Class", fontSize = 12.sp)

                OutlinedTextField(
                    value = studentClass,
                    onValueChange = { studentClass = it },
                    placeholder = { Text("Example: A3") },
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(12.dp))


                Text("Generation", fontSize = 12.sp)

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    generations.forEach { gen ->
                        Chip(
                            text = gen,
                            color = if (selectedGeneration == gen) Color(0xFF4285F4) else Color.White,
                            textColor = if (selectedGeneration == gen) Color.White else Color.Black,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable { selectedGeneration = gen }
                        )
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        PriceCard(
            title = "Semester 1 only",
            subtitle = "January - May 2026",
            price = "$600",
            selected = selectedPayment == "Semester 1"
        ) {
            selectedPayment = "Semester 1"
        }

        PriceCard(
            title = "Semester 2 only",
            subtitle = "June - October 2026",
            price = "$600",
            selected = selectedPayment == "Semester 2"
        ) {
            selectedPayment = "Semester 2"
        }

        PriceCard(
            title = "Full Academic Year",
            subtitle = "Both Semester 1 & 2",
            price = "$1200",
            selected = selectedPayment == "Full Year"
        ) {
            selectedPayment = "Full Year"
        }



        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- ENROLLMENT DATE ---------- */
        SectionTitle("Enrollment Date")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { showDatePicker = true }
        ) {
            OutlinedTextField(
                value = enrollmentDate,
                onValueChange = {},
                enabled = false,   // 👈 IMPORTANT
                placeholder = { Text("dd / MM / yyyy") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },

                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                enrollmentDate = java.text.SimpleDateFormat(
                                    "dd / MM / yyyy",
                                    java.util.Locale.getDefault()
                                ).format(java.util.Date(millis))
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },

                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }


        Spacer(modifier = Modifier.height(24.dp))



        SectionTitle("Enrollment Summary")

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Header
                Text(
                    text = "Enrollment Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A237E)
                )

                Spacer(modifier = Modifier.height(12.dp))

                SummaryRow("🎓 Academic Year", "Year $selectedYear")
                SummaryRow("🏫 Faculty", selectedFaculty)
                SummaryRow("📘 Major", selectedMajor)
                SummaryRow("👥 Class & Gen", "$studentClass - Gen $selectedGeneration")
                SummaryRow("📅 Enrollment Date", enrollmentDate)
                SummaryRow("💳 Payment Plan", selectedPayment)

                Divider(modifier = Modifier.padding(vertical = 12.dp))

                // Total
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Amount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        text = when (selectedPayment) {
                            "Semester 1", "Semester 2" -> "$600"
                            "Full Year" -> "$1200"
                            else -> "$0"
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF4285F4)
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // payment action later
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4285F4)
            )
        ) {
            Text(
                text = "Make Payment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}



@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1A237E),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun Chip(text: String, color: Color, textColor: Color = Color.White,
         modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 12.sp, color = textColor)
    }
}

@Composable
fun YearBox(year: Int, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(
                if (selected) Color(0xFF4285F4) else Color.White,
                RoundedCornerShape(12.dp)
            )
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Year", fontSize = 10.sp,
                color = if (selected) Color.White else Color.Gray)
            Text(year.toString(), fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else Color.Black)
        }
    }
}

@Composable
fun InfoBox(label: String, value: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(label, fontSize = 12.sp)
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(value, modifier = Modifier.padding(16.dp))
        }
    }
}


@Composable
fun PriceCard(
    title: String,
    subtitle: String,
    price: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF4285F4) else Color(0xFFE3F2FD)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else Color.Black
            )
            Text(
                subtitle,
                fontSize = 12.sp,
                color = if (selected) Color.White.copy(0.8f) else Color.Gray
            )
            Text(
                price,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else Color.Black
            )
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Black
        )
        Text(
            text = if (value.isBlank()) "-" else value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


