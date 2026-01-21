package com.example.enrollment.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.enrollment.viewmodel.EnrollmentCoursesState
import com.example.enrollment.viewmodel.StudentViewModel

// ----- Data Models -----
data class ClassSession(
    val type: String, // Lecture / Lab
    val name: String,
    val time: String,
    val room: String,
    val lecturer: String
)

data class DailySchedule(
    val dayName: String,
    val date: String,
    val sessions: List<ClassSession>
)

// ----- Sample Data -----
val sampleSchedules = listOf(
    DailySchedule(
        "Monday", "Jan 1",
        listOf(
            ClassSession("Lecture", "Mobile App Development", "07:00 - 08:30", "Room D306", "Mr. Hang Sopheak"),
            ClassSession("Lab", "Database System", "08:30 - 10:00", "Room D306", "Dr. Sok Rithy"),
            ClassSession("Lecture", "Web Development", "10:30 - 12:00", "Room D306", "Mr. Hang Sopheak")
        )
    ),
    DailySchedule(
        "Tuesday", "Jan 2",
        listOf(
            ClassSession("Lecture", "Algorithm Design", "07:00 - 08:30", "Room T302", "Mr. Boun Vichea"),
            ClassSession("Lecture", "Software Engineering", "08:30 - 10:00", "Room T302", "Mr. Hun Dara")
        )
    ),
    DailySchedule(
        "Wednesday", "Jan 3",
        listOf(
            ClassSession("Lecture", "Mobile App Development", "07:00 - 08:30", "Room D306", "Mr. Hang Sopheak")
        )
    ),
    DailySchedule(
        "Thursday", "Jan 4",
        listOf(
            ClassSession("Lecture", "Computer Network", "07:00 - 08:30", "Room D306", "Dr. Lim Sopheaktra"),
            ClassSession("Lecture", "Computer Network", "09:00 - 10:30", "Room D306", "Dr. Sok Kimheng")
        )
    ),
    DailySchedule(
        "Friday", "Jan 5",
        listOf(
            ClassSession("Lecture", "Data Mining", "07:00 - 08:30", "Room T301", "Dr. Chan Dara"),
            ClassSession("Lab", "Database Lab", "08:30 - 10:00", "Room T302", "Mr. Vuthy Rith"),
            ClassSession("Lecture", "Artificial Intelligence", "10:30 - 12:00", "Room T303", "Dr. Sokha Phan")
        )
    ),
    DailySchedule(
        "Saturday", "Jan 6",
        listOf(
            ClassSession("Lecture", "Human-Computer Interaction", "07:00 - 08:30", "Room D305", "Ms. Lida Sim"),
            ClassSession("Lecture", "Software Project Management", "08:45 - 10:15", "Room D305", "Mr. Kosal Heng"),
            ClassSession("Lab", "Mobile App Lab", "10:30 - 12:00", "Room D306", "Dr. Hang Sopheak")
        )
    )
)



// ----- Main Screen -----
@Composable
fun MyClassScreen(navController: NavHostController) {
    val studentViewModel: StudentViewModel = viewModel()
    val enrollmentCoursesState by studentViewModel.enrollmentCoursesState.collectAsState()

    LaunchedEffect(Unit) {
        studentViewModel.fetchEnrollmentCourses()
    }

    var isDailyView by remember { mutableStateOf(true) }
    var selectedDayIndex by remember { mutableStateOf(0) }
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState)
    ) {
        // ----- Header (no padding except start) -----
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A237E))
                .height(56.dp)
                .padding(start = 16.dp)
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
                "My Class",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----- Daily / Weekly toggle (with horizontal padding) -----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = { isDailyView = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDailyView) Color(0xFF4285F4) else Color.LightGray
                )
            ) { Text("Daily View", color = Color.White) }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { isDailyView = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isDailyView) Color(0xFF1A237E) else Color.LightGray
                )
            ) { Text("Weekly View", color = Color.White) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----- Daily / Weekly content -----
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            when (enrollmentCoursesState) {
                is EnrollmentCoursesState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is EnrollmentCoursesState.Success -> {
                    val courses = (enrollmentCoursesState as EnrollmentCoursesState.Success).courses
                    DisplayEnrollmentCourses(courses)
                }
                is EnrollmentCoursesState.Error -> {
                    Text("Error: ${(enrollmentCoursesState as EnrollmentCoursesState.Error).message}", color = Color.Red)
                    // Show empty list on error
                    DisplayEnrollmentCourses(emptyList())
                }
                else -> {
                    // Show empty list for idle
                    DisplayEnrollmentCourses(emptyList())
                }
            }
        }
    }
}

@Composable
fun DisplayScheduleContent(isDailyView: Boolean, selectedDayIndex: Int, onDaySelected: (Int) -> Unit) {
    if (isDailyView) {
        // Horizontal date selector
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            sampleSchedules.forEachIndexed { index, day ->
                Button(
                    onClick = { onDaySelected(index) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedDayIndex == index) Color(0xFF4285F4) else Color.LightGray
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("${day.dayName.take(3)}\n${day.date}", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show only selected day's schedule
        DayScheduleCard(sampleSchedules[selectedDayIndex])
    } else {
        // Weekly View → show all days
        sampleSchedules.forEach { day ->
            DayScheduleCard(day)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
}




// ----- Day Card -----
@Composable
fun DayScheduleCard(day: DailySchedule) {
    var isExpanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Day header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4285F4), RoundedCornerShape(8.dp))
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(day.dayName, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(day.date, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text("${day.sessions.size} Classes", color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isExpanded) "▲" else "▼", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Class sessions
        if (isExpanded) {
            Column {
                day.sessions.forEach { session ->
                    ClassCard(session)
                }
            }
        }
    }
}

// ----- Class Card -----
@Composable
fun ClassCard(session: ClassSession) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(session.type, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
            Spacer(modifier = Modifier.height(4.dp))
            Text(session.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text("🕒 ${session.time}", fontSize = 12.sp)
            Text("📍 ${session.room}", fontSize = 12.sp)
            Text("👨‍🏫 ${session.lecturer}", fontSize = 12.sp)
        }
    }
}

// ----- Display Enrollment Courses -----
@Composable
fun DisplayEnrollmentCourses(courses: List<com.example.enrollment.model.student.EnrollmentCourseResponse>) {
    if (courses.isEmpty()) {
        Text("No enrolled courses found.", modifier = Modifier.padding(16.dp))
    } else {
        Column {
            courses.forEach { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Course ID: ${course.course_id}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Text("Enrollment ID: ${course.enrollment_id}", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
