package hotel.model;

/**
 * SuiteRoom — Inheritance: extends Room.
 * Premium room type with highest price.
 */
public class SuiteRoom extends Room {

    public SuiteRoom(int roomNumber) {
        super(roomNumber, 5000.0); // Suite rooms cost Rs.5000/night
    }

    @Override
    public String getRoomDetails() {
        return "Room #" + getRoomNumber() + " [SUITE] - King Bed, Living Area, Up to 4 Guests";
    }
}
