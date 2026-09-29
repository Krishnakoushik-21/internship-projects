package com.hospital.model;

/**
 * Patient class demonstrating INHERITANCE (extends Person),
 * ENCAPSULATION and CONSTRUCTORS.
 */
public class Patient extends Person {
    private String gender;
    private String disease;

    // Default constructor
    public Patient() {
        super();
    }

    // Parameterized constructor
    public Patient(int id, String name, int age, String phone, String gender, String disease) {
        super(id, name, age, phone);
        this.gender = gender;
        this.disease = disease;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    // Polymorphism: method overriding
    @Override
    public void displayInfo() {
        System.out.println("[Patient] " + getBasicInfo()
                + ", Gender: " + gender + ", Disease: " + disease);
    }

    @Override
    public String toString() {
        return getBasicInfo() + ", Gender: " + gender + ", Disease: " + disease;
    }
}
