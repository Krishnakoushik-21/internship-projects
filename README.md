# Internship Projects - Core Java Programming

Public monorepo to store all 15 internship projects.

## Structure

```
internship-projects/
  project-01-student-management-system/  # Done - Student Management System (Core Java)
  project-02-hospital-management-system/  # Done - Hospital Management System (OOP + Advanced Java, 70 pts)
  project-03-student-record-management-system/  # Done - Student Record Management & Analysis (Collections/Generics/Streams/File, 70 pts)
  project-04/  # TODO
  project-05/  # TODO
  project-06/  # TODO
  project-07/  # TODO
  project-08/  # TODO
  project-09/  # TODO
  project-10/  # TODO
  project-11/  # TODO
  project-12/  # TODO
  project-13/  # TODO
  project-14/  # TODO
  project-15/  # TODO
```

## Project 01 - Student Management System
Location: `project-01-student-management-system/`
- Features: Add / View / Search / Delete Student, Grade Calculation, Input Validation
- Tech: Core Java, Scanner, Arrays, OOP basics
- Run:
```bash
cd project-01-student-management-system
javac -d out src/Main.java src/Student.java src/StudentManager.java
java -cp out Main
```

## Project 02 - Hospital Management System
Location: `project-02-hospital-management-system/`
- OOP & Advanced Java (70 pts mandatory): Person/Patient/Doctor, Appointment, Prescription, Bill polymorphism, custom exceptions, packages com.hospital.*
- Run:
```bash
cd project-02-hospital-management-system
javac -d out $(find src -name "*.java")
java -cp out com.hospital.main.Main
```

## Project 03 - Student Record Management & Analysis System
Location: `project-03-student-record-management-system/`
- Collections, Generics, Streams & File Processing (70 pts mandatory): List/Set/Map, GenericUtils<T>, Comparators, Stream stats, Optional, validation, custom exceptions, CSV via NIO, packages com.studentmanagement.*
- Run:
```bash
cd project-03-student-record-management-system
javac -d out $(find src -name "*.java")
java -cp out com.studentmanagement.main.Main
```

## How to add a new project
```bash
mkdir project-02-your-project-name
# copy files into project-02-your-project-name/
git add project-02-your-project-name
git commit -m "Add project-02-your-project-name"
git push origin main
```
