# Personal App

[![Version](https://img.shields.io/badge/version-v1.0.1-blue.svg)](https://github.com/anand2532/personal-app/releases)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

A modern Android application featuring a terminal-style interface with ChatGPT integration, weather information, and project management capabilities.

## 🚀 Features

### Terminal-Style Interface
- **Custom Terminal UI** with full ANSI color code support
- **Terminal prompts** (`anand@personal-app:~$`, `assistant>`)
- **16-color terminal palette** with dark background (#1E1E1E)
- **Box drawing characters** for elegant borders and tree structures

### ChatGPT Integration
- Real-time chat with OpenAI ChatGPT API
- Terminal-style message formatting with color coding
- Supports GPT-3.5-turbo model
- Error handling and loading states

### Weather & Time
- **Live date and time** display (updates every second)
- **Location-based weather** information via OpenWeatherMap API
- Detailed weather card showing:
  - Current temperature and "feels like"
  - Humidity and wind speed
  - Weather description
  - Location information

### Project Management
- Four project slots ready for future implementation
- Terminal-style UI for project interfaces
- Command-line aesthetic for project display

### Modern UI/UX
- Material Design 3 components
- Responsive layout
- Smooth animations and transitions
- Clean and organized interface

## 📋 Requirements

- **Android SDK**: 24+ (Android 7.0 Nougat or higher)
- **OpenWeatherMap API Key**: [Get it here](https://openweathermap.org/api) (Free tier available)
- **OpenAI API Key**: [Get it here](https://platform.openai.com/api-keys) (Required for ChatGPT features)
- **Java**: JDK 17 or higher
- **Android Studio**: Latest version recommended

## 🛠️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/anand2532/personal-app.git
cd personal-app
```

### 2. Configure API Keys

Create a `local.properties` file in the root directory:

```properties
# OpenWeatherMap API Key
WEATHER_API_KEY=your_openweathermap_api_key_here

# OpenAI API Key (for ChatGPT)
CHATGPT_API_KEY=your_openai_api_key_here
```

**Note**: Use `local.properties.template` as a reference. The `local.properties` file is ignored by git for security.

### 3. Build the Project

```bash
# Build debug APK
./gradlew assembleDebug

# Or use Android Studio:
# File → Open → Select project folder
```

### 4. Install on Device

```bash
# Via ADB (device must be connected)
adb install app/build/outputs/apk/debug/app-debug.apk

# Or use the provided script
./install.sh
```

## 📱 Usage

### First Launch
1. The app will request location permissions (required for weather)
2. Grant location permission when prompted
3. Weather data will load automatically based on your location

### Using the Chat
1. Type your message in the terminal-style input field
2. Press Enter or tap Send
3. View responses with terminal-style formatting

### Weather & Time
- View current time and weather in the top right corner
- Tap to open detailed weather information
- Use refresh button to update weather data

### Projects
- Tap any project button (Project 1-4) to see terminal-style output
- Full project interfaces coming soon

## 🏗️ Project Structure

```
personal-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/personal/app/
│   │   │   │   ├── MainActivity.kt          # Main home screen
│   │   │   │   ├── WeatherTimeActivity.kt   # Weather & Time details
│   │   │   │   ├── TerminalTextView.kt      # Terminal UI component
│   │   │   │   ├── ChatService.kt           # ChatGPT API service
│   │   │   │   ├── ChatViewModel.kt         # Chat ViewModel
│   │   │   │   ├── WeatherService.kt        # Weather API service
│   │   │   │   └── MainViewModel.kt         # Weather ViewModel
│   │   │   ├── res/
│   │   │   │   ├── layout/                  # UI layouts
│   │   │   │   ├── values/                  # Resources (strings, colors, themes)
│   │   │   │   └── drawable/                # Icons and graphics
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts                 # App dependencies
├── build.gradle.kts                          # Root build config
├── settings.gradle.kts                       # Project settings
├── gradle/                                   # Gradle wrapper
├── README.md                                 # This file
├── CONTRIBUTING.md                           # Contribution guidelines
├── RELEASE_NOTES.md                          # Release notes
└── install.sh                                # Installation helper script
```

## 🔧 Technology Stack

- **Language**: Kotlin
- **UI Framework**: Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit 2.9.0
- **Coroutines**: Kotlin Coroutines for async operations
- **Lifecycle**: AndroidX Lifecycle Components
- **Location**: Google Play Services Location
- **Build System**: Gradle with Kotlin DSL

## 📝 Configuration

### API Keys Setup

1. **OpenWeatherMap API Key**:
   - Sign up at [OpenWeatherMap](https://openweathermap.org/api)
   - Get your free API key from the dashboard
   - Add to `local.properties`: `WEATHER_API_KEY=your_key`

2. **OpenAI API Key**:
   - Sign up at [OpenAI Platform](https://platform.openai.com)
   - Create an API key from the dashboard
   - Add to `local.properties`: `CHATGPT_API_KEY=your_key`

**Note**: API keys may take a few hours to activate after creation.

## 🤝 Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for details on:
- Code style guidelines
- Pull request process
- Branch naming conventions
- Commit message format

### Quick PR Workflow

```bash
# Create feature branch
git checkout -b feature/your-feature-name

# Make changes and commit
git add .
git commit -m "feat: Your feature description"

# Push and create PR
git push -u origin feature/your-feature-name
```

## 📄 License

This project is open source. See repository for license details.

## 🐛 Troubleshooting

### Weather Not Loading
- Check if OpenWeatherMap API key is valid and activated
- Verify location permissions are granted
- Check internet connection

### ChatGPT Not Responding
- Verify OpenAI API key is set in `local.properties`
- Check API key has sufficient credits/quota
- Check internet connection

### Build Errors
- Ensure JDK 17+ is installed
- Sync Gradle files in Android Studio
- Clean and rebuild: `./gradlew clean build`

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/anand2532/personal-app/issues)
- **Releases**: [GitHub Releases](https://github.com/anand2532/personal-app/releases)

## 🎯 Future Enhancements

- [ ] Implement full project interfaces for Project 1-4
- [ ] Add more terminal commands and features
- [ ] Support for multiple ChatGPT models
- [ ] Weather forecast (5-day, hourly)
- [ ] Customizable terminal themes
- [ ] Terminal command history
- [ ] Export chat conversations

---

**Version**: 1.0.1  
**Last Updated**: November 2024
