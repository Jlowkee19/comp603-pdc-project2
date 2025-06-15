@echo off
REM Cafe Management System - Windows Launcher Script
REM Authors: Lorenz Soriano (18011129) & Phoebe Cruz (21143576)

echo Starting Cafe Management System...
echo =================================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed or not in PATH
    echo Please install Java 21 or later to run this application
    pause
    exit /b 1
)

REM Check if JAR file exists
set JAR_FILE=target\CafeSystem-1.0-SNAPSHOT-executable.jar
if not exist "%JAR_FILE%" (
    echo ❌ Executable JAR file not found: %JAR_FILE%
    echo Please run 'mvn clean package' to build the application first
    pause
    exit /b 1
)

echo ✅ Java detected
echo ✅ JAR file found: %JAR_FILE%
echo.
echo 🚀 Launching Cafe Management System...
echo.
echo Login Credentials:
echo   Admin: username=admin, password=admin123
echo   Staff: username=enz, password=whoyou123
echo.

REM Run the application
java -jar "%JAR_FILE%"

echo.
echo 👋 Thanks for using Cafe Management System!
pause