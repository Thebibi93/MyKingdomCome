@echo off

REM Delete all .class files in the src directory
for /R src %%G in (*.class) do del /S /Q "%%G"

REM Define the source directory and resources directory
set SRC_DIR=src
set RES_DIR=resources

REM Compile all Java files in the source directory
for /R %SRC_DIR% %%G in (*.java) do (
    javac "%%G"
)

REM If compilation was successful, run the main class
if %errorlevel%==0 (
    java -cp %SRC_DIR%;%RES_DIR% view.main.Game
) else (
    echo Compilation failed
)