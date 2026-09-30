package com.studentmanagement.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Stores student information.
 * Natural ordering is by student ID.
 */
public class Student implements Comparable<Student> {

    private int id;
    private String name;
    private int age;
    private String department;
    private String email;
    private String phone;
    private double cgpa;
    private int year;
    private Map<String, Integer> marks;

    public Student() {
        this.marks = new LinkedHashMap<>();
    }

    public Student(int id, String name, int age, String department,
                   String email, String phone, double cgpa, int year) {
        this(id, name, age, department, email, phone, cgpa, year, new LinkedHashMap<>());
    }

    public Student(int id, String name, int age, String department,
                   String email, String phone, double cgpa, int year,
                   Map<String, Integer> marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.cgpa = cgpa;
        this.year = year;
        this.marks = marks != null ? new LinkedHashMap<>(marks) : new LinkedHashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Map<String, Integer> getMarks() {
        return Collections.unmodifiableMap(marks);
    }

    public void setMarks(Map<String, Integer> marks) {
        this.marks = marks != null ? new LinkedHashMap<>(marks) : new LinkedHashMap<>();
    }

    public void addMark(String subject, int mark) {
        marks.put(subject, mark);
    }

    public void removeMark(String subject) {
        marks.remove(subject);
    }

    public double getAverageMarks() {
        if (marks.isEmpty()) {
            return 0.0;
        }
        return marks.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "Student{id=%d, name='%s', age=%d, dept='%s', email='%s', phone='%s', cgpa=%.2f, year=%d, subjects=%d, avgMarks=%.1f}",
                id, name, age, department, email, phone, cgpa, year, marks.size(), getAverageMarks());
    }

    public String toDetailedString() {
        StringBuilder sb = new StringBuilder(toString());
        if (!marks.isEmpty()) {
            sb.append("\n  Marks: ");
            marks.forEach((subject, mark) -> sb.append(subject).append("=").append(mark).append(" "));
        }
        return sb.toString();
    }
}
