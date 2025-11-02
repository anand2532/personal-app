#!/bin/bash
# Script to install the app on your phone via ADB

ADB="/tmp/platform-tools/adb"
APK="app/build/outputs/apk/debug/app-debug.apk"

echo "Checking for connected devices..."
$ADB devices

if ! $ADB devices | grep -q "device$"; then
    echo ""
    echo "No device connected. Please:"
    echo "1. Make sure you've paired your phone (Settings > Developer Options > Wireless debugging > Pair device)"
    echo "2. Check the 'IP address & port' shown under Wireless debugging"
    echo "3. Run: $ADB connect IP:PORT"
    echo "   Example: $ADB connect 192.168.1.26:34567"
    echo ""
    read -p "Enter the connection IP:PORT: " CONNECTION
    $ADB connect $CONNECTION
    sleep 2
fi

if $ADB devices | grep -q "device$"; then
    echo "Installing app..."
    $ADB install -r $APK
    echo "Done! Check your phone for 'Personal App'"
else
    echo "Failed to connect to device. Please check your connection."
fi

