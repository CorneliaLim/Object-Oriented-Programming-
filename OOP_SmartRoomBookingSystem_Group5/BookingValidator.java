import java.time.LocalDate;
import java.time.LocalTime;

public class BookingValidator {
    public static boolean isValidBookingTime(LocalTime time) {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(20, 0);
        return !time.isBefore(startTime) && !time.isAfter(endTime);
    }

    public static boolean isValidBookingDate(LocalDate date) {
        return !date.isBefore(LocalDate.now());
    }

    public static boolean isRoomAvailable(SmartRoom room, LocalDate date, LocalTime time) {
        return room.getSchedule().isAvailable(date, time);
    }

    public static boolean isValidCapacity(SmartRoom room, int requiredCapacity) {
        return room.getCapacity() >= requiredCapacity;
    }
} 