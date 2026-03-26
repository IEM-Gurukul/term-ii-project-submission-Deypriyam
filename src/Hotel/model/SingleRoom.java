package hotel.model;

/**
 * SingleRoom — Inheritance: extends Room, inherits all fields.
 * Overrides getRoomDetails() — this is Polymorphism in action.
 */
public class SingleRoom extends Room {

    public SingleRoom(int roomNumber) {
        super(roomNumber, 1500.0); // Single rooms cost Rs.1500/night
    }

    @Override
    public String getRoomDetails() {
        return "Room #" + getRoomNumber() + " [SINGLE] - 1 Bed, 1 Guest Max";
    }
}
