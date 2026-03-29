[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/pG3gvzt-)
# PCCCS495 – Term II Project

## Project Title
Hotel Booking System

---

## Problem Statement (max 150 words)
Managing hotel reservations manually is error-prone, time-consuming, and difficult to scale.
Guests face confusion over room availability, pricing, and booking confirmation, while hotel staff
struggle to track occupancy, cancellations, and payments in real time.
This project addresses those challenges by building a well-structured, object-oriented Hotel Booking System
that models real-world entities such as guests, rooms, bookings, and payments — demonstrating clean
design, proper abstraction, and robust error handling.

---

## Target User
Hotel front-desk staff and administrators who need to manage room inventory, process guest
check-ins/check-outs, and handle booking records through a console-based interface.

---

## Core Features

- Search and view available rooms by type (Single, Double, Suite)
- Book a room for a guest with date validation and conflict detection
- Cancel an existing booking with status update
- Check-in and Check-out management with bill generation
- View all bookings for a specific guest
- Admin panel: view occupancy summary and room status

---

## OOP Concepts Used

- **Abstraction:** `Room` is an abstract class with abstract method `getRoomDetails()`. It cannot be instantiated directly — only through its subclasses.
- **Inheritance:** `SingleRoom`, `DoubleRoom`, and `SuiteRoom` all extend the abstract `Room` class, inheriting shared fields like `roomNumber` and `pricePerNight`.
- **Polymorphism:** A `List<Room>` holds all room types. When `getRoomDetails()` is called on each room, Java automatically calls the correct subclass version at runtime.
- **Exception Handling:** Three custom checked exceptions — `RoomNotAvailableException`, `InvalidDateException`, `BookingNotFoundException` — are thrown from the service layer and caught in the UI layer.
- **Collections / Threads:** `ArrayList<Room>` is used in `RoomService` for room inventory. `HashMap<String, Booking>` is used in `BookingService` for O(1) booking lookup by ID.

---

## Proposed Architecture Description
The project follows a Layered Architecture with clear separation of concerns across three layers.
The **Model Layer** contains core domain classes: `Room` (abstract), `SingleRoom`, `DoubleRoom`, `SuiteRoom`, `Guest`, and `Booking`.
The **Service Layer** contains `RoomService`, `BookingService`, and `GuestService` which handle all business logic including availability checks, booking creation, cancellation, and bill calculation.
The **UI Layer** contains `HotelApp` which provides a console-based menu, reads user input, calls service methods, and displays results — with no business logic of its own.

---

## How to Run

**Step 1 — Create output folder**
```
mkdir out
```

**Step 2 — Compile all Java files**
```
javac -d out src/hotel/model/*.java src/hotel/exception/*.java src/hotel/service/*.java src/hotel/ui/*.java
```

**Step 3 — Run the program**
```
java -cp out hotel.ui.HotelApp
```

---

## Git Discipline Notes
Minimum 10 meaningful commits required.
