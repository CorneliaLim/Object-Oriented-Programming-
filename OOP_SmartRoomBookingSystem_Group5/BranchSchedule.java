import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class BranchSchedule {
    private Set<DayOfWeek> operatingDays;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Set<LocalDate> holidays;

    public BranchSchedule() { 
        this.operatingDays = EnumSet.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        );
        this.openingTime = LocalTime.of(8, 0);
        this.closingTime = LocalTime.of(20, 0);
        this.holidays = new HashSet<>();
    }

    public void setOperatingDays(Set<DayOfWeek> days) {
        this.operatingDays = new HashSet<>(days);
    }

    public void setOperatingHours(LocalTime opening, LocalTime closing) {
        this.openingTime = opening;
        this.closingTime = closing;
    }

    public void addHoliday(LocalDate date) {
        holidays.add(date);
    }

    public void removeHoliday(LocalDate date) {
        holidays.remove(date);
    }

    public boolean isOperatingDay(LocalDate date) {
        return operatingDays.contains(date.getDayOfWeek()) && !holidays.contains(date);
    }

    public boolean isWithinOperatingHours(LocalTime time) {
        return !time.isBefore(openingTime) && !time.isAfter(closingTime);
    }

    public boolean isValidBookingTime(LocalDate date, LocalTime time) {
        return isOperatingDay(date) && isWithinOperatingHours(time);
    }

    public Set<DayOfWeek> getOperatingDays() {
        return new HashSet<>(operatingDays);
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public Set<LocalDate> getHolidays() {
        return new HashSet<>(holidays);
    }
} 