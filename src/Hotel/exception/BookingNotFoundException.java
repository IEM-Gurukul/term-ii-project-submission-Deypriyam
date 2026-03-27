package hotel.exception;

/**
 * Thrown when a booking ID provided by the user does not exist
 * in the system — e.g., during cancellation or check-out.
 */
public class BookingNotFoundException extends Exception {
    public BookingNotFoundException(String bookingId) {
        super("No booking found with ID: " + bookingId);
    }
}
