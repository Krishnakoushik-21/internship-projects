package com.hospital.service;

import com.hospital.exception.PatientNotFoundException;
import com.hospital.model.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles patient CRUD. Demonstrates clean code (Single Responsibility).
 */
public class PatientService {
    private List<Patient> patients = new ArrayList<>();
    private int idCounter = 101;

    // Overloading: add with object vs add with fields
    public Patient addPatient(Patient patient) {
        if (patient.getId() == 0) {
            patient.setId(idCounter++);
        }
        patients.add(patient);
        return patient;
    }

    public Patient addPatient(String name, int age, String phone, String gender, String disease) {
        Patient p = new Patient(idCounter++, name, age, phone, gender, disease);
        patients.add(p);
        return p;
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public Patient findById(int id) throws PatientNotFoundException {
        for (Patient p : patients) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new PatientNotFoundException("Patient with ID " + id + " not found.");
    }

    public void updatePatient(int id, String name, int age, String phone,
                              String gender, String disease) throws PatientNotFoundException {
        Patient p = findById(id);
        if (name != null && !name.isBlank()) p.setName(name);
        if (age > 0) p.setAge(age);
        if (phone != null && !phone.isBlank()) p.setPhone(phone);
        if (gender != null && !gender.isBlank()) p.setGender(gender);
        if (disease != null && !disease.isBlank()) p.setDisease(disease);
    }

    public void removePatient(int id) throws PatientNotFoundException {
        Patient p = findById(id);
        patients.remove(p);
    }

    public boolean isEmpty() {
        return patients.isEmpty();
    }
}
