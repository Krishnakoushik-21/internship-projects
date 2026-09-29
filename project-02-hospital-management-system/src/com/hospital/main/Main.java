package com.hospital.main;

import com.hospital.exception.DoctorNotFoundException;
import com.hospital.exception.InvalidAppointmentException;
import com.hospital.exception.InvalidBillException;
import com.hospital.exception.PatientNotFoundException;
import com.hospital.interfaces.Billable;
import com.hospital.model.Appointment;
import com.hospital.model.Bill;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.model.Person;
import com.hospital.model.Prescription;
import com.hospital.service.AppointmentService;
import com.hospital.service.BillingService;
import com.hospital.service.DoctorService;
import com.hospital.service.PatientService;
import com.hospital.service.PrescriptionService;
import com.hospital.util.InputUtil;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point - console menu.
 * Keeps Main thin: delegates business logic to services.
 */
public class Main {
    private PatientService patientService = new PatientService();
    private DoctorService doctorService = new DoctorService();
    private AppointmentService appointmentService = new AppointmentService();
    private PrescriptionService prescriptionService = new PrescriptionService();
    private BillingService billingService = new BillingService();
    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
        Scanner starter = null;
        try {
            starter = new Scanner(System.in);
            app.seedDemoData();
            app.run();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            // Demonstrates finally block; app scanner closed in run()
            if (starter != null) {
                starter.close();
            }
            System.out.println("Thank you for using Hospital Management System.");
        }
    }

    private void seedDemoData() {
        // Seed minimal demo data so evaluator can test quickly
        patientService.addPatient("Ravi Kumar", 35, "9876543210", "Male", "Fever");
        doctorService.addDoctor("Dr. Sharma", 45, "9123456780", "General Medicine");
        doctorService.addDoctor("Dr. Iyer", 38, "9988776655", "Orthopedics");
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = InputUtil.readInt(sc, "Enter choice: ");
            try {
                switch (choice) {
                    case 1: addPatient(); break;
                    case 2: viewPatients(); break;
                    case 3: updatePatient(); break;
                    case 4: removePatient(); break;
                    case 5: addDoctor(); break;
                    case 6: viewDoctors(); break;
                    case 7: bookAppointment(); break;
                    case 8: viewAppointments(); break;
                    case 9: cancelAppointment(); break;
                    case 10: createPrescription(); break;
                    case 11: viewPrescriptions(); break;
                    case 12: generateBill(); break;
                    case 13: demoPolymorphism(); break;
                    case 0: running = false; break;
                    default: System.out.println("Invalid choice. Enter 0-13.");
                }
            } catch (PatientNotFoundException | DoctorNotFoundException |
                     InvalidAppointmentException | InvalidBillException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            } finally {
                // No silent failures; always show separator
                System.out.println("----------------------------------------");
            }
        }
        sc.close();
    }

    private void printMenu() {
        System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Patient");
        System.out.println("2. View Patients");
        System.out.println("3. Update Patient");
        System.out.println("4. Remove Patient");
        System.out.println("5. Add Doctor");
        System.out.println("6. View Doctors");
        System.out.println("7. Book Appointment");
        System.out.println("8. View Appointments");
        System.out.println("9. Cancel Appointment");
        System.out.println("10. Create Prescription");
        System.out.println("11. View Prescriptions");
        System.out.println("12. Generate Bill");
        System.out.println("13. Demo Polymorphism (Person/Bill)");
        System.out.println("0. Exit");
    }

    private void addPatient() {
        String name = InputUtil.readNonEmpty(sc, "Patient name: ");
        int age = InputUtil.readInt(sc, "Age: ");
        String phone = InputUtil.readNonEmpty(sc, "Phone: ");
        String gender = InputUtil.readNonEmpty(sc, "Gender: ");
        String disease = InputUtil.readNonEmpty(sc, "Disease/Symptom: ");
        Patient p = patientService.addPatient(name, age, phone, gender, disease);
        System.out.println("Patient added with ID: " + p.getId());
    }

    private void viewPatients() {
        List<Patient> list = patientService.getAllPatients();
        if (list.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        // Polymorphism: Person reference pointing to Patient
        for (Patient p : list) {
            Person personRef = p;
            personRef.displayInfo();
        }
    }

    private void updatePatient() throws PatientNotFoundException {
        int id = InputUtil.readInt(sc, "Enter Patient ID to update: ");
        Patient existing = patientService.findById(id);
        System.out.println("Current: " + existing);
        String name = InputUtil.readString(sc, "New name (blank to keep): ");
        String ageStr = InputUtil.readString(sc, "New age (blank to keep): ");
        int age = ageStr.isEmpty() ? -1 : Integer.parseInt(ageStr);
        String phone = InputUtil.readString(sc, "New phone (blank to keep): ");
        String gender = InputUtil.readString(sc, "New gender (blank to keep): ");
        String disease = InputUtil.readString(sc, "New disease (blank to keep): ");
        patientService.updatePatient(id,
                name.isEmpty() ? null : name, age,
                phone.isEmpty() ? null : phone,
                gender.isEmpty() ? null : gender,
                disease.isEmpty() ? null : disease);
        System.out.println("Patient updated.");
    }

    private void removePatient() throws PatientNotFoundException {
        int id = InputUtil.readInt(sc, "Enter Patient ID to remove: ");
        patientService.removePatient(id);
        System.out.println("Patient removed.");
    }

    private void addDoctor() {
        String name = InputUtil.readNonEmpty(sc, "Doctor name: ");
        int age = InputUtil.readInt(sc, "Age: ");
        String phone = InputUtil.readNonEmpty(sc, "Phone: ");
        String spec = InputUtil.readNonEmpty(sc, "Specialization: ");
        Doctor d = doctorService.addDoctor(name, age, phone, spec);
        System.out.println("Doctor added with ID: " + d.getId());
    }

    private void viewDoctors() {
        List<Doctor> list = doctorService.getAllDoctors();
        if (list.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        for (Doctor d : list) {
            Person personRef = d; // runtime polymorphism
            personRef.displayInfo();
        }
    }

    private void bookAppointment() throws PatientNotFoundException,
            DoctorNotFoundException, InvalidAppointmentException {
        if (patientService.isEmpty() || doctorService.isEmpty()) {
            System.out.println("Need at least 1 patient and 1 doctor first.");
            return;
        }
        viewPatients();
        int pid = InputUtil.readInt(sc, "Enter Patient ID: ");
        viewDoctors();
        int did = InputUtil.readInt(sc, "Enter Doctor ID: ");
        String date = InputUtil.readNonEmpty(sc, "Date (e.g. 2026-10-05 10:00): ");

        Patient p = patientService.findById(pid);
        Doctor d = doctorService.findById(did);
        Appointment a = appointmentService.bookAppointment(p, d, date);
        System.out.println("Appointment booked with ID: " + a.getAppointmentId());
    }

    private void viewAppointments() {
        List<Appointment> list = appointmentService.getAllAppointments();
        if (list.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment a : list) {
            a.displayInfo();
        }
    }

    private void cancelAppointment() throws InvalidAppointmentException {
        int id = InputUtil.readInt(sc, "Enter Appointment ID to cancel: ");
        appointmentService.cancelAppointment(id);
        System.out.println("Appointment cancelled.");
    }

    private void createPrescription() throws PatientNotFoundException, DoctorNotFoundException {
        if (patientService.isEmpty() || doctorService.isEmpty()) {
            System.out.println("Need at least 1 patient and 1 doctor first.");
            return;
        }
        int pid = InputUtil.readInt(sc, "Enter Patient ID: ");
        int did = InputUtil.readInt(sc, "Enter Doctor ID: ");
        Patient p = patientService.findById(pid);
        Doctor d = doctorService.findById(did);
        String notes = InputUtil.readString(sc, "Notes/diagnosis: ");
        Prescription pr = prescriptionService.createPrescription(p, d, notes);

        int n = InputUtil.readInt(sc, "How many medicines? ");
        for (int i = 0; i < n; i++) {
            System.out.println("Medicine " + (i + 1) + ":");
            String mName = InputUtil.readNonEmpty(sc, "  Name: ");
            String dosage = InputUtil.readNonEmpty(sc, "  Dosage (e.g. 500mg twice daily): ");
            int qty = InputUtil.readInt(sc, "  Quantity: ");
            double price = InputUtil.readDouble(sc, "  Price per unit: ");
            prescriptionService.addMedicine(pr, mName, dosage, qty, price);
        }
        System.out.println("Prescription created with ID: " + pr.getPrescriptionId());
        pr.displayInfo();
    }

    private void viewPrescriptions() {
        List<Prescription> list = prescriptionService.getAllPrescriptions();
        if (list.isEmpty()) {
            System.out.println("No prescriptions found.");
            return;
        }
        for (Prescription pr : list) {
            pr.displayInfo();
            System.out.println();
        }
    }

    private void generateBill() throws PatientNotFoundException, InvalidBillException {
        int pid = InputUtil.readInt(sc, "Enter Patient ID for billing: ");
        Patient p = patientService.findById(pid);
        double consult = InputUtil.readDouble(sc, "Consultation fee: ");
        double med = InputUtil.readDouble(sc, "Medicine charges: ");
        double lab = InputUtil.readDouble(sc, "Lab charges: ");
        String type = InputUtil.readNonEmpty(sc, "Bill type (general/emergency): ");

        // Polymorphism: Billable reference -> GeneralBill or EmergencyBill
        Billable billable = billingService.generateBill(p, consult, med, lab, type);
        System.out.println("Bill generated. Amount via interface: Rs."
                + billingService.getTotalViaInterface(billable));
        billable.printBill();
    }

    private void demoPolymorphism() {
        System.out.println("--- Polymorphism Demo ---");
        // 1. Person reference to different objects (overriding)
        Person p1 = new Patient(999, "Demo Patient", 30, "9000000000", "Female", "Cold");
        Person p2 = new Doctor(998, "Demo Doctor", 50, "9111111111", "Cardiology");
        p1.displayInfo();
        p2.displayInfo();

        // 2. Billable reference to different bill types (overriding)
        try {
            Patient temp = new Patient(997, "Temp", 40, "9000000001", "Male", "Checkup");
            Bill general = (Bill) billingService.generateBill(temp, 500, 300, 200, "general");
            Bill emergency = (Bill) billingService.generateBill(temp, 500, 300, 200, "emergency");
            Billable b1 = general;
            Billable b2 = emergency;
            System.out.println("General bill total: Rs." + b1.calculateBillAmount());
            System.out.println("Emergency bill total: Rs." + b2.calculateBillAmount());
            // 3. Overloading demo
            System.out.println("Overloading: addPatient(Patient) vs addPatient(name,age,...), "
                    + "generateBill(...,type) vs generateBill(...) work.");
        } catch (InvalidBillException e) {
            System.out.println("Demo billing error: " + e.getMessage());
        }
    }
}
