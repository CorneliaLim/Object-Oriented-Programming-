import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class BookingManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Booking> bookings;

    public BookingManager() {
        this.bookings = new ArrayList<>();
    }

    public Booking createBooking(Customer customer, Branch branch, SmartRoom room, LocalDate date, LocalTime time) {
        if (!room.getSchedule().isAvailable(date, time)) {
            throw new IllegalStateException("Room is not available at the specified time.");
        }

        Booking booking = new Booking(customer, branch, room, date, time);
        bookings.add(booking);
        room.getSchedule().bookSlot(date, time);
        return booking;
    }

    public void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {
            booking.display();
        }
    }

    public boolean deleteBooking(String bookingId) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().equals(bookingId)) {
                Booking booking = bookings.remove(i);
                // Free up the room slot
                booking.getRoom().getSchedule().freeSlot(booking.getDate(), booking.getTime());
                return true;
            }
        }
        return false;
    }

    public Booking getBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getBookingsByUserName(String userName) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomer().getName().equals(userName)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    public void clearBookings() {
        bookings.clear();
    }

    public void addBookingFromFile(String bookingId, Customer customer, Branch branch, SmartRoom room, LocalDate date, LocalTime time) {
        Booking booking = new Booking(customer, branch, room, date, time);
        try {
            java.lang.reflect.Field idField = Booking.class.getDeclaredField("bookingId");
            idField.setAccessible(true);
            idField.set(booking, bookingId);
        } catch (Exception e) {
        }
        bookings.add(booking);
        room.getSchedule().bookSlot(date, time);
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
} 