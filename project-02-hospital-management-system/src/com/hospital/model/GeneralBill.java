package com.hospital.model;

/**
 * General bill - no extra charges.
 * Demonstrates POLYMORPHISM (method overriding).
 */
public class GeneralBill extends Bill {

    public GeneralBill() {
        super();
    }

    public GeneralBill(int billId, Patient patient, double consultationFee,
                       double medicineCharges, double labCharges) {
        super(billId, patient, consultationFee, medicineCharges, labCharges);
    }

    @Override
    public double calculateBillAmount() {
        return baseTotal();
    }

    @Override
    public String getBillType() {
        return "General";
    }
}
