# Student Record Management & Analysis System - Project 03

## 1. Project Title
Student Record Management & Analysis System (Collections, Generics, Streams & File Processing - 70 Points Mandatory Assignment)

## 2. Project Description
A console-based Core Java application to manage student records: add, view, search, update, delete, sort, filter, calculate statistics, and save/load data from files. Built to genuinely demonstrate Java Collections (List/Set/Map), Generics, Sorting/Comparators, File I/O (NIO), and Java 8+ features (lambdas, Streams, Optional) with validation and custom exception handling. No Spring Boot, no external frameworks.

## 3. Features
- Add Student (validated: unique ID/email, name, age 15-60, email format, 10-digit phone, CGPA 0-10, year 1-5, marks 0-100)
- View All Students (detailed view incl. subjects/marks)
- Search Student by ID (Optional), name (partial), department, email (Optional)
- Update Student (existence check, re-validation, collections kept consistent)
- Delete Student (existence check, collections kept consistent)
- Sort Students by name / CGPA (high-low) / age / department / ID (Comparator + Java 8 style)
- Filter Students by CGPA threshold / department / year / age range / Top N
- Student Statistics: total, average CGPA, highest/lowest CGPA, count per department, average per department, group by department/year, Top 5 (all via Stream API)
- Save Students to File (CSV via Path/Files/BufferedWriter, try-with-resources)
- Load Students from File (handles missing file, skips invalid/corrupted lines, skips duplicates gracefully)
- Seeded demo data (5 students) for quick evaluation

## 4. Technologies Used
- Java 8+ (tested on Java 21; only Java 8-compatible APIs used)
- Core Java only: Collections, Generics, Streams, Optional, Lambdas, NIO File I/O, Scanner
- No external libraries

## 5. Project Structure
```
project-03-student-record-management-system/
├── src/
│   └── com/
│       └── studentmanagement/
│           ├── model/
│           │   └── Student.java              # Stores student info, Comparable by ID
│           ├── repository/
│           │   └── StudentRepository.java    # List/Set/Map + basic data access
│           ├── service/
│           │   └── StudentService.java       # Business logic, streams, sorting, stats
│           ├── util/
│           │   ├── GenericUtils.java         # Generic class + static generic methods
│           │   ├── ValidationUtils.java      # All validation
│           │   └── FileUtils.java            # CSV save/load (NIO, try-with-resources)
│           ├── exception/
│           │   ├── StudentNotFoundException.java
│           │   ├── DuplicateStudentException.java
│           │   ├── InvalidStudentDataException.java
│           │   └── FileProcessingException.java
│           └── main/
│               └── Main.java                 # Console menu + user interaction
├── students.csv                              # Sample data file
├── README.md
└── out/ (generated, ignored)
```

## 6. How to Compile
```bash
cd project-03-student-record-management-system
mkdir -p out
javac -d out $(find src -name "*.java")
```

Windows (PowerShell):
```powershell
cd project-03-student-record-management-system
mkdir out
javac -d out (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })
```

## 7. How to Run
```bash
java -cp out com.studentmanagement.main.Main
```

Menu:
```
=========================================
   STUDENT RECORD MANAGEMENT SYSTEM
=========================================
1. Add Student
2. View All Students
3. Search Student
4. Update Student
5. Delete Student
6. Sort Students
7. Filter Students
8. Student Statistics
9. Save Students to File
10. Load Students from File
11. Exit
```

Default file is `students.csv` in the project folder (you can type a custom path at save/load prompts).

## 8. Sample Usage
```
----- Add Student -----
Student ID (positive, unique): 106
Name: Divya
Age (15-60): 20
Department (e.g. CSE/ECE/EEE/MECH): CSE
Email: divya@email.com
Phone (10 digits): 9876543215
CGPA (0.0-10.0): 8.7
Year (1-5): 3
How many subjects/marks to enter? (0 to skip): 2
  Subject 1 name: DSA
  Marks for DSA (0-100): 89
  Subject 2 name: OS
  Marks for OS (0-100): 91
Student added: Student{id=106, name='Divya', ...}

===== STUDENT STATISTICS =====
Total Students: 5
Average CGPA: 8.22
Highest CGPA: 9.1 (Priya)
Lowest CGPA: 6.9
Students by Department:
  CSE: 2
  ECE: 1
  EEE: 1
  MECH: 1
Top 5 Students:
  1. Priya - 9.1
  ...

Error: Student ID already exists: 101
Error: Student not found with ID: 999
```

File format (`students.csv`):
```
id,name,age,department,email,phone,cgpa,year,marks
101,Krishna,20,CSE,krishna@email.com,9876543210,8.4,4,DSA:85;DBMS:88
102,Rahul,21,ECE,rahul@email.com,9876543211,7.8,4,Signals:76;EMF:81
```

