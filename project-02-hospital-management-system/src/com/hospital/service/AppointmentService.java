package com.hospital.service;

import com.hospital.exception.InvalidAppointmentException;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {
    private List<Appointment> appointments = new ArrayList<>();
    private int idCounter = 301;

    // Overloading: book with objects vs book with date string
    public Appointment bookAppointment(Patient patient, Doctor doctor, String date)
            throws InvalidAppointmentException {
        if (patient == null || doctor == null) {
            throw new InvalidAppointmentException("Patient and Doctor must not be null.");
        }
        if (date == null || date.isBlank()) {
            throw new InvalidAppointmentException("Appointment date must not be empty.");
        }
        Appointment a = new Appointment(idCounter++, patient, doctor, date);
        a.schedule();
        appointments.add(a);
        return a;
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public Appointment findById(int id) throws InvalidAppointmentException {
        for (Appointment a : appointments) {
            if (a.getAppointmentId() == id) {
                return a;
            }
        }
        throw new InvalidAppointmentException("Appointment with ID " + id + " not found.");
    }

    public void cancelAppointment(int id) throws InvalidAppointmentException {
        Appointment a = findById(id);
        if (!a.isScheduled()) {
            throw new InvalidAppointmentException("Appointment " + id + " is already " + a.getStatus() + ".");
        }
        a.cancel();
    }

    public boolean isEmpty() {
        return appointments.isEmpty();
    }
}
