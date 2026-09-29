# Hospital Management System - Project 02

## 1. Project Title
Hospital Management System (Object-Oriented & Advanced Java - 70 Points Mandatory Assignment)

## 2. Project Description
A structured console-based Java application to manage hospital operations: patients, doctors,
appointments, prescriptions and billing. Built to demonstrate OOP and advanced Java concepts
with clean packages, custom exceptions and polymorphism. No frameworks - Core Java only.

## 3. Features
- Patient management: Add, View, Update, Remove
- Doctor management: Add, View, Assign to appointment
- Appointment management: Book, View, Cancel (with status Scheduled/Cancelled)
- Prescription management: Create prescription, Add multiple medicines, View with total cost
- Billing: Generate General vs Emergency bills with different calculation (polymorphism)
- Console menu with input validation
- Demo mode for polymorphism (Person/Bill)
- Seeded demo data (1 patient + 2 doctors) for quick evaluation

## 4. Technologies Used
- Java 17+ (tested on Java 21)
- Core Java only: OOP, Collections (ArrayList), Exception Handling, Packages
- Scanner for console I/O
- No external libraries

## 5. Project Structure
```
project-02-hospital-management-system/
├── src/
│   └── com/
│       └── hospital/
│           ├── model/
│           │   ├── Person.java          # abstract base
│           │   ├── Patient.java         # extends Person
│           │   ├── Doctor.java          # extends Person
│           │   ├── Appointment.java     # implements Schedulable
│           │   ├── Medicine.java
│           │   ├── Prescription.java
│           │   ├── Bill.java            # abstract, implements Billable
│           │   ├── GeneralBill.java     # extends Bill
│           │   └── EmergencyBill.java   # extends Bill
│           ├── interfaces/
│           │   ├── Schedulable.java
│           │   └── Billable.java
│           ├── exception/
│           │   ├── PatientNotFoundException.java
│           │   ├── DoctorNotFoundException.java
│           │   ├── InvalidAppointmentException.java
│           │   └── InvalidBillException.java
│           ├── service/
│           │   ├── PatientService.java
│           │   ├── DoctorService.java
│           │   ├── AppointmentService.java
│           │   ├── PrescriptionService.java
│           │   └── BillingService.java
│           ├── util/
│           │   └── InputUtil.java
│           └── main/
│               └── Main.java            # console menu
├── README.md
└── out/ (generated, ignored)
```

## 6. How to Compile
```bash
cd project-02-hospital-management-system
mkdir -p out
javac -d out $(find src -name "*.java")
```

Windows (PowerShell):
```powershell
cd project-02-hospital-management-system
mkdir out
javac -d out (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })
```

## 7. How to Run
```bash
java -cp out com.hospital.main.Main
```

Menu:
```
===== HOSPITAL MANAGEMENT SYSTEM =====
1. Add Patient
2. View Patients
3. Update Patient
4. Remove Patient
5. Add Doctor
6. View Doctors
7. Book Appointment
8. View Appointments
9. Cancel Appointment
10. Create Prescription
11. View Prescriptions
12. Generate Bill
13. Demo Polymorphism (Person/Bill)
0. Exit
```

## 8. OOP Concepts Demonstrated
- Classes & Objects: Patient, Doctor, Appointment, Prescription, Medicine, Bill objects created via services and Main
- Constructors: Default + parameterized in Person, Patient, Doctor, Appointment, Medicine, Prescription, Bill, GeneralBill, EmergencyBill
- Encapsulation: All model fields private with public getters/setters, validation in setters (e.g. Person.setAge)
- Inheritance: Person -> Patient, Person -> Doctor; Bill -> GeneralBill, Bill -> EmergencyBill
- Abstraction: abstract Person.displayInfo(), abstract Bill.calculateBillAmount()/getBillType(), concrete helpers getBasicInfo(), baseTotal(), printBill()
- Interfaces: Schedulable (schedule/cancel/isScheduled) implemented by Appointment; Billable (calculateBillAmount/printBill) implemented by Bill hierarchy
- Polymorphism: Overriding displayInfo, calculateBillAmount; Overloading addPatient, addMedicine, generateBill, bookAppointment; Runtime via Person p = new Patient(), Billable b = new EmergencyBill()
- Exception Handling: Custom checked exceptions + try-catch-finally in Main.run() and Main.main(), throws propagation in services
- Packages: com.hospital.model, service, interfaces, exception, util, main
- Clean Code: SRP (services per entity), small methods, meaningful names, no logic duplication via InputUtil, separation of model/service/main

