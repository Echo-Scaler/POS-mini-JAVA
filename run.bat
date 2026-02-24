@echo off
setlocal enabledelayedexpansion

echo ========================================
echo   ELITE POS SYSTEM - AUTOMATED RUNNER
echo ========================================

:: 1. Search for MySQL Connector JAR
set "JAR_FILE="
for /f "delims=" %%i in ('dir /b /s mysql-*.jar 2^>nul') do set "JAR_FILE=%%i"

if "%JAR_FILE%"=="" (
    echo [ERROR] MySQL Connector JAR not found in this folder!
    echo Please download it from: https://dev.mysql.com/downloads/connector/j/
    echo and place the .jar file in this directory.
    pause
    exit /b
)

echo [INFO] Found JAR: %JAR_FILE%

:: 2. Compile Java files
echo [INFO] Compiling Java files...
javac -d . *.java dao\*.java model\*.java service\*.java ui\*.java util\*.java
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Compilation failed! Please check if JDK is installed and in your PATH.
    pause
    exit /b
)

:menu
echo.
echo  [1] RUN POS Application
echo  [2] SEED Sample Data (Run this first!)
echo  [3] EXIT
echo.
set /p choice="Choose an option (1-3): "

if "%choice%"=="1" goto run_app
if "%choice%"=="2" goto seed_data
if "%choice%"=="3" goto :eof
goto menu

:seed_data
echo [INFO] Seeding Sample Data...
java -cp ".;%JAR_FILE%" util.DataSeeder
pause
goto menu

:run_app
echo [INFO] Starting POS Application...
java -cp ".;%JAR_FILE%" Main
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Execution failed!
    pause
)
goto menu
