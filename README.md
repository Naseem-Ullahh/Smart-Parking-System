# 🅿️ Smart Parking Management System

A full-featured **Java Swing** desktop application for managing parking sites, users, and billing — built as an Object-Oriented Programming course project at **NUST (MCS), Islamabad**.

---

## 📋 Overview

The Smart Parking Management System simulates a real-world parking management platform with three distinct user roles, each with their own portal and feature set. The system supports everything from slot booking and session tracking to billing, wallet payments, and admin-level site management — all through a custom-built Java Swing GUI.

---

## ✨ Features

### 👤 Admin Portal
- Secure sign-in with username and password
- Add and remove parking sites with configurable capacity, location, and hourly rate
- View live slot status for any site
- Search any vehicle by license plate and view its parking session
- View all registered regular users
- Generate per-site sales reports for both regular and walk-in users
- Change admin credentials from the Settings panel

### 🙋 Regular User Portal
- Sign up with CNIC, contact number, vehicle details, and password
- Sign in and access a personal dashboard
- Book a specific slot at any available parking site
- Check out and automatically release the slot
- View full parking history in a sortable table
- View outstanding dues broken down by session
- Pay bills via Cash or in-app Wallet
- Top up wallet with quick-select preset amounts
- Change password securely

### 🚶 Walk-In Customer Portal
- Quick check-in — no account needed
- Enter name, contact, vehicle details, site, and slot
- On check-out, an invoice is generated showing duration, check-in/out times, and total bill
- Pay by Cash or Card on exit

---

## 🏗️ Project Structure

```
SmartParkingSystem/
│
├── Main.java            # Entry point — all GUI screens and Swing components
├── Admin.java           # Singleton admin class — site management
├── User.java            # Abstract User + RegularUser + WalkInUser subclasses
├── ParkingSite.java     # Parking site with slot array and booking logic
├── ParkingRecord.java   # Individual parking session record with billing
├── SalesReport.java     # Static utility for generating per-site sales reports
└── Main2.java           # Console-based version of the full system
```

---

## 🧱 OOP Concepts Applied

| Concept | Where Used |
|---|---|
| **Inheritance** | `RegularUser` and `WalkInUser` extend abstract `User` |
| **Abstraction** | `User` is abstract with `showDetails()` enforced in subclasses |
| **Encapsulation** | All fields private with getters/setters throughout |
| **Polymorphism** | `parkOut()` overridden in `WalkInUser` for different billing behaviour |
| **Singleton Pattern** | `Admin.getInstance()` ensures only one admin object exists |
| **Static Collections** | `userMap`, `cnicUserMap`, `WalkInUserMap` for O(1) lookups |

---

## 💻 Tech Stack

- **Language:** Java (JDK 8+)
- **GUI:** Java Swing (custom rounded components — no external libraries)
- **Build:** Compile with `javac`, run with `java`
- **IDE:** Any Java IDE (IntelliJ IDEA / Eclipse / VS Code with Java extension)

---

## 🚀 How to Run

**1. Clone the repository**
```bash
git clone https://github.com/naseem-ustaaz/SmartParkingSystem.git
cd SmartParkingSystem
```

**2. Compile all files from the parent directory**
```bash
javac SmartParkingSystem/*.java
```

**3. Run the GUI version**
```bash
java SmartParkingSystem.Main
```

**Or run the console version**
```bash
java SmartParkingSystem.Main2
```

---



## 📌 Default Admin Credentials

| Field | Value |
|---|---|
| Username | `admin123` |
| Password | `123` |

> ⚠️ These can be changed from the Settings panel inside the Admin Dashboard.

---

## 👨‍💻 Author

**Hanzila Nawaz**
**Naseem Ullah**
**Amaan Shahid**
**Waseem Abbas**
BS Software Engineering — NUST (MCS), Rawalpindi
BESE-30 | 2024–2028

---

## 📄 License

This project was developed for academic purposes as part of the Object-Oriented Programming course at NUST.
