package com.hospital.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Prescription class holding medicines for a patient.
 */
public class Prescription {
    private int prescriptionId;
    private Patient patient;
    private Doctor doctor;
    private List<Medicine> medicines;
    private String notes;

    // Default constructor
    public Prescription() {
        this.medicines = new ArrayList<>();
    }

    // Parameterized constructor
    public Prescription(int prescriptionId, Patient patient, Doctor doctor, String notes) {
        this.prescriptionId = prescriptionId;
        this.patient = patient;
        this.doctor = doctor;
        this.notes = notes;
        this.medicines = new ArrayList<>();
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void addMedicine(Medicine medicine) {
        if (medicine != null) {
            medicines.add(medicine);
        }
    }

    public double getTotalMedicineCost() {
        double total = 0;
        for (Medicine m : medicines) {
            total += m.getTotalPrice();
        }
        return total;
    }

    public void displayInfo() {
        String patientName = (patient != null) ? patient.getName() : "N/A";
        String doctorName = (doctor != null) ? doctor.getName() : "N/A";
        System.out.println("[Prescription] ID: " + prescriptionId
                + ", Patient: " + patientName
                + ", Doctor: " + doctorName);
        if (notes != null && !notes.isEmpty()) {
            System.out.println("  Notes: " + notes);
        }
        if (medicines.isEmpty()) {
            System.out.println("  No medicines added.");
        } else {
            System.out.println("  Medicines:");
            for (Medicine m : medicines) {
                m.displayInfo();
            }
            System.out.println("  Total Medicine Cost: Rs." + getTotalMedicineCost());
        }
    }
}
