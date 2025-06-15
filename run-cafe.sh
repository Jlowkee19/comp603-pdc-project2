#!/bin/bash
# Cafe Management System - Launcher Script
# Authors: Lorenz Soriano (18011129) & Phoebe Cruz (21143576)

echo "Starting Cafe Management System..."
echo "================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed or not in PATH"
    echo "Please install Java 21 or later to run this application"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d. -f1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "⚠️  Warning: Java version $JAVA_VERSION detected. This application requires Java 21 or later."
    echo "Application may not work properly with older Java versions."
fi

# Check if JAR file exists
JAR_FILE="target/CafeInventorySystem-1.0-SNAPSHOT-executable.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ Executable JAR file not found: $JAR_FILE"
    echo "Please run 'mvn clean package' to build the application first"
    exit 1
fi

echo "✅ Java detected: $(java -version 2>&1 | head -n 1)"
echo "✅ JAR file found: $JAR_FILE"
echo ""
echo "🚀 Launching Cafe Management System..."
echo ""
echo "Login Credentials:"
echo "  Admin: username=admin, password=admin123"
echo "  Staff: username=enz, password=whoyou123"
echo ""

# Run the application
java -jar "$JAR_FILE"

echo ""
echo "👋 Thanks for using Cafe Management System!"