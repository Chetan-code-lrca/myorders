# MyOrders

An Android order-history UI built with Kotlin. The current app focuses on the orders screen and uses local sample data to demonstrate filtering, search, status tabs, and order actions.

## What is implemented

- Bottom navigation with Home, Orders, Payments, and Account sections
- Orders list with Completed, Cancelled, Booked Again, and All filters
- Search by order ID or pickup/drop address
- Empty-state handling when no orders match the current filters
- Order actions for invoice, booking again, details, sharing, and reporting
- Material-based Android UI using XML layouts and View Binding

The Home, Payments, and Account tabs currently use placeholder fragments. The order data is stored in memory and is not connected to a backend or payment service.

## Tech stack

- Kotlin
- Android SDK 34
- AndroidX AppCompat
- Material Components
- RecyclerView
- Fragment KTX
- View Binding
- Gradle

The app module uses `minSdk 24`, `targetSdk 34`, and Java/Kotlin 17.

## Run locally

### Requirements

- Android Studio
- JDK 17
- Android SDK Platform 34

### Clone

```bash
git clone https://github.com/Chetan-code-lrca/myorders.git
cd myorders
```

Open the project in Android Studio and let Gradle sync.

### Run

Select the `app` configuration and run it on an Android emulator or a physical device running Android 7.0 (API 24) or newer.

### Build

Use Android Studio's **Build → Make Project** or build the `app` module from the Gradle tool window.

The repository does not include a Gradle wrapper, so Android Studio is the recommended build environment.

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
│   ├── build.gradle.kts
│   └── src/main/AndroidManifest.xml
├── gradle/
├── settings.gradle.kts
├── build.gradle.kts
└── README.md
```

## Notes

The sample orders in the app are hard-coded for UI testing. Actions such as downloading an invoice, sharing an order, reporting an order, and booking again currently provide local UI feedback rather than calling external services.

No API keys, login credentials, or external service configuration are required to run the current version.

## Tests

The repository contains Android instrumentation test scaffolding. Tests can be run from Android Studio through the Android test configuration.
