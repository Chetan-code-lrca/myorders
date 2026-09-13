# MyOrders

MyOrders is an Android app that recreates an order-history screen with Kotlin and AndroidX. The current version is focused on the Orders experience: browsing sample orders, filtering them by status, searching, and opening the available order actions.

## Features

- Orders list with **All**, **Completed**, **Cancelled**, and **Booked Again** filters
- Search by order ID or pickup/drop address
- Empty states when no orders match the current search or filter
- Order actions for invoice, booking again, details, sharing, and reporting
- Bottom navigation for Home, Orders, Payments, and Account
- Material-based Android UI with View Binding

The Home, Payments, and Account sections are currently placeholders. Orders are backed by local sample data, so there is no server-side order history, payment processing, or user account system in the current version.

## Tech stack

- Kotlin 1.9.23
- Android Gradle Plugin 8.3.1
- Gradle 9.3.1
- Android SDK 34
- AndroidX AppCompat
- Material Components
- RecyclerView
- ConstraintLayout
- Fragment KTX
- View Binding

The application module uses `minSdk 24`, `targetSdk 34`, and Java/Kotlin 17.

## Requirements

Install:

- Android Studio with Android SDK Platform 34
- JDK 17
- An Android emulator or physical device running Android 7.0 (API 24) or newer

The repository includes the Gradle wrapper, so a system-wide Gradle installation is not required.

## Run locally

Clone the repository:

```bash
git clone https://github.com/Chetan-code-lrca/myorders.git
cd myorders
```

Open the project in Android Studio and allow Gradle to sync.

### Linux / macOS

```bash
./gradlew assembleDebug
./gradlew installDebug
```

### Windows PowerShell

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
```

To launch the app from Android Studio, select the `app` run configuration and choose an emulator or connected Android device.

## Testing

Run the unit tests with:

```bash
./gradlew test
```

Run Android instrumentation tests on a connected device or emulator with:

```bash
./gradlew connectedAndroidTest
```

On Windows:

```powershell
.\gradlew.bat test
.\gradlew.bat connectedAndroidTest
```

## Project structure

```text
myorders/
├── app/
│   ├── src/main/java/com/assignment/myorders/
│   │   ├── MainActivity.kt
│   │   ├── model/
│   │   └── ui/
│   │       ├── orders/
│   │       └── placeholder/
│   ├── src/main/res/
│   ├── build.gradle.kts
│   └── src/main/AndroidManifest.xml
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Current scope

This repository is primarily an Android UI implementation. The order records are hard-coded sample data used to demonstrate the interface and interactions.

The order actions currently provide local UI feedback rather than calling external order, invoice, sharing, reporting, or payment services.

No API keys, login credentials, or external service configuration are required for the current application.

## Build details

The project uses the Gradle wrapper with Gradle 9.3.1 and Android Gradle Plugin 8.3.1. The application module targets Android SDK 34 and uses Java/Kotlin 17.