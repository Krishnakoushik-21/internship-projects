package com.studentmanagement.util;

import com.studentmanagement.exception.InvalidStudentDataException;
import com.studentmanagement.model.Student;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Handles all input validation. No invalid data is allowed into the collections.
 */
public final class ValidationUtils {

    public static final int MIN_AGE = 15;
    public static final int MAX_AGE = 60;
    public static final double MIN_CGPA = 0.0;
    public static final double MAX_CGPA = 10.0;
    public static final int MIN_YEAR = 1;
    public static final int MAX_YEAR = 5;
    public static final int MIN_MARK = 0;
    public static final int MAX_MARK = 100;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9]{10}$");

    private ValidationUtils() {
    }

    public static int validateId(int id) throws InvalidStudentDataException {
        if (id <= 0) {
            throw new InvalidStudentDataException("Student ID must be a positive number. Got: " + id);
        }
        return id;
    }

    public static String validateName(String name) throws InvalidStudentDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidStudentDataException("Name cannot be empty.");
        }
        if (name.trim().length() < 2) {
            throw new InvalidStudentDataException("Name must contain at least 2 characters.");
        }
        return name.trim();
    }

    public static int validateAge(int age) throws InvalidStudentDataException {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new InvalidStudentDataException(
                    "Age must be between " + MIN_AGE + " and " + MAX_AGE + ". Got: " + age);
        }
        return age;
    }

    public static String validateDepartment(String department) throws InvalidStudentDataException {
        if (department == null || department.trim().isEmpty()) {
            throw new InvalidStudentDataException("Department cannot be blank.");
        }
        return department.trim().toUpperCase();
    }

    public static String validateEmail(String email) throws InvalidStudentDataException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidStudentDataException("Email cannot be blank.");
        }
        String cleaned = email.trim();
        if (!EMAIL_PATTERN.matcher(cleaned).matches()) {
            throw new InvalidStudentDataException("Invalid email format: " + email);
        }
        return cleaned;
    }

    public static String validatePhone(String phone) throws InvalidStudentDataException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidStudentDataException("Phone cannot be blank.");
        }
        String cleaned = phone.trim();
        if (!PHONE_PATTERN.matcher(cleaned).matches()) {
            throw new InvalidStudentDataException("Phone must be exactly 10 digits. Got: " + phone);
        }
        return cleaned;
    }

    public static double validateCgpa(double cgpa) throws InvalidStudentDataException {
        if (cgpa < MIN_CGPA || cgpa > MAX_CGPA) {
            throw new InvalidStudentDataException(
                    "CGPA must be between " + MIN_CGPA + " and " + MAX_CGPA + ". Got: " + cgpa);
        }
        return cgpa;
    }

    public static int validateYear(int year) throws InvalidStudentDataException {
        if (year < MIN_YEAR || year > MAX_YEAR) {
            throw new InvalidStudentDataException(
                    "Year must be between " + MIN_YEAR + " and " + MAX_YEAR + ". Got: " + year);
        }
        return year;
    }

    public static String validateSubject(String subject) throws InvalidStudentDataException {
        if (subject == null || subject.trim().isEmpty()) {
            throw new InvalidStudentDataException("Subject name cannot be blank.");
        }
        return subject.trim();
    }

    public static int validateMark(int mark) throws InvalidStudentDataException {
        if (mark < MIN_MARK || mark > MAX_MARK) {
            throw new InvalidStudentDataException(
                    "Marks must be between " + MIN_MARK + " and " + MAX_MARK + ". Got: " + mark);
        }
        return mark;
    }

    public static void validateMarks(Map<String, Integer> marks) throws InvalidStudentDataException {
        if (marks == null) {
            return;
        }
        for (Map.Entry<String, Integer> entry : marks.entrySet()) {
            validateSubject(entry.getKey());
            if (entry.getValue() == null) {
                throw new InvalidStudentDataException("Mark for '" + entry.getKey() + "' is null.");
            }
            validateMark(entry.getValue());
        }
    }

    public static void validateStudent(Student student) throws InvalidStudentDataException {
        if (student == null) {
            throw new InvalidStudentDataException("Student cannot be null.");
        }
        validateId(student.getId());
        student.setName(validateName(student.getName()));
        validateAge(student.getAge());
        student.setDepartment(validateDepartment(student.getDepartment()));
        student.setEmail(validateEmail(student.getEmail()));
        student.setPhone(validatePhone(student.getPhone()));
        validateCgpa(student.getCgpa());
        validateYear(student.getYear());
        validateMarks(student.getMarks());
    }
}
