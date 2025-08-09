import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private List<SmartRoom> rooms;

    public RoomManager() {
        this.rooms = new ArrayList<>();
    }

    public void addRoom(SmartRoom room) {
        rooms.add(room);
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

    public SmartRoom getRoomById(String roomId) {
        for (SmartRoom room : rooms) {
            if (room.getRoomId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public List<SmartRoom> getAvailableRooms(RoomType type, LocalDate date, LocalTime time) {
        List<SmartRoom> availableRooms = new ArrayList<>();
        for (SmartRoom room : rooms) {
            if (room.getType().equals(type) && room.getSchedule().isAvailable(date, time)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void displayAllRooms() {
        System.out.println("\n=== All Rooms ===");
        for (SmartRoom room : rooms) {
            System.out.println(room);
        }
    }

    public List<SmartRoom> getRoomsByType(RoomType type) {
        List<SmartRoom> typeRooms = new ArrayList<>();
        for (SmartRoom room : rooms) {
            if (room.getType().equals(type)) {
                typeRooms.add(room);
            }
        }
        return typeRooms;
    }
} 