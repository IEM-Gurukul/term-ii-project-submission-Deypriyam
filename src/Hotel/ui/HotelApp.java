package hotel.ui;

import hotel.model.*;
import hotel.service.*;
import java.util.List;
import java.util.Scanner;

public class HotelApp {

    private static Scanner scanner = new Scanner(System.in);
    private static RoomService roomService = new RoomService();
    private static GuestService guestService = new GuestService();
    private static BookingService bookingService = new BookingService(roomService);

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
                case 4 -> System.out.println("Coming soon: Book a Room");
                case 5 -> System.out.println("Coming soon: Cancel Booking");
                case 6 -> System.out.println("Coming soon: Check-Out");
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

    // Shows all rooms — polymorphism happens here:
    // room.toString() calls getRoomDetails() on whichever subclass it actually is
    private static void showAllRooms() {
        System.out.println("\n===== ALL ROOMS =====");
        List<Room> rooms = roomService.getAllRooms();
        for (Room room : rooms) {
            System.out.println(room); // calls toString() -> getRoomDetails() polymorphically
        }
    }

    // Shows only rooms where isAvailable() == true
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

    // Reads guest details from console, delegates to GuestService
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
}
