package hotel.exception;

/**
 * Thrown when check-out date is not after check-in date,
 * or when dates are in the past.
 */
public class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }
}
