package com.studentmanagement.service;

import com.studentmanagement.exception.DuplicateStudentException;
import com.studentmanagement.exception.FileProcessingException;
import com.studentmanagement.exception.InvalidStudentDataException;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.model.Student;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.util.FileUtils;
import com.studentmanagement.util.GenericUtils;
import com.studentmanagement.util.ValidationUtils;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Contains business logic: validation orchestration, sorting, filtering,
 * statistics (Stream API), and file save/load.
 */
public class StudentService {

    // Java 8 style comparators (reused, no duplicated sorting code)
    public static final Comparator<Student> BY_ID =
            Comparator.comparingInt(Student::getId);
    public static final Comparator<Student> BY_NAME =
            Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Student> BY_CGPA_DESC =
            Comparator.comparingDouble(Student::getCgpa).reversed();
    public static final Comparator<Student> BY_CGPA_ASC =
            Comparator.comparingDouble(Student::getCgpa);
    public static final Comparator<Student> BY_AGE =
            Comparator.comparingInt(Student::getAge);
    public static final Comparator<Student> BY_DEPARTMENT_THEN_NAME =
            Comparator.comparing(Student::getDepartment).thenComparing(BY_NAME);

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public StudentRepository getRepository() {
        return repository;
    }

    // ---------------- CRUD ----------------

    public Student addStudent(int id, String name, int age, String department,
                              String email, String phone, double cgpa, int year,
                              Map<String, Integer> marks)
            throws InvalidStudentDataException, DuplicateStudentException {
        ValidationUtils.validateId(id);
        String vName = ValidationUtils.validateName(name);
        ValidationUtils.validateAge(age);
        String vDept = ValidationUtils.validateDepartment(department);
        String vEmail = ValidationUtils.validateEmail(email);
        String vPhone = ValidationUtils.validatePhone(phone);
        ValidationUtils.validateCgpa(cgpa);
        ValidationUtils.validateYear(year);
        ValidationUtils.validateMarks(marks);
        Student student = new Student(id, vName, age, vDept, vEmail, vPhone, cgpa, year, marks);
        repository.addStudent(student);
        return student;
    }

    public Student addStudent(Student student)
            throws InvalidStudentDataException, DuplicateStudentException {
        ValidationUtils.validateStudent(student);
        repository.addStudent(student);
        return student;
    }

