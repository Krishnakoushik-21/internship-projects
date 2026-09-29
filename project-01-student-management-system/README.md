# Student Management System

## Project Description
The Student Management System is a beginner-friendly Java application designed to manage student records efficiently. It helps academic staff store basic student information, evaluate marks, calculate grades, and search or delete records. The project was created to demonstrate real-world use of Core Java concepts in a practical, easy-to-understand application.

## Features
- Add Student
- View Students
- Search Student
- Calculate Results
- Grade Calculation
- Pass/Fail Calculation
- Delete Student
- Input Validation
- Menu-driven interface

## Technologies Used
- Java
- Core Java
- Scanner
- Arrays
- OOP basics

## Java Concepts Demonstrated
- Variables
- Data Types
- Operators
- Conditional Statements
- Switch
- Loops
- Arrays
- Strings
- Methods
- Input Validation
- Exception Handling
- Classes and Objects

## Project Structure
The project contains the following files:

- `Main.java` - Handles the menu, user interaction, and program execution.
- `Student.java` - Stores student details, marks, grade, and result calculations.
- `StudentManager.java` - Handles student storage, searching, deletion, validation, and result processing.

## How to Run
Follow these steps in VS Code on Windows:

1. Install JDK on your computer.
2. Open the project folder in VS Code.
3. Open the terminal in VS Code.
4. Compile the Java files.
5. Run the `Main` class.

Example commands:

```bash
cd student-management-system-java
javac -d out src\Main.java src\Student.java src\StudentManager.java
java -cp out Main
```

## Example Usage
Below is a sample flow of how the application works:

1. Add Student
   - Enter Student ID: 101
   - Enter Student Name: Alice Johnson
   - Enter Age: 20
   - Enter marks for Java: 90
   - Enter marks for Python: 85
   - Enter marks for DBMS: 88
   - Enter marks for Operating Systems: 80
   - Enter marks for Computer Networks: 92

2. View Students
   - The system displays the student record with total, average, grade, and pass/fail status.

3. Search Student
   - Search by Student ID or Student Name.
   - Example: Student ID 101 or name Alice Johnson.

4. Calculate Result
   - The application calculates total marks, average, grade, and pass/fail.

5. Delete Student
   - Enter the Student ID and confirm the deletion.

6. Exit
   - Select Exit from the menu to close the program.

## Validation
The project validates all user inputs before accepting them. It checks:
- Student ID must be numeric and positive.
- Student ID must not already exist.
- Student name cannot be empty or contain only spaces.
- Age must be valid and positive.
- Marks must be between 0 and 100.
- Invalid menu input is handled safely.
- Input mismatch errors are handled without crashing the program.

## Future Enhancements
The following enhancements are possible in the future, but they are not part of the current implementation:
- Database integration
- GUI-based interface
- Login system
- File storage for persistent records

## Git Commands for GitHub
Use the following commands in Windows Terminal or VS Code Terminal to upload the project to GitHub:

```bash
mkdir student-management-system-java
cd student-management-system-java
git init
git checkout -b main
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/your-username/student-management-system-java.git
git push -u origin main
```

Replace `your-username` with your actual GitHub username.
