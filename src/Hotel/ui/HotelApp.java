package hotel.ui;

import hotel.exception.*;
import hotel.model.*;
import hotel.service.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class HotelApp {

    private static Scanner scanner = new Scanner(System.in);
    private static RoomService roomService = new RoomService();
    private static GuestService guestService = new GuestService();
    private static BookingService bookingService = new BookingService(roomService);

    private static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        printBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> showAllRooms();
                case 2 -> showAvailableRooms();
                case 3 -> registerGuest();
                case 4 -> makeBooking();
                case 5 -> cancelBooking();
                case 6 -> checkOut();
                case 7 -> System.out.println("Coming soon: View Booking Details");
                case 8 -> System.out.println("Coming soon: Guest Booking History");
                case 9 -> System.out.println("Coming soon: Admin Panel");
                case 0 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void showAllRooms() {
        System.out.println("\n===== ALL ROOMS =====");
        List<Room> rooms = roomService.getAllRooms();
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    private static void showAvailableRooms() {
        System.out.println("\n===== AVAILABLE ROOMS =====");
        List<Room> available = roomService.getAvailableRooms();
        if (available.isEmpty()) {
            System.out.println("No rooms available at this time.");
        } else {
            for (Room room : available) {
                System.out.println(room);
            }
        }
    }

    private static void registerGuest() {
        System.out.println("\n===== REGISTER GUEST =====");
        System.out.print("Enter Name  : ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Phone : ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter Email : ");
        String email = scanner.nextLine().trim();
        guestService.registerGuest(name, phone, email);
    }

    // Full booking flow:
    // 1. Find guest by ID  2. Show available rooms  3. Read room + dates
    // 4. Call bookingService.bookRoom() which throws custom exceptions if invalid
    private static void makeBooking() {
        System.out.println("\n===== BOOK A ROOM =====");

        System.out.print("Enter Guest ID (e.g. G001): ");
        String guestId = scanner.nextLine().trim();

        Guest guest = guestService.findGuestById(guestId);
        if (guest == null) {
            System.out.println("Guest not found. Please register first (Option 3).");
            return;
        }

        showAvailableRooms();

        int roomNumber = readInt("Enter Room Number to book: ");
        LocalDate checkIn  = readDate("Enter Check-In  date (yyyy-MM-dd): ");
        LocalDate checkOut = readDate("Enter Check-Out date (yyyy-MM-dd): ");

        if (checkIn == null || checkOut == null) return;

        try {
            Booking booking = bookingService.bookRoom(guest, roomNumber, checkIn, checkOut);
            System.out.println("\nBooking Confirmed!");
            System.out.println(booking);
        } catch (RoomNotAvailableException e) {
            // Custom exception caught here — service threw it, UI displays it
            System.out.println("ERROR: " + e.getMessage());
        } catch (InvalidDateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // Asks for booking ID, calls service, catches BookingNotFoundException
    private static void cancelBooking() {
        System.out.println("\n===== CANCEL BOOKING =====");
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine().trim();

        try {
            bookingService.cancelBooking(bookingId);
        } catch (BookingNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // Checks out guest, service prints the bill internally
    private static void checkOut() {
        System.out.println("\n===== CHECK-OUT =====");
        System.out.print("Enter Booking ID to check out: ");
        String bookingId = scanner.nextLine().trim();

        try {
            bookingService.checkOut(bookingId);
        } catch (BookingNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     HOTEL BOOKING MANAGEMENT SYSTEM  ║");
        System.out.println("║          Spring 2026 Project          ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    private static void printMainMenu() {
        System.out.println("\n======= MAIN MENU =======");
        System.out.println(" 1. View All Rooms");
        System.out.println(" 2. View Available Rooms");
        System.out.println(" 3. Register Guest");
        System.out.println(" 4. Book a Room");
        System.out.println(" 5. Cancel Booking");
        System.out.println(" 6. Check-Out");
        System.out.println(" 7. View Booking Details");
        System.out.println(" 8. View Guest Booking History");
        System.out.println(" 9. Admin Panel");
        System.out.println(" 0. Exit");
        System.out.println("=========================");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Reads and parses a date — returns null if format is wrong
    private static LocalDate readDate(String prompt) {
        System.out.print(prompt);
        try {
            return LocalDate.parse(scanner.nextLine().trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd (e.g. 2026-04-15)");
            return null;
        }
    }
}