    public Student updateStudent(int id, String name, int age, String department,
                                 String email, String phone, double cgpa, int year,
                                 Map<String, Integer> marks)
            throws InvalidStudentDataException, StudentNotFoundException, DuplicateStudentException {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException("Cannot update. Student not found with ID: " + id);
        }
        String vName = ValidationUtils.validateName(name);
        ValidationUtils.validateAge(age);
        String vDept = ValidationUtils.validateDepartment(department);
        String vEmail = ValidationUtils.validateEmail(email);
        String vPhone = ValidationUtils.validatePhone(phone);
        ValidationUtils.validateCgpa(cgpa);
        ValidationUtils.validateYear(year);
        ValidationUtils.validateMarks(marks);
        Student updated = new Student(id, vName, age, vDept, vEmail, vPhone, cgpa, year, marks);
        repository.updateStudent(updated);
        return updated;
    }

    public Student deleteStudent(int id) throws StudentNotFoundException {
        return repository.deleteStudent(id);
    }

    // ---------------- Search (Optional for single results) ----------------

    public Optional<Student> findById(int id) {
        return repository.findById(id);
    }

    public Student getByIdOrThrow(int id) throws StudentNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
    }

    public Optional<Student> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Student> searchByName(String namePart) {
        return repository.findByName(namePart);
    }

    public List<Student> searchByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    public List<Student> getAllStudents() {
        return repository.getAllStudents();
    }

    // ---------------- Sorting (Comparator + GenericUtils) ----------------

    public List<Student> getSorted(Comparator<Student> comparator) {
        return GenericUtils.sorted(repository.getAllStudents(), comparator);
    }

    public List<Student> sortByName() {
        return getSorted(BY_NAME);
    }

    public List<Student> sortByCgpaDesc() {
        return getSorted(BY_CGPA_DESC);
    }

    public List<Student> sortByAge() {
        return getSorted(BY_AGE);
    }

    public List<Student> sortByDepartment() {
        return getSorted(BY_DEPARTMENT_THEN_NAME);
    }

    public List<Student> sortById() {
        return getSorted(BY_ID);
    }

    // ---------------- Filtering (Lambda + Streams via GenericUtils) ----------------

    public List<Student> filterByCgpaAbove(double threshold) {
        return GenericUtils.filter(getAllStudents(), s -> s.getCgpa() > threshold);
    }

    public List<Student> filterByDepartment(String department) {
        return GenericUtils.filter(getAllStudents(),
                s -> s.getDepartment().equalsIgnoreCase(department.trim()));
    }

    public List<Student> filterByYear(int year) {
        return GenericUtils.filter(getAllStudents(), s -> s.getYear() == year);
    }

    public List<Student> filterByAgeRange(int minAge, int maxAge) {
        return GenericUtils.filter(getAllStudents(),
                s -> s.getAge() >= minAge && s.getAge() <= maxAge);
    }

    public List<Student> topStudents(int n) {
        return getAllStudents().stream()
                .sorted(BY_CGPA_DESC)
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<String> allStudentNames() {
        return GenericUtils.map(getAllStudents(), Student::getName);
    }

    // ---------------- Statistics (Stream API) ----------------

    public long totalCount() {
        return repository.count();
    }

    public OptionalDouble averageCgpa() {
        return getAllStudents().stream().mapToDouble(Student::getCgpa).average();
    }

    public Optional<Student> highestCgpa() {
        return getAllStudents().stream().max(Comparator.comparingDouble(Student::getCgpa));
    }

    public Optional<Student> lowestCgpa() {
        return getAllStudents().stream().min(Comparator.comparingDouble(Student::getCgpa));
    }

    public long countAboveCgpa(double threshold) {
        return getAllStudents().stream().filter(s -> s.getCgpa() > threshold).count();
    }

    public Map<String, Long> countByDepartment() {
        return getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getDepartment, Collectors.counting()));
    }

    public Map<String, List<Student>> groupByDepartment() {
        return getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getDepartment));
    }

    public Map<String, Double> averageCgpaByDepartment() {
        return getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getDepartment,
                        Collectors.averagingDouble(Student::getCgpa)));
    }

    public Map<Integer, List<Student>> groupByYear() {
        return getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getYear));
    }

    public Map<String, Object> buildStatistics() {
        Map<String, Object> stats = new LinkedHashMap<>();
        List<Student> all = getAllStudents();
        stats.put("total", (long) all.size());
        stats.put("averageCgpa", all.stream().mapToDouble(Student::getCgpa).average().orElse(0.0));
        stats.put("highest", highestCgpa().orElse(null));
        stats.put("lowest", lowestCgpa().orElse(null));
        stats.put("countByDept", countByDepartment());
        stats.put("avgByDept", averageCgpaByDepartment());
        stats.put("top5", topStudents(5));
        return stats;
    }

    // ---------------- File processing ----------------

    public void saveToFile(Path path) throws FileProcessingException {
        FileUtils.saveStudents(getAllStudents(), path);
    }

    /**
     * Loads students from file. Existing records are kept; duplicates are skipped gracefully.
     *
     * @return number of newly added students
     */
    public int loadFromFile(Path path) throws FileProcessingException {
        List<Student> loaded = FileUtils.loadStudents(path);
        int added = 0;
        for (Student s : loaded) {
            try {
                ValidationUtils.validateStudent(s);
                repository.addStudent(s);
                added++;
            } catch (DuplicateStudentException e) {
                System.err.println("Skipping duplicate ID " + s.getId() + ": " + e.getMessage());
            } catch (InvalidStudentDataException e) {
                System.err.println("Skipping invalid record ID " + s.getId() + ": " + e.getMessage());
            }
        }
        return added;
    }

    public void seedSampleData() {
        try {
            addStudent(101, "Krishna", 20, "CSE", "krishna@email.com", "9876543210", 8.4, 4, Map.of("DSA", 85, "DBMS", 88));
            addStudent(102, "Rahul", 21, "ECE", "rahul@email.com", "9876543211", 7.8, 4, Map.of("Signals", 76, "EMF", 81));
            addStudent(103, "Priya", 19, "CSE", "priya@email.com", "9876543212", 9.1, 3, Map.of("DSA", 95, "OS", 92));
            addStudent(104, "Amit", 22, "MECH", "amit@email.com", "9876543213", 6.9, 4, Map.of("Thermo", 68, "CAD", 72));
            addStudent(105, "Sneha", 20, "EEE", "sneha@email.com", "9876543214", 8.9, 2, Map.of("Circuits", 90, "Maths", 87));
        } catch (InvalidStudentDataException | DuplicateStudentException e) {
            System.err.println("Seed data issue: " + e.getMessage());
        }
    }
}
