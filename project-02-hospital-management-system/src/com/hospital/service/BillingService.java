package com.hospital.service;

import com.hospital.exception.InvalidBillException;
import com.hospital.interfaces.Billable;
import com.hospital.model.Bill;
import com.hospital.model.EmergencyBill;
import com.hospital.model.GeneralBill;
import com.hospital.model.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates POLYMORPHISM: Billable reference points to
 * GeneralBill or EmergencyBill at runtime.
 */
public class BillingService {
    private List<Bill> bills = new ArrayList<>();
    private int idCounter = 501;

    // Overloading: generate by type string
    public Bill generateBill(Patient patient, double consultationFee,
                             double medicineCharges, double labCharges,
                             String type) throws InvalidBillException {
        if (patient == null) {
            throw new InvalidBillException("Patient must not be null for billing.");
        }
        if (consultationFee < 0 || medicineCharges < 0 || labCharges < 0) {
            throw new InvalidBillException("Bill amounts must not be negative.");
        }
        Bill bill;
        if ("emergency".equalsIgnoreCase(type)) {
            bill = new EmergencyBill(idCounter++, patient, consultationFee, medicineCharges, labCharges);
        } else if ("general".equalsIgnoreCase(type)) {
            bill = new GeneralBill(idCounter++, patient, consultationFee, medicineCharges, labCharges);
        } else {
            throw new InvalidBillException("Unknown bill type: " + type + ". Use general/emergency.");
        }
        bills.add(bill);
        return bill;
    }

    // Overloaded convenience method (defaults to general)
    public Bill generateBill(Patient patient, double consultationFee,
                             double medicineCharges, double labCharges)
            throws InvalidBillException {
        return generateBill(patient, consultationFee, medicineCharges, labCharges, "general");
    }

    // Runtime polymorphism demo: works with any Billable
    public double getTotalViaInterface(Billable billable) {
        return billable.calculateBillAmount();
    }

    public List<Bill> getAllBills() {
        return bills;
    }
}
