package hotel.model;

/**
 * Abstract base class for all room types.
 * Abstraction: We cannot create a plain "Room" — it must be a specific type.
 * Common fields are here so subclasses don't repeat them (Inheritance benefit).
 */
public abstract class Room {

    private int roomNumber;
    private double pricePerNight;
    private boolean available;

    // Constructor — called by every subclass via super()
    public Room(int roomNumber, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.available = true; // every room starts as available
    }

    // Abstract method — every subclass MUST implement this differently
    // This is what enables Polymorphism later
    public abstract String getRoomDetails();

    // --- Getters and Setters ---

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // toString used when printing room in lists
    @Override
    public String toString() {
        return getRoomDetails() + " | Price: Rs." + pricePerNight + "/night | "
                + (available ? "AVAILABLE" : "BOOKED");
    }
}
