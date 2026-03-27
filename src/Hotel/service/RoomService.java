package hotel.service;

import hotel.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RoomService — Service Layer.
 * Responsible for managing the hotel's room inventory.
 *
 * Uses ArrayList<Room> (Collection) to store all rooms.
 * Polymorphism: the list holds SingleRoom, DoubleRoom, SuiteRoom
 *               all referenced as Room type.
 */
public class RoomService {

    // ArrayList = Collection — stores all room objects
    private List<Room> rooms = new ArrayList<>();

    /**
     * Pre-loads the hotel with rooms.
     * In a real system this would load from a database or file.
     */
    public RoomService() {
        // Single Rooms: 101–103
        rooms.add(new SingleRoom(101));
        rooms.add(new SingleRoom(102));
        rooms.add(new SingleRoom(103));

        // Double Rooms: 201–203
        rooms.add(new DoubleRoom(201));
        rooms.add(new DoubleRoom(202));
        rooms.add(new DoubleRoom(203));

        // Suites: 301–302
        rooms.add(new SuiteRoom(301));
        rooms.add(new SuiteRoom(302));
    }

    /**
     * Returns all rooms regardless of availability.
     */
    public List<Room> getAllRooms() {
        return rooms;
    }

    /**
     * Returns only rooms that are currently available.
     * Enhanced for-loop iterates the polymorphic list.
     */
    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                available.add(room);
            }
        }
        return available;
    }

    /**
     * Finds a room by its number.
     * Returns null if not found — caller should check.
     */
    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    /**
     * Displays occupancy summary for admin.
     */
    public void printOccupancySummary() {
        int total = rooms.size();
        int booked = 0;
        for (Room room : rooms) {
            if (!room.isAvailable()) booked++;
        }
        System.out.println("=== Occupancy Summary ===");
        System.out.println("Total Rooms  : " + total);
        System.out.println("Booked Rooms : " + booked);
        System.out.println("Available    : " + (total - booked));
    }
}
