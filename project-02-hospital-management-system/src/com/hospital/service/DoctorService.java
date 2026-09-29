package com.hospital.service;

import com.hospital.exception.DoctorNotFoundException;
import com.hospital.model.Doctor;
import java.util.ArrayList;
import java.util.List;

public class DoctorService {
    private List<Doctor> doctors = new ArrayList<>();
    private int idCounter = 201;

    public Doctor addDoctor(Doctor doctor) {
        if (doctor.getId() == 0) {
            doctor.setId(idCounter++);
        }
        doctors.add(doctor);
        return doctor;
    }

    public Doctor addDoctor(String name, int age, String phone, String specialization) {
        Doctor d = new Doctor(idCounter++, name, age, phone, specialization);
        doctors.add(d);
        return d;
    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public Doctor findById(int id) throws DoctorNotFoundException {
        for (Doctor d : doctors) {
            if (d.getId() == id) {
                return d;
            }
        }
        throw new DoctorNotFoundException("Doctor with ID " + id + " not found.");
    }

    public boolean isEmpty() {
        return doctors.isEmpty();
    }
}
