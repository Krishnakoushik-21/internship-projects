public class StudentManager {
    private static final int MAX_STUDENTS = 100;
    private Student[] students;
    private int studentCount;

    public StudentManager() {
        students = new Student[MAX_STUDENTS];
        studentCount = 0;
    }

    public boolean addStudent(Student student) {
        if (student == null) {
            System.out.println("Student object is invalid.");
            return false;
        }

        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Maximum student capacity reached.");
            return false;
        }

        if (!validateStudentName(student.getStudentName())) {
            System.out.println("Invalid student name.");
            return false;
        }

        if (!validateAge(student.getAge())) {
            System.out.println("Invalid age. Age must be a positive value between 1 and 120.");
            return false;
        }

        if (!validateMarks(student.getMarks())) {
            System.out.println("Invalid marks entered. Each mark must be between 0 and 100.");
            return false;
        }

        if (isDuplicateStudentId(student.getStudentId())) {
            System.out.println("Duplicate student ID found. Please enter a unique ID.");
            return false;
        }

        student.calculateResult();
        students[studentCount] = student;
        studentCount = studentCount + 1;
        return true;
    }

    public void viewStudents() {
        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("\n===== STUDENT RECORDS =====");
        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            System.out.println("Student " + (i + 1));
            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("Student Name: " + student.getStudentName());
            System.out.println("Age: " + student.getAge());
            System.out.println("Total Marks: " + student.getTotalMarks());
            System.out.println("Average Marks: " + student.getAverageMarks());
            System.out.println("Grade: " + student.getGrade());
            System.out.println("Pass/Fail: " + student.getPassStatusText());
            System.out.println("------------------------------");
        }
    }

    public boolean deleteStudent(int studentId) {
        int index = findStudentIndexById(studentId);

        if (index == -1) {
            return false;
        }

        for (int i = index; i < studentCount - 1; i++) {
            students[i] = students[i + 1];
        }

        students[studentCount - 1] = null;
        studentCount = studentCount - 1;
        return true;
    }

    public Student findStudentById(int studentId) {
        int index = findStudentIndexById(studentId);
        return (index == -1) ? null : students[index];
    }

    public Student findStudentByName(String studentName) {
        if (studentName == null) {
            return null;
        }

        String searchName = studentName.trim();
        if (searchName.isEmpty()) {
            return null;
        }

        for (int i = 0; i < studentCount; i++) {
            String currentName = students[i].getStudentName().trim();
            if (currentName.equalsIgnoreCase(searchName) || currentName.toLowerCase().contains(searchName.toLowerCase())) {
                return students[i];
            }
        }

        return null;
    }

    public void calculateResult(int studentId) {
        Student student = findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.calculateResult();
        System.out.println("\nStudent ID: " + student.getStudentId());
        System.out.println("Student Name: " + student.getStudentName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Java: " + student.getMarks()[0]);
        System.out.println("Python: " + student.getMarks()[1]);
        System.out.println("DBMS: " + student.getMarks()[2]);
        System.out.println("Operating Systems: " + student.getMarks()[3]);
        System.out.println("Computer Networks: " + student.getMarks()[4]);
        System.out.println("Total Marks: " + student.getTotalMarks());
        System.out.println("Average: " + student.getAverageMarks());
        System.out.println("Grade: " + student.getGrade());
        System.out.println("Pass/Fail: " + student.getPassStatusText());
    }

    public boolean validateStudentName(String studentName) {
        if (studentName == null) {
            return false;
        }

        String cleanedName = studentName.trim();
        return !cleanedName.isEmpty() && cleanedName.length() >= 2;
    }

    public boolean validateAge(int age) {
        return age > 0 && age < 120;
    }

    public boolean validateMarks(double[] marks) {
        if (marks == null || marks.length != Student.SUBJECT_NAMES.length) {
            return false;
        }

        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                return false;
            }
        }

        return true;
    }

    public boolean validateMarks(double mark) {
        return mark >= 0 && mark <= 100;
    }

    public boolean isDuplicateStudentId(int studentId) {
        return findStudentById(studentId) != null;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public double calculateTotal(double[] marks) {
        double total = 0.0;
        for (int i = 0; i < marks.length; i++) {
            total = total + marks[i];
        }
        return total;
    }

    public double calculateAverage(double total, int subjectCount) {
        return subjectCount == 0 ? 0.0 : total / subjectCount;
    }

    public String calculateGrade(double averageMarks) {
        if (averageMarks >= 90 && averageMarks <= 100) {
            return "A+";
        } else if (averageMarks >= 80 && averageMarks < 90) {
            return "A";
        } else if (averageMarks >= 70 && averageMarks < 80) {
            return "B";
        } else if (averageMarks >= 60 && averageMarks < 70) {
            return "C";
        } else if (averageMarks >= 50 && averageMarks < 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public boolean isStudentExists(int studentId) {
        return findStudentById(studentId) != null;
    }

    private int findStudentIndexById(int studentId) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId() == studentId) {
                return i;
            }
        }
        return -1;
    }
}
