package com.hospital.service;

import com.hospital.model.Doctor;
import com.hospital.model.Medicine;
import com.hospital.model.Patient;
import com.hospital.model.Prescription;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionService {
    private List<Prescription> prescriptions = new ArrayList<>();
    private int idCounter = 401;

    public Prescription createPrescription(Patient patient, Doctor doctor, String notes) {
        Prescription p = new Prescription(idCounter++, patient, doctor, notes);
        prescriptions.add(p);
        return p;
    }

    public void addMedicine(Prescription prescription, Medicine medicine) {
        if (prescription != null && medicine != null) {
            prescription.addMedicine(medicine);
        }
    }

    // Overloading: add medicine by fields
    public void addMedicine(Prescription prescription, String name, String dosage,
                            int qty, double price) {
        Medicine m = new Medicine(name, dosage, qty, price);
        addMedicine(prescription, m);
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptions;
    }

    public boolean isEmpty() {
        return prescriptions.isEmpty();
    }
}