## 9. Where Each Requirement Is Implemented
- Patient Add/View/Update/Remove: PatientService + Main.addPatient/viewPatients/updatePatient/removePatient
- Doctor Add/View/Assign: DoctorService + Main.addDoctor/viewDoctors, assign in bookAppointment
- Appointment Book/View/Cancel: AppointmentService.bookAppointment/getAllAppointments/cancelAppointment + Schedulable interface
- Prescription Add/View: PrescriptionService.createPrescription/addMedicine + Prescription.addMedicine/getTotalMedicineCost
- Billing Generate + Polymorphism: BillingService.generateBill (general/emergency) + GeneralBill vs EmergencyBill.calculateBillAmount, Billable interface
- Constructors: See Person.java:14-27, Patient.java:12-22, Doctor.java:12-22, etc.
- Encapsulation: See Person.java private fields + getters/setters, all model classes same pattern
- Inheritance: Patient extends Person, Doctor extends Person
- Abstraction: Person.java abstract displayInfo, Bill.java abstract calculateBillAmount
- Interfaces: interfaces/Schedulable.java, interfaces/Billable.java
- Exception Handling: exception/*.java, Main.java try-catch-finally blocks, service throws
- Packages: src/com/hospital/* folders
- Clean Code: service layer separation, util/InputUtil.java for input, small focused methods

## 10. Sample Output
```
===== HOSPITAL MANAGEMENT SYSTEM =====
1. Add Patient
...
Enter choice: 2
[Patient] ID: 101, Name: Ravi Kumar, Age: 35, Phone: 9876543210, Gender: Male, Disease: Fever
----------------------------------------
Enter choice: 13
--- Polymorphism Demo ---
[Patient] ID: 999, Name: Demo Patient, Age: 30, Phone: 9000000000, Gender: Female, Disease: Cold
[Doctor] ID: 998, Name: Demo Doctor, Age: 50, Phone: 9111111111, Specialization: Cardiology, Available: Yes
General bill total: Rs.1000.0
Emergency bill total: Rs.1250.0
----------------------------------------
===== BILL #501 (General) =====
Patient: Test User
Consultation: Rs.500.0
Medicines: Rs.300.0
Lab: Rs.200.0
TOTAL: Rs.1000.0
===============================
```

## 11. Future Improvements
- File/DB persistence (currently in-memory ArrayList)
- Appointment date validation with LocalDateTime
- Login/roles (admin, doctor, receptionist)
- GUI or web version
- Unit tests (JUnit)

## Requirement Mapping

| Requirement | Class/File | How it is demonstrated |
|---|---|---|
| Classes & Objects | model/Patient.java, Doctor.java, Appointment.java, Prescription.java, Medicine.java, Bill.java, Person.java | Meaningful domain classes instantiated in services and Main (e.g. `new Patient(...)`, `new Appointment(...)`) |
| Constructors | All model classes (Person, Patient, Doctor, Appointment, Medicine, Prescription, Bill, GeneralBill, EmergencyBill) | Default + parameterized constructors, e.g. Patient(id,name,age,phone,gender,disease) |
| Encapsulation | model/*.java | Private fields + public getters/setters, validation in Person.setAge, no direct field access |
| Inheritance | model/Patient.java `extends Person`, model/Doctor.java `extends Person`, model/GeneralBill.java `extends Bill` | Patient/Doctor reuse Person id/name/age/phone; Bills reuse fee fields |
| Abstraction | model/Person.java, model/Bill.java | Abstract `displayInfo()`, `calculateBillAmount()`, `getBillType()`; concrete `getBasicInfo()`, `baseTotal()`, `printBill()` |
| Interfaces | interfaces/Schedulable.java, interfaces/Billable.java | Schedulable implemented by Appointment; Billable implemented by Bill hierarchy |
| Polymorphism | model/Patient.displayInfo vs Doctor.displayInfo; GeneralBill vs EmergencyBill calculateBillAmount; service overloads; Main.demoPolymorphism | Overriding + overloading + runtime `Person p = new Patient()`, `Billable b = new EmergencyBill()` |
| Exception Handling | exception/PatientNotFoundException, DoctorNotFoundException, InvalidAppointmentException, InvalidBillException; Main try-catch-finally; services throws | Custom checked exceptions, propagation, try-catch-finally in Main.run() and Main.main(), no empty catch |
| Packages | src/com/hospital/model, service, interfaces, exception, util, main | Clean separation: model, business logic, exceptions, utils, entry point |
| Clean Code | service/*, util/InputUtil.java, main/Main.java | SRP per service, small methods, meaningful names, InputUtil avoids duplication, proper modifiers, consistent formatting |

All concepts verified by compilation (`javac -d out`) and runtime test (`com.hospital.main.Main`).
