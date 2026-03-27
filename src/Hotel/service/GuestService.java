package hotel.service;

import hotel.model.Guest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GuestService — Service Layer.
 * Handles guest registration and lookup.
 *
 * HashMap<String, Guest>: guestId → Guest object.
 */
public class GuestService {

    private Map<String, Guest> guestMap = new HashMap<>();
    private int guestCounter = 1;

    /**
     * Registers a new guest and returns the Guest object.
     * Auto-generates a unique guest ID like "G001".
     */
    public Guest registerGuest(String name, String phone, String email) {
        String guestId = "G" + String.format("%03d", guestCounter++);
        Guest guest = new Guest(guestId, name, phone, email);
        guestMap.put(guestId, guest);
        System.out.println("Guest registered successfully! ID: " + guestId);
        return guest;
    }

    /**
     * Finds a guest by ID. Returns null if not found.
     */
    public Guest findGuestById(String guestId) {
        return guestMap.get(guestId); // O(1) HashMap lookup
    }

    /**
     * Returns all registered guests.
     */
    public List<Guest> getAllGuests() {
        return new ArrayList<>(guestMap.values());
    }
}
