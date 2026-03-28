package hotel.ui;

import hotel.exception.*;
import hotel.model.*;
import hotel.service.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * HotelApp — UI Layer (Main class).
 * Only handles: reading input, calling services, printing results.
 * No business logic here — that is Separation of Concerns.
 */
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
                case 7 -> viewBookingDetails();
                case 8 -> viewGuestBookings();
                case 9 -> adminPanel();
                case 0 -> {
                    System.out.println("\nThank you for using Hotel Booking System. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    // Polymorphism: room.toString() calls getRoomDetails()
    // on the actual subclass (Single/Double/Suite) at runtime
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
            System.out.println("ERROR: " + e.getMessage());
        } catch (InvalidDateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

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

    // Fetches and prints a single booking by ID
    private static void viewBookingDetails() {
        System.out.println("\n===== BOOKING DETAILS =====");
        System.out.print("Enter Booking ID: ");
        String bookingId = scanner.nextLine().trim();

        try {
            Booking booking = bookingService.getBooking(bookingId);
            System.out.println(booking);
        } catch (BookingNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // Uses guestBookingsMap inside BookingService (HashMap lookup by guest ID)
    private static void viewGuestBookings() {
        System.out.println("\n===== GUEST BOOKING HISTORY =====");
        System.out.print("Enter Guest ID: ");
        String guestId = scanner.nextLine().trim();

        List<Booking> bookings = bookingService.getBookingsForGuest(guestId);
        if (bookings.isEmpty()) {
            System.out.println("No bookings found for Guest ID: " + guestId);
        } else {
            for (Booking b : bookings) {
                System.out.println("--------------------");
                System.out.println(b);
            }
        }
    }

    // Admin view: occupancy summary + full booking list
    private static void adminPanel() {
        System.out.println("\n===== ADMIN PANEL =====");
        roomService.printOccupancySummary();

        System.out.println("\n--- All Bookings ---");
        List<Booking> all = bookingService.getAllBookings();
        if (all.isEmpty()) {
            System.out.println("No bookings in system.");
        } else {
            for (Booking b : all) {
                System.out.println("--------------------");
                System.out.println(b);
            }
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
