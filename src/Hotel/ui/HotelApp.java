package hotel.ui;

import hotel.service.*;
import java.util.Scanner;

/**
 * HotelApp — UI Layer (Main class).
 * Only handles: reading input, calling services, printing results.
 * No business logic here.
 */
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
                case 1 -> System.out.println("Coming soon: View All Rooms");
                case 2 -> System.out.println("Coming soon: View Available Rooms");
                case 3 -> System.out.println("Coming soon: Register Guest");
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
