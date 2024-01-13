#!/bin/sh

# Delete all .class files in the src directory
find . -type f -path "./src/*/*" -name "*.class" -delete

# Define the source directory and resources directory
SRC_DIR="src"
RES_DIR="ressources"

# Compile all Java files in the source directory
find $SRC_DIR -name "*.java" -print | xargs javac -cp $SRC_DIR

# If compilation was successful, run the main class
if [ $? -eq 0 ]; then
    java -cp $SRC_DIR:$RES_DIR view.main.Game
else
    echo "Compilation failed"
fi
