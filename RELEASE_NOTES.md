# Release Notes

## Release v1.0.1 - Clean Terminal UI (Latest)

**Release Date:** November 2024

### 🎨 UI Improvements
- **Simplified Section Headers**: Removed decorative box-drawing characters (║) around PROJECTS and CHAT ASSISTANT sections
- **Cleaner Layout**: Removed footer decorative line for a more minimal terminal aesthetic
- **Better Spacing**: Improved visual spacing and readability throughout the app
- **Terminal Cursor**: Added custom terminal cursor drawable for better input visibility in chat field

### ✨ Features
- Clean terminal UI with simplified section headers
- Time | Temp display in top right toolbar (clickable)
- Weather & Time page with full terminal styling
- Terminal cursor drawable for better input visibility
- Improved visual spacing and readability

### 🔧 Technical Changes
- Updated `activity_main.xml` - Removed decorative lines from section headers
- Updated `activity_weather_time.xml` - Full terminal UI styling
- Added `cursor_terminal.xml` - Custom terminal cursor drawable
- Updated theme styling for consistent terminal appearance

---

## Release v1.0.0 - Personal App with Terminal UI

## Features

### 🖥️ Terminal-Style Interface
- Custom TerminalTextView with full ANSI color code support
- Terminal-style prompts: `anand@personal-app:~$` and `assistant>`
- 16-color terminal palette with dark background
- Box drawing characters for elegant borders

### 💬 ChatGPT Integration
- Real-time chat with OpenAI ChatGPT API
- Terminal-style message formatting
- Color-coded messages (user, assistant, system)
- Error handling and loading states

### 🌤️ Weather & Time
- Live date and time display (updates every second)
- Location-based weather information
- OpenWeatherMap API integration
- Detailed weather card with temperature, humidity, wind speed

### 📱 Project Buttons
- Four project slots (Project 1-4) ready for future implementation
- Terminal-style UI for project interfaces
- Command-line aesthetic

### 🎨 Material Design 3
- Modern Material Design 3 components
- Responsive layout
- Smooth animations and transitions

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/anand2532/personal-app.git
   cd personal-app
   ```

2. Add API keys to `local.properties`:
   ```
   WEATHER_API_KEY=your_openweathermap_api_key
   CHATGPT_API_KEY=your_openai_api_key
   ```

3. Build and install:
   ```bash
   ./gradlew assembleDebug
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Requirements

- Android SDK 24+ (Android 7.0+)
- OpenWeatherMap API key (free tier available)
- OpenAI API key (for ChatGPT features)

## Technology Stack

- Kotlin
- Material Design 3
- Retrofit for API calls
- Coroutines for async operations
- ViewModel & LiveData for architecture
- Custom TerminalTextView with ANSI support

---

**Full Changelog**: https://github.com/anand2532/personal-app/compare/v0.1.0...v1.0.0

