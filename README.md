Banking App - Kotlin Version
Overview
This repository contains the Kotlin (Native Android) implementation for the Mobile Developer Challenge: Banking App User Interface. The app provides an intuitive user interface for core banking operations, interacting with a provided backend API (or mocked where necessary). This version focuses primarily on the UI components and layout, with partial API integration for demonstration purposes. For a fully functional cross-platform version, check out the Flutter counterpart.
The app demonstrates:

User authentication (login and registration screens)
Dashboard for account overview
Fund transfer interface
Transaction history view
Basic error handling and loading states

It follows the provided Figma UI design and integrates with the challenge API at https://challenge-api.qena.dev.
Tech Stack

Language: Kotlin
Platform: Native Android
Build System: Gradle (KTS)
Key Libraries:
Android Jetpack: Core KTX, AppCompat, ConstraintLayout, Lifecycle (LiveData & ViewModel), Navigation, Fragment KTX
Dependency Injection: Hilt
Networking: Retrofit, OkHttp, Moshi (for JSON parsing)
Coroutines: Kotlinx Coroutines (for async operations)
Security: AndroidX Security Crypto (for secure token storage)
UI: Material Design, View Binding


Compile SDK: 36
Min SDK: 24
Target SDK: 36
JVM Target: 11

Features

User Authentication:

Login screen with username and password inputs.
Registration screen with fields for username, password, first name, last name, email, and phone number.
Secure storage of JWT access and refresh tokens using EncryptedSharedPreferences.
Automatic token refresh logic on 401 errors.


Dashboard / Account Overview:

Displays user's bank accounts with account number, type, and balance.
Navigation to create account, transfer funds, and view transactions.
Supports pagination for account listing.


Fund Transfer:

Form for peer-to-peer transfers.
Dropdown for selecting from user's accounts.
Inputs for recipient account number and amount.
Client-side validation and error handling (e.g., insufficient funds).


Transaction History:

Paginated list of transactions for a selected account.
Shows amount, type, direction (DEBIT/CREDIT), timestamp, and description.
"Load More" functionality for pagination.


UX Enhancements:

Loading indicators during API calls.
User-friendly error messages.
Responsive design for various screen sizes and orientations.
Basic input validation.



Note: This version emphasizes UI/UX fidelity to the Figma design. Full API integration (e.g., real-time data fetching and error responses) is partially implemented for demo purposes. Network calls use Retrofit with Coroutines for asynchronous handling.
Setup and Installation
Prerequisites

Android Studio (latest stable version recommended)
JDK 11 or higher
Android SDK with API level 36 installed
An emulator or physical device running Android 7.0 (API 24) or higher

Steps to Run

Clone the Repository:
git clone https://github.com/your-username/banking-app-kotlin.git


Open in Android Studio:

Launch Android Studio.
Select "Open an existing project" and navigate to the cloned directory.


Sync Gradle:

Android Studio will prompt to sync the project. Alternatively, click "Sync Project with Gradle Files" in the toolbar.
Ensure all dependencies are downloaded (listed in build.gradle.kts).


Configure API:

The app uses the base URL https://challenge-api.qena.dev.
No additional API keys are required, but ensure your device/emulator has internet access.


Build and Run:

Select a target device (emulator or connected phone).
Click "Run" (green play button) or use Shift + F10.
For release build: Run ./gradlew assembleRelease in the terminal.


Generate APK:

Run ./gradlew assembleDebug for a debug APK (located in app/build/outputs/apk/debug/).
Or ./gradlew assembleRelease for a release APK (in app/build/outputs/apk/release/).
The release APK is uploaded to GitHub Releases for easy download.



Troubleshooting

Gradle Sync Issues: Ensure your gradle.properties has android.useAndroidX=true and check internet for dependency downloads.
API Errors: Verify the API endpoint is live. If mocking is needed, adjust the Retrofit setup in the code.
Token Storage: Uses Android's EncryptedSharedPreferences for demo security; in production, consider more advanced key management.

API Integration Details

Base URL: https://challenge-api.qena.dev
Swagger Docs: https://challenge-api.qena.dev/swagger-ui.html
Endpoints Used:
POST /api/auth/login
POST /api/auth/register
GET /api/accounts (with pagination)
POST /api/accounts/transfer
GET /api/transactions/{accountId}


JWT Handling: Tokens are stored securely and refreshed via /api/auth/refresh endpoint.

UI Design Reference
The UI is based on the Figma design: Banking App UI.
Screenshots
(Add your app screenshots here for better visualization. Example placeholders:)

Login Screen: 
Dashboard: 
Fund Transfer: 
Transaction History: 

Code Structure

app/src/main/java/com/example/bankingapp:
ui: Activities, Fragments, and Adapters for screens.
data: Models, Repository for API interactions.
network: Retrofit setup, API interfaces.
di: Hilt modules for dependency injection.
utils: Helpers for security, validation, etc.



The code follows MVVM architecture with clean separation of concerns.
Limitations and Future Improvements

This version is UI-focused; full end-to-end testing and advanced features (e.g., biometric auth) can be added.
Pagination is basic; infinite scrolling could be enhanced.
Error handling covers common cases but can be expanded for edge scenarios.

License
This project is for demonstration purposes only. No license is applied; feel free to use it as a reference.
If you have questions, reach out via GitHub issues!
