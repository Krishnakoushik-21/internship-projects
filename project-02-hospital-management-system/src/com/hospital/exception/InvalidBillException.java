package com.hospital.exception;

/** Thrown for invalid billing operations. */
public class InvalidBillException extends Exception {
    public InvalidBillException(String message) {
        super(message);
    }
}
