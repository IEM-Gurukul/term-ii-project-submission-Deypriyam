package hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Booking — links a Guest to a Room for a date range.
 * Also tracks booking status: CONFIRMED, CANCELLED, CHECKED_OUT
 *
 * ChronoUnit.DAYS.between() computes how many nights the guest stays.
 */
public class Booking {

    // Enum for booking status — clean alternative to using raw strings
    public enum Status {
        CONFIRMED, CANCELLED, CHECKED_OUT
    }

    private String bookingId;      // e.g. "B001"
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Status status;
    private double totalAmount;

    public Booking(String bookingId, Guest guest, Room room,
                   LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = Status.CONFIRMED;

        // Calculate total cost = nights × price per night
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        this.totalAmount = nights * room.getPricePerNight();
    }

    // --- Getters ---

    public String getBookingId() { return bookingId; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public Status getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "Booking ID : " + bookingId + "\n"
             + "Guest      : " + guest.getName() + " (" + guest.getGuestId() + ")\n"
             + "Room       : " + room.getRoomDetails() + "\n"
             + "Check-In   : " + checkInDate + "\n"
             + "Check-Out  : " + checkOutDate + "\n"
             + "Nights     : " + getNights() + "\n"
             + "Total      : Rs." + totalAmount + "\n"
             + "Status     : " + status;
    }
}
