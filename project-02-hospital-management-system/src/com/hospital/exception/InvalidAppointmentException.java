package com.hospital.exception;

/** Thrown for invalid appointment operations. */
public class InvalidAppointmentException extends Exception {
    public InvalidAppointmentException(String message) {
        super(message);
    }
}
