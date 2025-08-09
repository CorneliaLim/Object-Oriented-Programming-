import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class RoomSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<LocalDate, Map<LocalTime, Boolean>> bookings;

    public RoomSchedule() {
        this.bookings = new HashMap<>();
    }

    public boolean isAvailable(LocalDate date, LocalTime time) {
        if (!bookings.containsKey(date)) {
            return true;
        }
        return !bookings.get(date).getOrDefault(time, false);
    }

    public void bookSlot(LocalDate date, LocalTime time) {
        bookings.putIfAbsent(date, new HashMap<>());
        bookings.get(date).put(time, true);
    }

    public void freeSlot(LocalDate date, LocalTime time) {
        if (bookings.containsKey(date)) {
            bookings.get(date).remove(time);
            if (bookings.get(date).isEmpty()) {
                bookings.remove(date);
            }
        }
    }

    public void displaySchedule() {
        System.out.println("\n=== Room Schedule ===");
        for (Map.Entry<LocalDate, Map<LocalTime, Boolean>> dateEntry : bookings.entrySet()) {
            System.out.println("\nDate: " + dateEntry.getKey());
            for (Map.Entry<LocalTime, Boolean> timeEntry : dateEntry.getValue().entrySet()) {
                if (timeEntry.getValue()) {
                    System.out.println("Time: " + timeEntry.getKey() + " - Booked");
                }
            }
        }
    }
} 