## 9. Collections Used
- `List<Student> students` (ArrayList) in `repository/StudentRepository.java` - main ordered records; add/remove/update (`set`)/search/iteration/filtering/sorting.
- `Set<String> departments` (HashSet) in `StudentRepository.java` - unique department names, rebuilt on update/delete.
- `Set<String> studentEmails` (HashSet, lower-cased) in `StudentRepository.java` - unique emails, enforces email uniqueness.
- `Map<Integer, Student> studentMap` (HashMap) in `StudentRepository.java` - fast lookup by ID; demonstrates `put`, `get`, `containsKey`, `remove`, `values()` (`allValues()`), `keySet()` (`allIds()`), `entrySet()` (`allEntries()`).
- `Map<String, Integer> marks` (LinkedHashMap) in `model/Student.java` - subjects/marks per student.

## 10. Generics Implementation
File: `util/GenericUtils.java` (generic class `GenericUtils<T>` + static generic methods). This goes beyond `List<Student>`:
- Instance-level generic: `GenericUtils<T>` with `add(T)`, `getAll()`, `sortedCopy(Comparator<T>)`, `findFirst(Predicate<T>)`.
- Static generic helpers genuinely used by `service/StudentService.java`: `filter(List<T>, Predicate<T>)`, `sorted(List<T>, Comparator<T>)`, `sortInPlace`, `findFirst`, `map(List<T>, Function<T,R>)`, `countIf`, `forEach`.
- Why: reusable type-safe operations for any entity type (students now, any type later) without duplicated loops; e.g. `GenericUtils.filter(students, s -> s.getCgpa() > 8)` and `GenericUtils.map(students, Student::getName)`.

## 11. Sorting/Comparator Implementation
File: `service/StudentService.java` (constants `BY_ID`, `BY_NAME`, `BY_CGPA_DESC`, `BY_CGPA_ASC`, `BY_AGE`, `BY_DEPARTMENT_THEN_NAME`) + `getSorted(Comparator)` (no duplicated sorting code).
- `Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER)` (name)
- `Comparator.comparingDouble(Student::getCgpa).reversed()` (CGPA high-low)
- `Comparator.comparingInt(Student::getAge)` (age)
- `Comparator.comparing(Student::getDepartment).thenComparing(BY_NAME)` (department)
- `Comparator.comparingInt(Student::getId)` (ID) + `Student implements Comparable<Student>` (natural order by ID).
- Sorting executed via `GenericUtils.sorted(list, comparator)` (Stream `sorted().collect()`) and `topStudents` via `stream().sorted(BY_CGPA_DESC).limit(n)`.
- Menu: `main/Main.java` `sortFlow()`.

## 12. File Processing Implementation
File: `util/FileUtils.java`, used by `service/StudentService.java` (`saveToFile`/`loadFromFile`) and `main/Main.java` (`saveFlow`/`loadFlow`).
- Modern APIs: `Path`, `Files.newBufferedWriter` / `Files.newBufferedReader`, `BufferedWriter`/`BufferedReader`, `Files.exists`, `Files.createDirectories`, try-with-resources.
- CSV with header `id,name,age,department,email,phone,cgpa,year,marks`; marks encoded as `subject:mark;subject:mark`.
- Save: writes header + one line per student (`toCsvLine`).
- Load: skips blank lines/header, parses via `fromCsvLine`, validates each record, prints `Skipping invalid line N: <reason>` to stderr and continues (never crashes); `FileProcessingException` for missing file / IO errors; service layer additionally skips duplicate IDs gracefully.
- Logic separated from business logic (repository/service never touch `java.nio` directly).

## 13. Lambda Expressions
Used meaningfully (not filler):
- Filtering: `s -> s.getCgpa() > threshold`, `s -> s.getDepartment().equalsIgnoreCase(...)` (`StudentService`).
- Sorting consumers and comparators via method references: `Student::getName`, `Student::getCgpa`, `sorted.forEach(System.out::println)` (`Main.sortFlow`).
- Iterating: `all.forEach(s -> System.out.println(...))` (`Main.viewAllFlow`), `countByDepartment().forEach((dept, count) -> ...)` (`Main.statisticsFlow`).
- Searching: `.filter(s -> s.getEmail().equalsIgnoreCase(key)).findFirst()` (`StudentRepository`).
- Marks: `marks.forEach((subject, mark) -> ...)` (`Student`/`FileUtils`).

## 14. Stream API
File: `service/StudentService.java` (+ repository search helpers); reports in `main/Main.java` `statisticsFlow()`.
- `filter()`: CGPA/department/year/age filters, `countAboveCgpa`.
- `map()`: `mapToDouble(Student::getCgpa)`, `GenericUtils.map(students, Student::getName)`.
- `sorted()`: all sorting + `topStudents(n)` (`sorted().limit(n)`).
- `collect()`: `Collectors.toList()`, `groupingBy(Student::getDepartment)`, `groupingBy(..., counting())`, `groupingBy(..., averagingDouble(...))`, `groupingBy(Student::getYear)`.
- `count()`, `min()`/`max()` (lowest/highest CGPA), `average()` (average CGPA), `forEach()` (reports), `limit()`, `findFirst()`.
- Example reports: CGPA above value, average CGPA, highest/lowest CGPA, students per department, group by department, group by year, Top N.

