package com.hospital.model;

/**
 * Abstract base class demonstrating ABSTRACTION and INHERITANCE.
 * Common attributes for all persons in the hospital.
 */
public abstract class Person {
    private int id;
    private String name;
    private int age;
    private String phone;

    // Default constructor
    public Person() {
    }

    // Parameterized constructor
    public Person(int id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    // Encapsulation: getters and setters
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
        if (age > 0 && age < 150) {
            this.age = age;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Concrete method shared by subclasses
    public String getBasicInfo() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Phone: " + phone;
    }

    // Abstract method - must be implemented by subclasses (runtime polymorphism)
    public abstract void displayInfo();
}
