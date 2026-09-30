package com.studentmanagement.main;

import com.studentmanagement.exception.DuplicateStudentException;
import com.studentmanagement.exception.FileProcessingException;
import com.studentmanagement.exception.InvalidStudentDataException;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.model.Student;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.util.ValidationUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console entry point. Handles menu and user interaction only;
 * business logic lives in StudentService.
 */
public class Main {

    private static final Path DEFAULT_FILE = Paths.get("students.csv");

    private final StudentService service;
    private final Scanner scanner;

    public Main(StudentService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        StudentRepository repository = new StudentRepository();
        StudentService service = new StudentService(repository);
        service.seedSampleData();
        try (Scanner scanner = new Scanner(System.in)) {
            new Main(service, scanner).run();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ", -1);
            try {
                switch (choice) {
                    case 1 -> addStudentFlow();
                    case 2 -> viewAllFlow();
                    case 3 -> searchFlow();
                    case 4 -> updateFlow();
                    case 5 -> deleteFlow();
                    case 6 -> sortFlow();
                    case 7 -> filterFlow();
                    case 8 -> statisticsFlow();
                    case 9 -> saveFlow();
                    case 10 -> loadFlow();
                    case 11 -> {
                        System.out.println("Exiting. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please select 1-11.");
                }
            } catch (StudentNotFoundException | DuplicateStudentException
                    | InvalidStudentDataException | FileProcessingException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=========================================");
        System.out.println("   STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("=========================================");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Sort Students");
        System.out.println("7. Filter Students");
        System.out.println("8. Student Statistics");
        System.out.println("9. Save Students to File");
        System.out.println("10. Load Students from File");
        System.out.println("11. Exit");
    }

    // ---------------- 1. Add ----------------

    private void addStudentFlow() throws InvalidStudentDataException, DuplicateStudentException {
        System.out.println("----- Add Student -----");
        int id = readInt("Student ID (positive, unique): ", 0);
        String name = readLine("Name: ");
        int age = readInt("Age (" + ValidationUtils.MIN_AGE + "-" + ValidationUtils.MAX_AGE + "): ", 0);
        String dept = readLine("Department (e.g. CSE/ECE/EEE/MECH): ");
        String email = readLine("Email: ");
        String phone = readLine("Phone (10 digits): ");
        double cgpa = readDouble("CGPA (0.0-10.0): ", -1);
        int year = readInt("Year (1-5): ", 0);
        Map<String, Integer> marks = readMarks();
        Student added = service.addStudent(id, name, age, dept, email, phone, cgpa, year, marks);
        System.out.println("Student added: " + added);
    }

    private Map<String, Integer> readMarks() {
        Map<String, Integer> marks = new LinkedHashMap<>();
        int n = readInt("How many subjects/marks to enter? (0 to skip): ", 0);
        for (int i = 0; i < n; i++) {
            String subject = readLine("  Subject " + (i + 1) + " name: ");
            int mark = readInt("  Marks for " + subject + " (0-100): ", -1);
            try {
                marks.put(ValidationUtils.validateSubject(subject), ValidationUtils.validateMark(mark));
            } catch (InvalidStudentDataException e) {
                System.out.println("  Invalid marks entry skipped: " + e.getMessage());
            }
        }
        return marks;
    }

    // ---------------- 2. View ----------------

    private void viewAllFlow() {
        List<Student> all = service.getAllStudents();
        if (all.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("----- All Students (" + all.size() + ") -----");
        all.forEach(s -> System.out.println(s.toDetailedString())); // lambda iteration
    }

    // ---------------- 3. Search ----------------

    private void searchFlow() {
        System.out.println("Search by: 1) ID  2) Name  3) Department  4) Email");
        int mode = readInt("Enter option: ", -1);
        switch (mode) {
            case 1 -> {
                int id = readInt("Enter Student ID: ", 0);
                Optional<Student> result = service.findById(id); // Optional
                result.ifPresentOrElse(
                        s -> System.out.println("Found: " + s.toDetailedString()),
                        () -> System.out.println("No student found with ID: " + id));
            }
            case 2 -> {
                String name = readLine("Enter name (or part of name): ");
                List<Student> results = service.searchByName(name);
                printListOrEmpty(results, "No students matching name: " + name);
            }
            case 3 -> {
                String dept = readLine("Enter department: ");
                List<Student> results = service.searchByDepartment(dept);
                printListOrEmpty(results, "No students in department: " + dept);
            }
            case 4 -> {
                String email = readLine("Enter email: ");
                Optional<Student> result = service.findByEmail(email); // Optional
                if (result.isPresent()) {
                    System.out.println("Found: " + result.get().toDetailedString());
                } else {
                    System.out.println("No student found with email: " + email
                            + ". Did you mean one of: " + service.allStudentNames());
                }
            }
            default -> System.out.println("Invalid search option.");
        }
    }

    // ---------------- 4. Update ----------------

    private void updateFlow() throws InvalidStudentDataException, StudentNotFoundException, DuplicateStudentException {
        System.out.println("----- Update Student -----");
        int id = readInt("Enter Student ID to update: ", 0);
        Student existing = service.getByIdOrThrow(id); // orElseThrow inside service
        System.out.println("Current: " + existing.toDetailedString());
        System.out.println("Enter new values (press Enter to keep current).");

        String name = readLineWithDefault("Name [" + existing.getName() + "]: ", existing.getName());
        int age = readIntWithDefault("Age [" + existing.getAge() + "]: ", existing.getAge());
        String dept = readLineWithDefault("Department [" + existing.getDepartment() + "]: ", existing.getDepartment());
        String email = readLineWithDefault("Email [" + existing.getEmail() + "]: ", existing.getEmail());
        String phone = readLineWithDefault("Phone [" + existing.getPhone() + "]: ", existing.getPhone());
        double cgpa = readDoubleWithDefault("CGPA [" + existing.getCgpa() + "]: ", existing.getCgpa());
        int year = readIntWithDefault("Year [" + existing.getYear() + "]: ", existing.getYear());

        System.out.print("Update marks? (y/n): ");
        Map<String, Integer> marks = existing.getMarks().isEmpty()
                ? new LinkedHashMap<>()
                : new LinkedHashMap<>(existing.getMarks());
        String ans = scanner.nextLine().trim();
        if (ans.equalsIgnoreCase("y")) {
            marks = readMarks();
        }
        Student updated = service.updateStudent(id, name, age, dept, email, phone, cgpa, year, marks);
        System.out.println("Updated: " + updated);
    }

    // ---------------- 5. Delete ----------------

    private void deleteFlow() throws StudentNotFoundException {
        System.out.println("----- Delete Student -----");
        int id = readInt("Enter Student ID to delete: ", 0);
        Student removed = service.deleteStudent(id);
        System.out.println("Deleted: " + removed);
    }

    // ---------------- 6. Sort ----------------

    private void sortFlow() {
        System.out.println("Sort by: 1) Name  2) CGPA (high-low)  3) Age  4) Department  5) ID");
        int mode = readInt("Enter option: ", -1);
        List<Student> sorted = switch (mode) {
            case 1 -> service.sortByName();
            case 2 -> service.sortByCgpaDesc();
            case 3 -> service.sortByAge();
            case 4 -> service.sortByDepartment();
            case 5 -> service.sortById();
            default -> null;
        };
        if (sorted == null) {
            System.out.println("Invalid sort option.");
            return;
        }
        System.out.println("----- Sorted Students -----");
        sorted.forEach(System.out::println); // method-reference lambda
    }

    // ---------------- 7. Filter ----------------

    private void filterFlow() {
        System.out.println("Filter: 1) CGPA above value  2) Department  3) Year  4) Age range  5) Top N");
        int mode = readInt("Enter option: ", -1);
        List<Student> results;
        switch (mode) {
            case 1 -> {
                double threshold = readDouble("CGPA above: ", -1);
                results = service.filterByCgpaAbove(threshold);
            }
            case 2 -> {
                String dept = readLine("Department: ");
                results = service.filterByDepartment(dept);
            }
            case 3 -> {
                int year = readInt("Year: ", 0);
                results = service.filterByYear(year);
            }
            case 4 -> {
                int min = readInt("Min age: ", 0);
                int max = readInt("Max age: ", 0);
                results = service.filterByAgeRange(min, max);
            }
            case 5 -> {
                int n = readInt("Top N students: ", 0);
                results = service.topStudents(n);
            }
            default -> {
                System.out.println("Invalid filter option.");
                return;
            }
        }
        printListOrEmpty(results, "No students matched the filter.");
    }

    // ---------------- 8. Statistics ----------------

    private void statisticsFlow() {
        System.out.println("===== STUDENT STATISTICS =====");
        System.out.println("Total Students: " + service.totalCount());
        System.out.println("Average CGPA: " + String.format("%.2f",
                service.averageCgpa().orElse(0.0))); // Optional.orElse
        service.highestCgpa().ifPresentOrElse( // Optional.ifPresent
                s -> System.out.println("Highest CGPA: " + s.getCgpa() + " (" + s.getName() + ")"),
                () -> System.out.println("Highest CGPA: N/A"));
        System.out.println("Lowest CGPA: " + service.lowestCgpa().map(Student::getCgpa).orElse(null));
        System.out.println();
        System.out.println("Students by Department:");
        service.countByDepartment().forEach((dept, count) -> // lambda iteration
                System.out.println("  " + dept + ": " + count));
        System.out.println();
        System.out.println("Average CGPA by Department:");
        service.averageCgpaByDepartment().forEach((dept, avg) ->
                System.out.println("  " + dept + ": " + String.format("%.2f", avg)));
        System.out.println();
        System.out.println("Students grouped by Department:");
        service.groupByDepartment().forEach((dept, list) -> {
            System.out.println("  " + dept + " (" + list.size() + "):");
            list.forEach(s -> System.out.println("    - " + s.getName() + " (" + s.getCgpa() + ")"));
        });
        System.out.println();
        System.out.println("Top 5 Students:");
        List<Student> top = service.topStudents(5);
        for (int i = 0; i < top.size(); i++) {
            Student s = top.get(i);
            System.out.println("  " + (i + 1) + ". " + s.getName() + " - " + s.getCgpa());
        }
    }

    // ---------------- 9/10. File ----------------

    private void saveFlow() throws FileProcessingException {
        Path path = readPath("Save to file [" + DEFAULT_FILE + "]: ", DEFAULT_FILE);
        service.saveToFile(path);
        System.out.println("Saved " + service.totalCount() + " student(s) to " + path.toAbsolutePath());
    }

    private void loadFlow() throws FileProcessingException {
        Path path = readPath("Load from file [" + DEFAULT_FILE + "]: ", DEFAULT_FILE);
        int added = service.loadFromFile(path);
        System.out.println("Loaded " + added + " new student(s) from " + path.toAbsolutePath()
                + ". Total now: " + service.totalCount());
    }

    // ---------------- Input helpers ----------------

    private void printListOrEmpty(List<Student> list, String emptyMessage) {
        if (list.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        list.forEach(s -> System.out.println(s.toDetailedString()));
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String readLineWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    private int readInt(String prompt, int fallbackOnInvalid) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                if (fallbackOnInvalid != 0 || input.equals("0")) {
                    if ("-1".equals(input)) {
                        return -1;
                    }
                    System.out.println("Please enter a valid integer.");
                } else {
                    return fallbackOnInvalid;
                }
            }
        }
    }

    private int readIntWithDefault(String prompt, int defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping " + defaultValue);
            return defaultValue;
        }
    }

    private double readDouble(String prompt, double fallbackOnInvalid) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                if (fallbackOnInvalid == -1 && !input.isEmpty()) {
                    System.out.println("Please enter a valid number.");
                } else {
                    return fallbackOnInvalid;
                }
            }
        }
    }

    private double readDoubleWithDefault(String prompt, double defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping " + defaultValue);
            return defaultValue;
        }
    }

    private Path readPath(String prompt, Path defaultPath) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultPath : Paths.get(input);
    }
}
