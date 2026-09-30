# SQLite & JSON Practical 7 – Android

A Kotlin-based Android application developed for **Mobile Application Development (MAD) Practical 7**. The application retrieves person/contact records from an online JSON API, parses the JSON response, stores the records in a local **SQLite database**, and displays them in a **RecyclerView**.

The app also allows individual contact records to be deleted and provides a refresh action to fetch the latest data from the API.

## 🎯 Practical Aim

> Develop an Android application that retrieves person data in JSON format from an Internet API and stores the retrieved data in an SQLite database.


## 📱 Project Overview

The main purpose of this practical is to demonstrate how an Android application can combine:

- JSON data received from an Internet API
- `HttpURLConnection` for network communication
- Kotlin Coroutines for background work
- JSON parsing with `JSONArray` / `JSONObject`
- SQLite for local data persistence
- `RecyclerView` with a custom adapter
- View Binding for UI access
- `Serializable` for the `Person` data model

## ✨ Features

- Fetches person data from a JSON Generator API.
- Parses JSON objects into Kotlin `Person` objects.
- Stores fetched contacts in an SQLite database.
- Loads stored contacts from SQLite when the app starts.
- Uses a `RecyclerView` to display contacts in card-based UI.
- Displays name, phone number, email, and address.
- Deletes individual contacts from the SQLite database and the visible list.
- Refreshes the data from the remote API using the floating action button.
- Shows a Toast message when API loading fails.
- Supports light/dark Material 3 themes through the Android project theme configuration.


## 🛠️ Technology Stack

| Technology | Usage |
|---|---|
| **Kotlin** | Application development |
| **Android SDK** | Android application framework |
| **RecyclerView** | Displaying the list of contacts |
| **SQLite** | Local contact storage |
| **SQLiteOpenHelper** | Database creation and management |
| **HttpURLConnection** | API/network requests |
| **Kotlin Coroutines** | Background network/database work |
| **org.json** | Parsing JSON responses |
| **View Binding** | Accessing XML views safely |
| **Material Components** | Cards and Floating Action Button |
| **ConstraintLayout** | UI layout positioning |

## 🔄 Application Flow

```text
                ┌─────────────────────┐
                │    Start App        │
                └──────────┬──────────┘
                           │
                           ▼
                ┌─────────────────────┐
                │ Check SQLite Count  │
                └───────┬─────┬───────┘
                        │     │
                  Empty │     │ Has Data
                        │     │
                        ▼     ▼
             ┌─────────────┐  ┌─────────────┐
             │ Fetch JSON  │  │ Load SQLite │
             │ from API    │  │ Contacts    │
             └──────┬──────┘  └──────┬──────┘
                    │                │
                    ▼                │
             ┌─────────────┐         │
             │ Parse JSON  │         │
             │ to Person   │         │
             └──────┬──────┘         │
                    │                │
                    ▼                │
             ┌─────────────┐         │
             │ Save to     │         │
             │ SQLite      │         │
             └──────┬──────┘         │
                    └───────┬────────┘
                            ▼
                  ┌─────────────────┐
                  │ RecyclerView    │
                  │ Displays People │
                  └────────┬────────┘
                           │
                    Delete │ / Refresh
                           ▼
                  ┌─────────────────┐
                  │ Update SQLite & │
                  │ RecyclerView    │
                  └─────────────────┘
```

## 📦 Project Structure

```text
24012011117_MAD_Practical7/
│
├── app/
│   └── src/main/
│       ├── java/com/example/a24012011117_mad_practical_7/
│       │   ├── MainActivity.kt
│       │   ├── Person.kt
│       │   ├── PersonAdapter.kt
│       │   ├── HttpRequest.kt
│       │   ├── DatabaseHelper.kt
│       │   └── PersonDbTableData.kt
│       │
│       ├── res/
│       │   ├── layout/
│       │   │   ├── activity_main.xml
│       │   │   ├── item_person.xml
│       │   │   └── single_item.xml
│       │   ├── drawable/
│       │   └── values/
│       │
│       └── AndroidManifest.xml
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
└── gradlew.bat
```

## 🧩 Important Classes

### `MainActivity.kt`

