package hotel.model;

/**
 * Guest — represents a hotel guest.
 * Encapsulation: all fields are private, accessed via getters only.
 */
public class Guest {

    private String guestId;   // e.g. "G001"
    private String name;
    private String phone;
    private String email;

    public Guest(String guestId, String name, String phone, String email) {
        this.guestId = guestId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // --- Getters ---

    public String getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "[" + guestId + "] " + name + " | Phone: " + phone + " | Email: " + email;
    }
}
