package com.studentmanagement.util;

import com.studentmanagement.exception.FileProcessingException;
import com.studentmanagement.exception.InvalidStudentDataException;
import com.studentmanagement.model.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * File reading/writing logic, kept separate from business logic.
 * Format (CSV): id,name,age,department,email,phone,cgpa,year,marks
 * where marks = subject:mark;subject:mark (may be empty).
 */
public final class FileUtils {

    public static final String HEADER = "id,name,age,department,email,phone,cgpa,year,marks";

    private FileUtils() {
    }

    public static void saveStudents(List<Student> students, Path path) throws FileProcessingException {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            throw new FileProcessingException("Could not create directories for: " + path, e);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(HEADER);
            writer.newLine();
            for (Student s : students) {
                writer.write(toCsvLine(s));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FileProcessingException("Failed to save students to file: " + path, e);
        }
    }

    public static List<Student> loadStudents(Path path) throws FileProcessingException {
        if (!Files.exists(path)) {
            throw new FileProcessingException("File not found: " + path.toAbsolutePath());
        }
        List<Student> result = new ArrayList<>();
        int lineNumber = 0;
        int skipped = 0;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (lineNumber == 1 && line.equalsIgnoreCase(HEADER)) {
                    continue;
                }
                try {
                    Student student = fromCsvLine(line);
                    ValidationUtils.validateStudent(student);
                    result.add(student);
                } catch (InvalidStudentDataException | IllegalArgumentException e) {
                    skipped++;
                    System.err.println("Skipping invalid line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new FileProcessingException("Failed to read students from file: " + path, e);
        }
        if (skipped > 0) {
            System.out.println("Loaded " + result.size() + " student(s), skipped " + skipped + " invalid line(s).");
        }
        return result;
    }

    public static String toCsvLine(Student s) {
        return s.getId() + ","
                + escape(s.getName()) + ","
                + s.getAge() + ","
                + escape(s.getDepartment()) + ","
                + escape(s.getEmail()) + ","
                + escape(s.getPhone()) + ","
                + s.getCgpa() + ","
                + s.getYear() + ","
                + marksToString(s.getMarks());
    }

    public static Student fromCsvLine(String line) throws InvalidStudentDataException {
        String[] parts = line.split(",", 9);
        if (parts.length < 8) {
            throw new InvalidStudentDataException("Corrupted line (expected >= 8 columns): " + line);
        }
        try {
            int id = Integer.parseInt(parts[0].trim());
            String name = unescape(parts[1].trim());
            int age = Integer.parseInt(parts[2].trim());
            String department = unescape(parts[3].trim());
            String email = unescape(parts[4].trim());
            String phone = unescape(parts[5].trim());
            double cgpa = Double.parseDouble(parts[6].trim());
            int year = Integer.parseInt(parts[7].trim());
            Map<String, Integer> marks = new LinkedHashMap<>();
            if (parts.length == 9 && !parts[8].trim().isEmpty()) {
                marks = parseMarks(parts[8].trim());
            }
            return new Student(id, name, age, department, email, phone, cgpa, year, marks);
        } catch (NumberFormatException e) {
            throw new InvalidStudentDataException("Number parsing failed in line: " + line + " (" + e.getMessage() + ")");
        }
    }

    private static Map<String, Integer> parseMarks(String raw) throws InvalidStudentDataException {
        Map<String, Integer> marks = new LinkedHashMap<>();
        String[] pairs = raw.split(";");
        for (String pair : pairs) {
            pair = pair.trim();
            if (pair.isEmpty()) {
                continue;
            }
            String[] kv = pair.split(":", 2);
            if (kv.length != 2) {
                throw new InvalidStudentDataException("Invalid marks entry: '" + pair + "'");
            }
            String subject = ValidationUtils.validateSubject(unescape(kv[0].trim()));
            int mark;
            try {
                mark = Integer.parseInt(kv[1].trim());
            } catch (NumberFormatException e) {
                throw new InvalidStudentDataException("Invalid mark value: '" + kv[1] + "'");
            }
            ValidationUtils.validateMark(mark);
            marks.put(subject, mark);
        }
        return marks;
    }

    private static String marksToString(Map<String, Integer> marks) {
        StringBuilder sb = new StringBuilder();
        marks.forEach((subject, mark) -> {
            if (sb.length() > 0) {
                sb.append(";");
            }
            sb.append(escape(subject)).append(":").append(mark);
        });
        return sb.toString();
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(";", " ").replace(",", " ").replace("\n", " ").trim();
    }

    private static String unescape(String value) {
        return value == null ? "" : value.trim();
    }
}
