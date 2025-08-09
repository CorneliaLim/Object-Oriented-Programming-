import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int bookingCounter = 1;
    private String bookingId;
    private Customer customer;
    private Branch branch;
    private SmartRoom room;
    private LocalDate date;
    private LocalTime time;

    public Booking(Customer customer, Branch branch, SmartRoom room, LocalDate date, LocalTime time) {
        this.bookingId = "BK" + String.format("%04d", bookingCounter++);
        this.customer = customer;
        this.branch = branch;
        this.room = room;
        this.date = date;
        this.time = time;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Branch getBranch() {
        return branch;
    }

    public SmartRoom getRoom() {
        return room;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void display() {
        System.out.println("\n=== Booking Details ===");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Branch: " + branch.getName());
        System.out.println("Room: " + room);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Customer ID: " + customer.getUserId());
    }
} 