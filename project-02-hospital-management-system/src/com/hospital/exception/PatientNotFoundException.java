package com.hospital.exception;

/** Thrown when a patient ID is not found. */
public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
