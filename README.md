# DoJo Movie

DoJo Movie is an Android application built with Kotlin for browsing and simulating movie ticket purchases. It features user authentication (registration/login) with phone numbers, OTP verification via SMS, and a local SQLite database to store user and transaction history. Movies are fetched from a remote API using Volley, and the app includes a Google Maps view for location.

## Key Features

* **User Authentication**: Register and log in using a phone number and password.
* **OTP Verification**: Securely verifies a user's phone number using SMS (requires SMS permissions).
* **Movie Browsing**: Fetches and displays a list of movies from a remote JSON API using Volley.
* **Movie Details**: View details, price, and poster for each movie.
* **Purchase Simulation**: Select a quantity and "buy" movies. A record of the transaction is saved to the local database.
* **Transaction History**: View a list of all past purchases made by the user.
* **User Profile**: View user's phone number and log out of the application.
* **Location View**: Integrates Google Maps to display a static "DoJo Movie" location pin.

## Technologies Used

* **Core**: Kotlin, Android SDK
* **Networking**: Volley
* **Database**: SQLite (managed via a custom `SQLiteOpenHelper`)
* **UI/UX**:
    * Material Design
    * RecyclerView
    * Navigation Drawer
    * [ChaosLeung PinView](https://github.com/ChaosLeung/PinView) (for OTP input)
* **Services**: Google Maps API

## Prerequisites & Installation

1.  Clone the repository:
    ```sh
    git clone [https://github.com/calvinvirya/DoJo-Movie.git](https://github.com/calvinvirya/DoJo-Movie.git)
    ```
2.  Open the project in Android Studio.
3.  **API Key**: The project requires a valid Google Maps API key. The key is currently hardcoded in `app/src/main/AndroidManifest.xml`. You must replace the value `"AIzaSy..."` with your own valid API key for the map feature to work.
    ```xml
    <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="YOUR_API_KEY_HERE" />
    ```
4.  **Permissions**: The app requires `android.permission.SEND_SMS` and `android.permission.RECEIVE_SMS` for the OTP verification feature. You will need to grant these permissions on the device.
5.  Build and run the application.

## Project Structure

The project follows a standard Android app structure. Key components are organized as follows:
```
DoJo-Movie-master/
│
├── .gitignore                # (Files to ignore for Git)
├── build.gradle.kts          # (Root project build script)
├── gradle.properties         # (Gradle system properties)
├── gradlew                   # (Gradle wrapper script for Unix/Mac)
├── gradlew.bat               # (Gradle wrapper script for Windows)
├── settings.gradle.kts       # (Project settings, module includes)
│
├── app/                      # (Main application module)
│   │
│   ├── .gitignore
│   ├── build.gradle.kts      # (App module build script and dependencies)
│   ├── proguard-rules.pro    # (Rules for code shrinking)
│   │
│   └── src/
│       │
│       ├── androidTest/      # (Instrumented tests)
│       ├── main/
│       │   │
│       │   ├── AndroidManifest.xml   # (App components, permissions, API key)
│       │   │
│       │   ├── assets/           # (Static assets like images and fonts)
│       │   │   ├── ... (image files like logo.png, otp.png, etc.)
│       │   │
│       │   ├── java/com/example/dojomovie/  # (Main Kotlin source code)
│       │   │   │
│       │   │   ├── adapters/         # (RecyclerView Adapters)
│       │   │   │   ├── FilmGalleryAdapter.kt
│       │   │   │   └── HistoryGalleryAdapter.kt
│       │   │   │
│       │   │   ├── model/            # (Data model classes)
│       │   │   │   ├── Film.kt
│       │   │   │   ├── Transaction.kt
│       │   │   │   └── User.kt
│       │   │   │
│       │   │   ├── util/             # (Utility and helper classes)
│       │   │   │   ├── DB.kt             # (Static DB access & session helper)
│       │   │   │   └── Helper.kt         # (SQLiteOpenHelper schema definition)
│       │   │   │
│       │   │   ├── FilmDetailActivity.kt # (Movie details and purchase screen)
│       │   │   ├── HistoryActivity.kt    # (User's transaction history list)
│       │   │   ├── HomeActivity.kt       # (Main dashboard with movie list & map)
│       │   │   ├── LoginActivity.kt      # (User login screen)
│       │   │   ├── MainActivity.kt       # (Splash/intro screen)
│       │   │   ├── OtpPage.kt            # (OTP verification screen)
│       │   │   ├── ProfileActivity.kt    # (User profile and logout screen)
│       │   │   └── RegisterActivity.kt   # (User registration screen)
│       │   │
│       │   └── res/                  # (Application resources)
│       │       │
│       │       ├── drawable/         # (Icons, shapes, and other graphics)
│       │       ├── font/             # (Custom font files)
│       │       ├── layout/           # (XML layout files for activities/items)
│       │       ├── menu/             # (Navigation drawer menu definition)
│       │       ├── mipmap-*/         # (App launcher icons)
│       │       ├── values/           # (Strings, colors, dimensions, themes)
│       │       ├── values-night/     # (Resources for night mode)
│       │       └── xml/              # (App configuration files)
│       │
│       └── test/                 # (Unit tests)
│
└── gradle/
    ├── wrapper/              # (Gradle wrapper files)
    │   ├── gradle-wrapper.jar
    │   └── gradle-wrapper.properties
    └── libs.versions.toml    # (Version catalog for dependencies)
```

## Example Usage (App Flow)

1.  **Launch**: The app starts at `MainActivity`, showing an intro screen.
2.  **Register**: Click "New to DoJo Movie" to go to `RegisterActivity`. Enter a phone number and a password (must be 8+ chars).
3.  **Verify**: You are sent to `OtpPage`. The app simulates sending an SMS with an OTP. Enter the OTP to verify.
4.  **Login**: You are redirected to `LoginActivity`. Log in with the credentials you just created.
5.  **Home**: You arrive at `HomeActivity`, which shows a list of movies fetched from an API and a Google Map.
6.  **Details & Buy**: Click a movie to see `FilmDetailActivity`. Enter a quantity and click "Buy". A transaction is saved to the local SQLite database.
7.  **History**: Open the navigation drawer and go to "History" to see a list of your transactions.
8.  **Logout**: Go to "Profile" and click "Logout" to clear your session.
