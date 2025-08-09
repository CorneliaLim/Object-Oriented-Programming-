import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<SmartRoom> rooms;

    public Branch(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(SmartRoom room) {
        rooms.add(room);
    }

    public String getName() {
        return name;
    }

    public List<SmartRoom> getAvailableRooms(String type, LocalDate date, LocalTime time) {
        List<SmartRoom> availableRooms = new ArrayList<>();
        for (SmartRoom room : rooms) {
            if (room.getType().equals(type) && room.getSchedule().isAvailable(date, time)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public SmartRoom getRoomById(String id) {
        for (SmartRoom room : rooms) {
            if (room.getRoomId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public boolean deleteRoom(String roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId().equals(roomId)) {
                rooms.remove(i);
                return true;
            }
        }
        return false;
    }

    public void displayRooms() {
        System.out.println("\n=== Rooms in " + name + " ===");
        for (SmartRoom room : rooms) {
            System.out.println(room);
        }
    }
} 