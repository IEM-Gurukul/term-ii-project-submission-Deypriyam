package hotel.model;

/**
 * DoubleRoom — Inheritance: extends Room.
 * Different price and different details string.
 */
public class DoubleRoom extends Room {

    public DoubleRoom(int roomNumber) {
        super(roomNumber, 2500.0); // Double rooms cost Rs.2500/night
    }

    @Override
    public String getRoomDetails() {
        return "Room #" + getRoomNumber() + " [DOUBLE] - 2 Beds, 2 Guests Max";
    }
}
