#!/bin/bash
# Create GitHub release using GitHub CLI

VERSION=${1:-"v1.0.0"}
REPO="anand2532/personal-app"

if command -v gh &> /dev/null; then
    echo "Creating GitHub release using GitHub CLI..."
    gh release create $VERSION \
        --title "Personal App $VERSION" \
        --notes "Release $VERSION: Personal App with Terminal UI

Features:
- Terminal-style chat interface with ANSI color support
- ChatGPT integration
- Weather & Time display
- Project buttons (Project 1-4)
- Material Design 3 UI
- Location-based weather fetching

Installation:
1. Clone the repository
2. Add API keys to local.properties:
   - WEATHER_API_KEY (from OpenWeatherMap)
   - CHATGPT_API_KEY (from OpenAI)
3. Build and install: ./gradlew assembleDebug && adb install app/build/outputs/apk/debug/app-debug.apk"
else
    echo "GitHub CLI (gh) not installed."
    echo "Tag created and pushed. Create release manually at:"
    echo "https://github.com/$REPO/releases/new?tag=$VERSION"
fi
