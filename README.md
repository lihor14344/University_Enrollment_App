# Student Portal

## Project Description
This project is a student management system built as a learning project for Android development. It helps students handle their academic tasks such as enrolling in courses, checking schedules, and managing payments.

The app is built for learning purposes and uses real data from APIs to work dynamically. If the API is not available, the app falls back to static data. The design is simple, clean, and easy to use.

## Features
- **Student registration and login** - Secure authentication with Laravel Sanctum
- **View personal information** - Profile management
- **View academic information** - Student details and major
- **List available courses** - Browse courses with descriptions
- **Enroll in courses** - Course enrollment functionality
- **View class schedules** - Weekly class timetable
- **Track attendance** - Attendance records and percentages
- **View scores and grades** - Academic performance tracking
- **Process payments with QR codes** - QR code scanning for payments
- **Generate payment receipts** - Receipt generation after payment
- **Read news updates** - University announcements
- **Manage student cards** - Student ID card upload

## Technology Stack
- **Android Studio** (latest stable version)
- **Kotlin** programming language
- **Retrofit** for API calls
- **MVVM architecture** with Repository pattern
- **ViewModel + LiveData** for state management
- **RecyclerView** for lists (where applicable)
- **Simple XML layouts** with Jetpack Compose
- **QR code handling** with Google ML Kit
- **DataStore** for secure token storage

## Project Structure
```
com.example.enrollment/
├── data/
│   ├── AuthPreferences.kt          # Token storage management
│   ├── StaticData.kt               # Fallback data when API fails
│   └── repository/
│       ├── AuthRepository.kt       # Authentication operations
│       └── StudentRepository.kt    # Student data operations
├── model/
│   ├── auth/                       # Authentication models
│   ├── academic/                   # Course and academic models
│   ├── payment/                    # Payment related models
│   ├── profile/                    # Profile models
│   ├── student/                    # Student specific models
│   └── common/                     # Shared models like News
├── network/
│   ├── ApiClient.kt                # Retrofit client setup
│   ├── ApiService.kt               # API endpoints definition
│   └── AuthInterceptor.kt          # Token authentication interceptor
├── ui/
│   ├── Auth/                       # Authentication screens and ViewModels
│   ├── screens/                    # All feature screens
│   ├── navigation/                 # Navigation graph
│   └── theme/                      # App theming
└── MainActivity.kt                 # App entry point
```

## Learning Objectives
This project helps learn:
- Android app architecture (MVVM)
- API integration with Retrofit
- Data persistence with DataStore
- QR code scanning
- Error handling and fallback mechanisms
- Clean code principles
- Material Design implementation

## Data Handling
- **Real API data** when available
- **Static fallback data** when API fails
- **No admin dashboards** (student-only focus)
- **Secure token management** with DataStore

## Limitations (Learning Project)
- No admin panel
- No advanced security features beyond basic auth
- Focus only on student functionality
- Simple UI/UX suitable for learning