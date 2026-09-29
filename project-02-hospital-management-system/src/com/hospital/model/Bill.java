package com.hospital.model;

import com.hospital.interfaces.Billable;

/**
 * Abstract Bill class demonstrating ABSTRACTION.
 * Subclasses provide different billing calculations (POLYMORPHISM).
 */
public abstract class Bill implements Billable {
    private int billId;
    private Patient patient;
    private double consultationFee;
    private double medicineCharges;
    private double labCharges;

    // Default constructor
    public Bill() {
    }

    // Parameterized constructor
    public Bill(int billId, Patient patient, double consultationFee,
                double medicineCharges, double labCharges) {
        this.billId = billId;
        this.patient = patient;
        this.consultationFee = consultationFee;
        this.medicineCharges = medicineCharges;
        this.labCharges = labCharges;
    }

    public int getBillId() {
        return billId;
    }

    public Patient getPatient() {
        return patient;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getMedicineCharges() {
        return medicineCharges;
    }

    public double getLabCharges() {
        return labCharges;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Abstract method - subclasses must define their own calculation
    @Override
    public abstract double calculateBillAmount();

    // Concrete helper shared by subclasses
    protected double baseTotal() {
        return consultationFee + medicineCharges + labCharges;
    }

    // Concrete method using abstract method (template pattern)
    @Override
    public void printBill() {
        String patientName = (patient != null) ? patient.getName() : "N/A";
        System.out.println("===== BILL #" + billId + " (" + getBillType() + ") =====");
        System.out.println("Patient: " + patientName);
        System.out.println("Consultation: Rs." + consultationFee);
        System.out.println("Medicines: Rs." + medicineCharges);
        System.out.println("Lab: Rs." + labCharges);
        System.out.println("TOTAL: Rs." + calculateBillAmount());
        System.out.println("===============================");
    }

    public abstract String getBillType();
}
