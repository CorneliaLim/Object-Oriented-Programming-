import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartRoomBookingSystem {
    private static BookingManager bookingManager;
    private static List<Branch> branches;
    private static Map<String, User> users;

    public static void main(String[] args) {
        initializeSystem();
       
        MainMenu mainMenu = new MainMenu(branches, bookingManager, users);
        
        mainMenu.loadUsersFromFile();
        mainMenu.loadBookingsFromFile();

        mainMenu.show();
    }

    private static void initializeSystem() {
        // Initialize new system
        bookingManager = new BookingManager();
        branches = new ArrayList<>();
        users = new HashMap<>();
        
        // Create branches
        Branch psz = new Branch("PSZ, UTMJB");
        psz.addRoom(new SmartRoom("P101", "Small", 4));
        psz.addRoom(new SmartRoom("P102", "Small", 4));
        psz.addRoom(new SmartRoom("P201", "Large", 8));
        psz.addRoom(new SmartRoom("P202", "Large", 8));
        branches.add(psz);

        Branch przs = new Branch("PRZS, UTMJB");
        przs.addRoom(new SmartRoom("R101", "Small", 4));
        przs.addRoom(new SmartRoom("R102", "Small", 4));
        przs.addRoom(new SmartRoom("R201", "Large", 8));
        przs.addRoom(new SmartRoom("R202", "Large", 8));
        branches.add(przs);

        // Create default admin user
        Admin admin = new Admin("A000", "Admin","admin");
        users.put(admin.getUserId(), admin);
    }
} 