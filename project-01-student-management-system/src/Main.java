import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager studentManager = new StudentManager();

    public static void main(String[] args) {
        int choice = 0;

        do {
            showMenu();
            choice = readMenuChoice();

            switch (choice) {
                case 1:
                    addStudentFlow();
                    break;
                case 2:
                    studentManager.viewStudents();
                    break;
                case 3:
                    searchStudentFlow();
                    break;
                case 4:
                    calculateResultFlow();
                    break;
                case 5:
                    deleteStudentFlow();
                    break;
                case 6:
                    System.out.println("Exiting Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please try again.");
                    break;
            }
        } while (choice != 6);
    }

    public static void showMenu() {
        System.out.println();
        System.out.println("===== STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Calculate Student Result");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public static int readMenuChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input. Please enter a number from 1 to 6.");
                scanner.nextLine();
            }
        }
    }

    public static void addStudentFlow() {
        System.out.println("\nAdd Student");

        int studentId = readStudentId();
        String studentName = readStudentName();
        int age = readAge();
        double[] marks = new double[Student.SUBJECT_NAMES.length];

        for (int i = 0; i < Student.SUBJECT_NAMES.length; i++) {
            System.out.print("Enter marks for " + Student.SUBJECT_NAMES[i] + ": ");
            while (true) {
                try {
                    double mark = scanner.nextDouble();
                    scanner.nextLine();

                    if (studentManager.validateMarks(mark)) {
                        marks[i] = mark;
                        break;
                    } else {
                        System.out.println("Marks must be between 0 and 100. Please try again.");
                        System.out.print("Enter marks for " + Student.SUBJECT_NAMES[i] + ": ");
                    }
                } catch (InputMismatchException exception) {
                    System.out.println("Invalid numeric input. Please enter a number between 0 and 100.");
                    scanner.nextLine();
                    System.out.print("Enter marks for " + Student.SUBJECT_NAMES[i] + ": ");
                }
            }
        }

        Student newStudent = new Student(studentId, studentName, age, marks);
        if (studentManager.addStudent(newStudent)) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Student could not be added.");
        }
    }

    public static void searchStudentFlow() {
        System.out.println("\nSearch Student");
        System.out.println("1. Search by Student ID");
        System.out.println("2. Search by Student Name");
        System.out.print("Enter search option: ");

        int searchOption = readMenuChoiceInRange(1, 2);

        if (searchOption == 1) {
            int studentId = readPositiveInteger("Enter Student ID: ");
            Student student = studentManager.findStudentById(studentId);
            if (student == null) {
                System.out.println("Student not found.");
            } else {
                displayStudent(student);
            }
        } else {
            String studentName = readStudentNameForSearch();
            Student student = studentManager.findStudentByName(studentName);
            if (student == null) {
                System.out.println("Student not found.");
            } else {
                displayStudent(student);
            }
        }
    }

    public static void calculateResultFlow() {
        int studentId = readPositiveInteger("Enter Student ID to calculate result: ");
        studentManager.calculateResult(studentId);
    }

    public static void deleteStudentFlow() {
        int studentId = readPositiveInteger("Enter Student ID to delete: ");
        Student student = studentManager.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Are you sure you want to delete student ID " + studentId + "? (Y/N): ");
        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("Y")) {
            if (studentManager.deleteStudent(studentId)) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public static int readStudentId() {
        while (true) {
            String input = readText("Enter Student ID: ");
            if (!input.matches("\\d+")) {
                System.out.println("Student ID must be numeric and positive.");
                continue;
            }

            int studentId = Integer.parseInt(input);
            if (studentId <= 0) {
                System.out.println("Student ID must be positive.");
                continue;
            }

            if (studentManager.isDuplicateStudentId(studentId)) {
                System.out.println("Student ID already exists. Please choose another ID.");
                continue;
            }

            return studentId;
        }
    }

    public static String readStudentName() {
        while (true) {
            String name = readText("Enter Student Name: ");
            if (name.trim().isEmpty()) {
                System.out.println("Student name cannot be empty.");
                continue;
            }

            if (!studentManager.validateStudentName(name)) {
                System.out.println("Invalid student name. Please enter at least 2 characters.");
                continue;
            }

            return name.trim();
        }
    }

    public static String readStudentNameForSearch() {
        while (true) {
            String name = readText("Enter Student Name: ");
            if (name.trim().isEmpty()) {
                System.out.println("Student name cannot be empty.");
                continue;
            }
            return name.trim();
        }
    }

    public static int readAge() {
        while (true) {
            try {
                System.out.print("Enter Age: ");
                int age = scanner.nextInt();
                scanner.nextLine();

                if (studentManager.validateAge(age)) {
                    return age;
                } else {
                    System.out.println("Invalid age. Please enter a positive value between 1 and 119.");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Invalid numeric input. Please enter a valid age.");
                scanner.nextLine();
            }
        }
    }

    public static int readPositiveInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive number.");
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input. Please enter a positive number.");
                scanner.nextLine();
            }
        }
    }

    public static int readMenuChoiceInRange(int min, int max) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice. Please choose between " + min + " and " + max + ".");
                System.out.print("Enter search option: ");
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                System.out.print("Enter search option: ");
            }
        }
    }

    public static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static void displayStudent(Student student) {
        System.out.println("\nStudent Details");
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Student Name: " + student.getStudentName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Java: " + student.getMarks()[0]);
        System.out.println("Python: " + student.getMarks()[1]);
        System.out.println("DBMS: " + student.getMarks()[2]);
        System.out.println("Operating Systems: " + student.getMarks()[3]);
        System.out.println("Computer Networks: " + student.getMarks()[4]);
        System.out.println("Total Marks: " + student.getTotalMarks());
        System.out.println("Average Marks: " + student.getAverageMarks());
        System.out.println("Grade: " + student.getGrade());
        System.out.println("Pass/Fail: " + student.getPassStatusText());
    }
}