Controls the application flow. It:

1. Initializes the SQLite database helper.
2. Configures the `RecyclerView` and `PersonAdapter`.
3. Checks whether local records already exist.
4. Downloads JSON data when the database is empty or when refresh is requested.
5. Parses the JSON response into `Person` objects.
6. Saves the records in SQLite.
7. Updates the RecyclerView on the main thread.

### `Person.kt`

Defines the contact model and implements `Serializable`.

```kotlin
class Person(
    var id: String,
    var name: String,
    var emailId: String,
    var phoneNo: String,
    var address: String
) : Serializable
```

### `HttpRequest.kt`

Uses `HttpURLConnection` to perform a `GET` request to the JSON API. The implementation also supports sending a Bearer token through the `Authorization` header.

### `DatabaseHelper.kt`

Extends `SQLiteOpenHelper` and manages the local SQLite database. It provides operations for:

- Creating the database/table
- Inserting contacts
- Reading all contacts
- Counting contacts
- Deleting a contact
- Deleting all contacts

### `PersonDbTableData.kt`

Contains the SQLite table name, column names, and `CREATE TABLE` statement.

### `PersonAdapter.kt`

A custom `RecyclerView.Adapter` that binds contact information to the item layout and handles deletion of individual records.

## 🗄️ SQLite Database

The application creates a database named:

```text
persons_db
```

The main table is:

```text
persons
```

### Table Columns

| Column | Type | Description |
|---|---|---|
| `id` | TEXT | Unique person ID / Primary Key |
| `name` | TEXT | Person name |
| `email` | TEXT | Email address |
| `phone` | TEXT | Phone number |
| `address` | TEXT | Person address |

The project uses `id` as the primary key and inserts with conflict replacement so records with the same ID can be updated.

## 🌐 JSON API

The application is configured to consume a JSON Generator endpoint:

```text
https://api.json-generator.com/templates/5rDXHcbgpo93/data
```

The parser expects each JSON record to contain a structure similar to:

```json
{
  "id": "123456789",
  "email": "person@example.com",
  "phone": "+919999999999",
  "profile": {
    "name": "Example Person",
    "address": "Example Address"
  }
}
```

The exact fields returned by the API should match the parser in `MainActivity.kt`.

## 🔐 API Token Configuration

The current source code sends a Bearer token from `MainActivity.kt` when making the API request.

**Important:** do not publish a real API token in a public GitHub repository. Before making the repository public, move the token to a safer configuration mechanism (for example, a local properties file, Gradle-provided build configuration, or another secret-management approach) and remove the hard-coded secret from source control.

## 🚀 How to Run

### Requirements

- Android Studio
- Android SDK with the project's compile/target SDK installed
- JDK compatible with the project configuration
- Internet connection for loading remote JSON data

### Steps

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download required dependencies.
4. Confirm that the JSON API endpoint/token configuration is valid.
5. Connect an Android device or start an emulator.
6. Run the `app` configuration.
7. The application will load stored SQLite data when available; otherwise, it fetches data from the API and saves it locally.

## 🔄 Refresh and Delete Behavior

### Refresh

Press the floating refresh button to request the latest JSON data. When fresh data is successfully parsed, the application clears the existing SQLite records and inserts the new records.

### Delete

Press the delete button on a contact card to remove that person from the SQLite database and immediately remove the item from the RecyclerView.

## 🎨 UI

The interface contains:

- A title: **SQLite and JSON Practical**
- A vertically scrolling `RecyclerView`
- Rounded Material contact cards
- Person icon
- Name, phone, email, and address information
- Circular delete button
- Floating refresh button
- Material 3 based application theme

## 🔒 Android Permission

The project declares Internet access in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

This is required because the application retrieves data from an external web API.

## 📚 Learning Outcomes

This practical demonstrates the integration of several important Android concepts in one application:

- Consuming remote JSON data
- Network communication with `HttpURLConnection`
- Performing work away from the main UI thread with Coroutines
- Parsing nested JSON objects
- Designing a reusable RecyclerView adapter
- Creating and querying SQLite databases
- Implementing insert, read, delete, and refresh operations
- Connecting UI components with View Binding
- Using a serializable data model
