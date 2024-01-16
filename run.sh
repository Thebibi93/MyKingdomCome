#!/bin/sh

# Delete all .class files in the src directory
find . -type f -path "./src/*/*" -name "*.class" -delete

# Define the source directory and resources directory
SRC_DIR="src"
RES_DIR="ressources"

# Compile all Java files in the source directory
find $SRC_DIR -name "*.java" -print | xargs javac -cp $SRC_DIR

# If compilation was successful, run the main class and build the jar
if [ $? -eq 0 ]; then
    java -cp $SRC_DIR:$RES_DIR view.main.Game

    # Create a manifest file
    echo "Main-Class: view.main.Game" > manifest.txt
    echo "Class-Path: ." >> manifest.txt

    # Create the jar file
    jar cvfm Game.jar manifest.txt -C $SRC_DIR . -C $RES_DIR .

    # Clean up the manifest file
    rm manifest.txt
else
    echo "Compilation failed"
fi