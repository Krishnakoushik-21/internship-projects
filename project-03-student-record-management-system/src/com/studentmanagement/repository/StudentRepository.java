package com.studentmanagement.repository;

import com.studentmanagement.exception.DuplicateStudentException;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Maintains List/Set/Map collections and basic data access.
 *
 * <ul>
 *   <li>{@code List<Student> students} - main ordered collection (add/remove/update/search/iterate/filter/sort)</li>
 *   <li>{@code Map<Integer, Student> studentMap} - fast lookup by ID (put/get/containsKey/remove/values/keySet/entrySet)</li>
 *   <li>{@code Set<String> departments} - unique department names</li>
 *   <li>{@code Set<String> studentEmails} - unique emails (enforces email uniqueness)</li>
 * </ul>
 */
public class StudentRepository {

    private final List<Student> students = new ArrayList<>();
    private final Map<Integer, Student> studentMap = new HashMap<>();
    private final Set<String> departments = new HashSet<>();
    private final Set<String> studentEmails = new HashSet<>();

    public void addStudent(Student student) throws DuplicateStudentException {
        if (studentMap.containsKey(student.getId())) {
            throw new DuplicateStudentException("Student ID already exists: " + student.getId());
        }
        if (studentEmails.contains(student.getEmail().toLowerCase())) {
            throw new DuplicateStudentException("Email already registered: " + student.getEmail());
        }
        students.add(student);                  // List.add
        studentMap.put(student.getId(), student); // Map.put
        departments.add(student.getDepartment()); // Set.add
        studentEmails.add(student.getEmail().toLowerCase());
    }

    public void updateStudent(Student updated) throws StudentNotFoundException, DuplicateStudentException {
        Student existing = studentMap.get(updated.getId()); // Map.get
        if (existing == null) {
            throw new StudentNotFoundException("Student not found with ID: " + updated.getId());
        }
        String newEmail = updated.getEmail().toLowerCase();
        String oldEmail = existing.getEmail().toLowerCase();
        if (!newEmail.equals(oldEmail) && studentEmails.contains(newEmail)) {
            throw new DuplicateStudentException("Email already registered: " + updated.getEmail());
        }
        int index = students.indexOf(existing);
        students.set(index, updated);             // List update
        studentMap.put(updated.getId(), updated); // Map update
        studentEmails.remove(oldEmail);
        studentEmails.add(newEmail);
        rebuildDepartments();
    }

    public Student deleteStudent(int id) throws StudentNotFoundException {
        if (!studentMap.containsKey(id)) {        // Map.containsKey
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        Student removed = studentMap.remove(id);  // Map.remove
        students.remove(removed);                 // List.remove
        studentEmails.remove(removed.getEmail().toLowerCase());
        rebuildDepartments();
        return removed;
    }

    public Optional<Student> findById(int id) {
        return Optional.ofNullable(studentMap.get(id));
    }

    public Optional<Student> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        String key = email.trim().toLowerCase();
        return students.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(key))
                .findFirst();
    }

    public List<Student> findByName(String namePart) {
        String query = namePart == null ? "" : namePart.trim().toLowerCase();
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public List<Student> findByDepartment(String department) {
        String query = department == null ? "" : department.trim().toUpperCase();
        return students.stream()
                .filter(s -> s.getDepartment().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Set<String> getDepartments() {
        return Collections.unmodifiableSet(departments);
    }

    public Set<String> getAllEmails() {
        return Collections.unmodifiableSet(studentEmails);
    }

    public int count() {
        return students.size();
    }

    public boolean existsById(int id) {
        return studentMap.containsKey(id);
    }

    public void clear() {
        students.clear();
        studentMap.clear();
        departments.clear();
        studentEmails.clear();
    }

    /** Demonstrates Map.values(). */
    public Collection<Student> allValues() {
        return Collections.unmodifiableCollection(studentMap.values());
    }

    /** Demonstrates Map.keySet(). */
    public Set<Integer> allIds() {
        return Collections.unmodifiableSet(studentMap.keySet());
    }

    /** Demonstrates Map.entrySet(). */
    public Set<Map.Entry<Integer, Student>> allEntries() {
        return Collections.unmodifiableSet(studentMap.entrySet());
    }

    private void rebuildDepartments() {
        departments.clear();
        for (Student s : students) { // List iteration
            departments.add(s.getDepartment());
        }
    }
}