## 15. Optional
- `Optional<Student> findById(int id)` (`StudentRepository` wrapping `Map.get`) and `Optional<Student> findByEmail(String)` (stream `findFirst`).
- `Optional<Student> highestCgpa()/lowestCgpa()`, `OptionalDouble averageCgpa()` (`StudentService` via `max`/`min`/`average`).
- Handled with `ifPresentOrElse`, `isPresent`/`get`, `orElse`, `orElseThrow` (`getByIdOrThrow`), `map(...).orElse(...)`, `ifPresent` - see `main/Main.java` `searchFlow`/`statisticsFlow`. Missing students print friendly messages instead of NPEs/crashes.

## 16. Validation
File: `util/ValidationUtils.java`; enforced in `service/StudentService.java` before anything enters collections.
- ID positive; name non-empty (>= 2 chars); age 15-60; department non-blank (stored upper-case); email regex `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$`; phone exactly 10 digits; CGPA 0.0-10.0; year 1-5; subject non-blank; marks 0-100; null checks.
- ID uniqueness + email uniqueness enforced in `StudentRepository` (`DuplicateStudentException`); update re-validates; invalid data rejected with `InvalidStudentDataException` and clear messages; menu input helpers re-prompt on non-numeric input.

## 17. Exception Handling
Files: `exception/StudentNotFoundException.java`, `DuplicateStudentException.java`, `InvalidStudentDataException.java`, `FileProcessingException.java` (message + cause constructors where relevant).
- No empty catches; every catch prints a useful message; `Main.run()` catches domain exceptions per menu action and continues running after recoverable errors (only Exit terminates).
- Examples: update/delete of missing ID -> `StudentNotFoundException`; duplicate ID/email -> `DuplicateStudentException`; bad input/file line -> `InvalidStudentDataException` (line skipped, load continues); missing/IO file errors -> `FileProcessingException` (message + cause preserved).
- Streams/file code uses try-with-resources; `Optional.orElseThrow` converts absence into `StudentNotFoundException`.

## Requirement Mapping

| Requirement | Implementation | File/Class |
|---|---|---|
| List | `List<Student> students` (ArrayList): add/remove/`set`/search/iterate/filter/sort | `repository/StudentRepository.java` |
| Set | `Set<String> departments`, `Set<String> studentEmails` (HashSet): unique depts/emails, maintained on add/update/delete | `repository/StudentRepository.java` |
| Map | `Map<Integer, Student> studentMap` (HashMap, key=ID): put/get/containsKey/remove/values/keySet/entrySet; `Map<String,Integer> marks` per student | `repository/StudentRepository.java`, `model/Student.java` |
| Generics | Generic class `GenericUtils<T>` + static `<T>`/`<T,R>` methods (`filter/sorted/map/findFirst/countIf`) used by service | `util/GenericUtils.java`, used in `service/StudentService.java` |
| Sorting/Comparators | `BY_NAME/BY_CGPA_DESC/BY_AGE/BY_DEPARTMENT_THEN_NAME/BY_ID` via `comparing/comparingDouble/comparingInt/reversed/thenComparing`; `Comparable<Student>` by ID | `service/StudentService.java`, `model/Student.java` |
| File I/O | `Path/Files/BufferedReader/BufferedWriter`, try-with-resources, CSV save/load, missing-file + corrupt-line handling | `util/FileUtils.java` |
| Java 8+ features | Lambdas, Streams, Optional, method refs, `Comparator.comparing`, `Collectors`, NIO, `ifPresentOrElse` | `service/*`, `repository/*`, `main/Main.java` |
| Lambda Expressions | Filter/sort/iterate/search/calculate lambdas | `service/StudentService.java`, `repository/StudentRepository.java`, `main/Main.java` |
| Stream API | filter/map/sorted/collect/count/min/max/average/groupingBy/forEach reports | `service/StudentService.java` |
| Optional | `findById/findByEmail/highest/lowest/average`; `isPresent/orElse/orElseThrow/ifPresent` | `repository/StudentRepository.java`, `service/StudentService.java`, `main/Main.java` |
| Validation | ID/name/age/dept/email/phone/CGPA/year/marks checks; uniqueness; no invalid data in collections | `util/ValidationUtils.java` |
| Exception Handling | 4 custom exceptions, graceful messages, app continues after recoverable errors | `exception/*`, `main/Main.java`, `service/*` |

All requirements verified by compilation (`javac -d out`) and runtime tests (CRUD, duplicate rejection, search miss, sort, filter, statistics, save/load, corrupt-line handling).
