package hotel.exception;

/**
 * Thrown when a guest tries to book a room that is already occupied.
 * Extends Exception (checked exception) — caller is FORCED to handle it.
 */
public class RoomNotAvailableException extends Exception {
    public RoomNotAvailableException(int roomNumber) {
        super("Room #" + roomNumber + " is not available. Please choose another room.");
    }
}
