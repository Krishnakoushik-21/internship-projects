package com.hospital.model;

/**
 * Doctor class demonstrating INHERITANCE (extends Person).
 */
public class Doctor extends Person {
    private String specialization;
    private boolean available;

    // Default constructor
    public Doctor() {
        super();
        this.available = true;
    }

    // Parameterized constructor
    public Doctor(int id, String name, int age, String phone, String specialization) {
        super(id, name, age, phone);
        this.specialization = specialization;
        this.available = true;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Polymorphism: method overriding (different behavior than Patient)
    @Override
    public void displayInfo() {
        System.out.println("[Doctor] " + getBasicInfo()
                + ", Specialization: " + specialization
                + ", Available: " + (available ? "Yes" : "No"));
    }

    @Override
    public String toString() {
        return getBasicInfo() + ", Specialization: " + specialization;
    }
}
