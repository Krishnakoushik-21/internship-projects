package com.hospital.exception;

/** Thrown when a doctor ID is not found. */
public class DoctorNotFoundException extends Exception {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
