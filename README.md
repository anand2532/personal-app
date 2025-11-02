# Personal App

An Android application that displays live date/time and current weather information.

## Features

- **Live Date & Time**: Real-time clock showing current date and time, updating every second
- **Weather Information**: Current weather data including:
  - Location
  - Temperature
  - Weather description
  - Feels like temperature
  - Humidity
  - Wind speed

## Setup Instructions

### Prerequisites

- Android Studio (latest version recommended)
- Android SDK (API 24 or higher)
- Kotlin support

### API Key Setup

This app uses OpenWeatherMap API to fetch weather data. You need to:

1. Sign up for a free API key at [OpenWeatherMap](https://openweathermap.org/api)
2. Get your API key from the dashboard
3. Create a `local.properties` file in the root directory (copy from `local.properties.template`)
4. Add your API key to `local.properties`:
   ```
   WEATHER_API_KEY=your_actual_api_key_here
   ```
   
   **Note**: The `local.properties` file is in `.gitignore` and will not be committed to version control.

### Permissions

The app requires the following permissions:
- Internet (for fetching weather data)
- Location (for getting weather based on your current location)

The app will request location permissions when you first launch it.

### Building and Running

1. Open the project in Android Studio
2. Sync Gradle files (Android Studio will prompt you)
3. Connect an Android device or start an emulator
4. Click "Run" or press Shift+F10

## Project Structure

```
app/
├── src/
│   └── main/
│       ├── java/com/personal/app/
│       │   ├── MainActivity.kt        # Main UI activity
│       │   ├── MainViewModel.kt       # ViewModel for data management
│       │   └── WeatherService.kt      # API service for weather data
│       ├── res/
│       │   ├── layout/
│       │   │   └── activity_main.xml  # Main UI layout
│       │   └── values/
│       │       ├── strings.xml
│       │       ├── colors.xml
│       │       └── themes.xml
│       └── AndroidManifest.xml
└── build.gradle.kts                   # App dependencies
```

## Technologies Used

- **Kotlin**: Programming language
- **Material Design 3**: UI components
- **Retrofit**: HTTP client for API calls
- **Coroutines**: Asynchronous operations
- **ViewModel & LiveData**: Architecture components
- **Location Services**: For getting user location
- **OpenWeatherMap API**: Weather data source

## Notes

- The app uses your device's location to fetch weather. If location is unavailable, it falls back to a default location (London).
- Weather data is fetched in metric units (°C, m/s).
- The date/time display updates every second automatically.

