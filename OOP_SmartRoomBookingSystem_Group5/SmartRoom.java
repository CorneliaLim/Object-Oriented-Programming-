import java.io.Serializable;

public class SmartRoom implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomId;
    private String type;
    private int capacity;
    private RoomSchedule schedule;

    public SmartRoom(String roomId, String type, int capacity) {
        this.roomId = roomId;
        this.type = type;
        this.capacity = capacity;
        this.schedule = new RoomSchedule();
    }

    public String getRoomId() {
        return roomId;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public RoomSchedule getSchedule() {
        return schedule;
    }

    public String toString() {
        return "Room ID: " + roomId + ", Type: " + type + ", Capacity: " + capacity;
    }
} 