package com.hospital.interfaces;

/**
 * Interface demonstrating INTERFACES.
 * Any billable entity must implement billing behavior.
 */
public interface Billable {
    double calculateBillAmount();
    void printBill();
}
