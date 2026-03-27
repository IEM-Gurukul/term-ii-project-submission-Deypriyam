package hotel.service;

import hotel.exception.*;
import hotel.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BookingService — Service Layer.
 * Contains ALL business logic for bookings.
 *
 * HashMap<String, Booking> bookingsMap:
 *   Key   = bookingId (e.g. "B001")
 *   Value = Booking object
 *   Benefit: O(1) lookup when you have a booking ID.
 *
 * This class throws custom exceptions — it does NOT print error messages.
 * Printing is the UI layer's job.
 */
public class BookingService {

    private Map<String, Booking> bookingsMap = new HashMap<>();
    private Map<String, List<Booking>> guestBookingsMap = new HashMap<>();
    private int bookingCounter = 1;

    private RoomService roomService;

    public BookingService(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Creates a new booking.
     *
     * @throws RoomNotAvailableException if the room is already booked
     * @throws InvalidDateException      if dates are invalid
     */
    public Booking bookRoom(Guest guest, int roomNumber,
                            LocalDate checkIn, LocalDate checkOut)
            throws RoomNotAvailableException, InvalidDateException {

        // --- Date Validation ---
        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidDateException(
                "Check-out date must be after check-in date.");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new InvalidDateException(
                "Check-in date cannot be in the past.");
        }

        // --- Room Availability Check ---
        Room room = roomService.findRoomByNumber(roomNumber);
        if (room == null) {
            throw new RoomNotAvailableException(roomNumber);
        }
        if (!room.isAvailable()) {
            throw new RoomNotAvailableException(roomNumber);
        }

        // --- Create Booking ---
        String bookingId = "B" + String.format("%03d", bookingCounter++);
        Booking booking = new Booking(bookingId, guest, room, checkIn, checkOut);

        // Mark room as occupied
        room.setAvailable(false);

        // Store in HashMap — O(1) insert
        bookingsMap.put(bookingId, booking);

        // Also store under guest ID for quick guest-wise lookup
        guestBookingsMap
            .computeIfAbsent(guest.getGuestId(), k -> new ArrayList<>())
            .add(booking);

        return booking;
    }

    /**
     * Cancels a booking by ID.
     *
     * @throws BookingNotFoundException if ID doesn't exist
     */
    public void cancelBooking(String bookingId) throws BookingNotFoundException {
        Booking booking = bookingsMap.get(bookingId); // O(1) HashMap lookup
        if (booking == null) {
            throw new BookingNotFoundException(bookingId);
        }
        if (booking.getStatus() == Booking.Status.CANCELLED) {
            System.out.println("Booking " + bookingId + " is already cancelled.");
            return;
        }

        // Free the room
        booking.getRoom().setAvailable(true);
        booking.setStatus(Booking.Status.CANCELLED);
        System.out.println("Booking " + bookingId + " has been cancelled.");
    }

    /**
     * Checks out a guest, prints the bill, frees the room.
     *
     * @throws BookingNotFoundException if ID doesn't exist
     */
    public void checkOut(String bookingId) throws BookingNotFoundException {
        Booking booking = bookingsMap.get(bookingId);
        if (booking == null) {
            throw new BookingNotFoundException(bookingId);
        }
        if (booking.getStatus() != Booking.Status.CONFIRMED) {
            System.out.println("Cannot check out — booking status is: " + booking.getStatus());
            return;
        }

        booking.getRoom().setAvailable(true);
        booking.setStatus(Booking.Status.CHECKED_OUT);

        printBill(booking);
    }

    /**
     * Prints a formatted bill for a booking.
     */
    private void printBill(Booking booking) {
        System.out.println("\n==============================");
        System.out.println("         FINAL BILL           ");
        System.out.println("==============================");
        System.out.println("Booking ID : " + booking.getBookingId());
        System.out.println("Guest      : " + booking.getGuest().getName());
        System.out.println("Room       : " + booking.getRoom().getRoomDetails());
        System.out.println("Check-In   : " + booking.getCheckInDate());
        System.out.println("Check-Out  : " + booking.getCheckOutDate());
        System.out.println("Nights     : " + booking.getNights());
        System.out.printf ("Rate       : Rs.%.2f/night%n", booking.getRoom().getPricePerNight());
        System.out.println("------------------------------");
        System.out.printf ("TOTAL DUE  : Rs.%.2f%n", booking.getTotalAmount());
        System.out.println("==============================");
        System.out.println("Thank you for staying with us!");
    }

    /**
     * Returns a booking by ID.
     *
     * @throws BookingNotFoundException if not found
     */
    public Booking getBooking(String bookingId) throws BookingNotFoundException {
        Booking booking = bookingsMap.get(bookingId);
        if (booking == null) throw new BookingNotFoundException(bookingId);
        return booking;
    }

    /**
     * Returns all bookings for a specific guest.
     */
    public List<Booking> getBookingsForGuest(String guestId) {
        return guestBookingsMap.getOrDefault(guestId, new ArrayList<>());
    }

    /**
     * Returns all bookings in the system.
     */
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookingsMap.values());
    }
}
