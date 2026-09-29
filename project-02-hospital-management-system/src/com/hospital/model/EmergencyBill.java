package com.hospital.model;

/**
 * Emergency bill - adds 25% emergency charge.
 * Demonstrates POLYMORPHISM (different behavior via same interface).
 */
public class EmergencyBill extends Bill {
    private static final double EMERGENCY_RATE = 0.25;

    public EmergencyBill() {
        super();
    }

    public EmergencyBill(int billId, Patient patient, double consultationFee,
                         double medicineCharges, double labCharges) {
        super(billId, patient, consultationFee, medicineCharges, labCharges);
    }

    @Override
    public double calculateBillAmount() {
        return baseTotal() * (1 + EMERGENCY_RATE);
    }

    @Override
    public String getBillType() {
        return "Emergency (+25%)";
    }
}
