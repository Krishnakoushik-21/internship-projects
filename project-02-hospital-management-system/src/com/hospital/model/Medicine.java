package com.hospital.model;

/**
 * Medicine class - part of prescription.
 */
public class Medicine {
    private String name;
    private String dosage;
    private int quantity;
    private double pricePerUnit;

    // Default constructor
    public Medicine() {
    }

    // Parameterized constructor
    public Medicine(String name, String dosage, int quantity, double pricePerUnit) {
        this.name = name;
        this.dosage = dosage;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getTotalPrice() {
        return quantity * pricePerUnit;
    }

    public void displayInfo() {
        System.out.println("  - " + name + " (" + dosage + ") x" + quantity
                + " @ Rs." + pricePerUnit + " = Rs." + getTotalPrice());
    }
}
