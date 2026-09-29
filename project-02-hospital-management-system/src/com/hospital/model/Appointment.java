package com.hospital.model;

import com.hospital.interfaces.Schedulable;

/**
 * Appointment class demonstrating CLASSES, OBJECTS, CONSTRUCTORS,
 * ENCAPSULATION and INTERFACES (implements Schedulable).
 */
public class Appointment implements Schedulable {
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String status; // Scheduled, Cancelled, Completed

    // Default constructor
    public Appointment() {
        this.status = "Scheduled";
    }

    // Parameterized constructor
    public Appointment(int appointmentId, Patient patient, Doctor doctor, String date) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.status = "Scheduled";
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    // Interface implementation
    @Override
    public void schedule() {
        this.status = "Scheduled";
    }

    @Override
    public void cancel() {
        this.status = "Cancelled";
    }

    @Override
    public boolean isScheduled() {
        return "Scheduled".equals(status);
    }

    public void displayInfo() {
        String patientName = (patient != null) ? patient.getName() : "N/A";
        String doctorName = (doctor != null) ? doctor.getName() : "N/A";
        System.out.println("[Appointment] ID: " + appointmentId
                + ", Patient: " + patientName
                + ", Doctor: " + doctorName
                + ", Date: " + date
                + ", Status: " + status);
    }
}
