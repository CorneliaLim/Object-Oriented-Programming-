import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;

public class MainMenu {
    private List<Branch> branches;
    private BookingManager bookingManager;
    private Map<String, User> users;
    private Scanner scanner;
    private User currentUser;

    private static final String USERS_FILE = "users.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";

    public MainMenu(List<Branch> branches, BookingManager bookingManager, Map<String, User> users) {
        this.branches = branches;
        this.bookingManager = bookingManager;
        this.users = users;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            if (currentUser == null) {
                loginMenu();
            } else {
                if (currentUser instanceof Admin) {
                    adminMenu();
                } else {
                    customerMenu();
                }
            }
        }
    }

    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
        }
    }

    private void waitForInput() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void loginMenu() {
        clearScreen();
        System.out.println("\n=== Smart Room Booking System ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine();
            waitForInput();
            return;
        }
        scanner.nextLine();

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Thank you for using the Smart Room Booking System!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
                waitForInput();
        }
    }

    private void adminMenu() {
        clearScreen();
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. User Management");
        System.out.println("2. Building Management");
        System.out.println("3. Room Management");
        System.out.println("4. View All Bookings");
        System.out.println("5. Logout");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");

        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine();
            waitForInput();
            return;
        }
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                userManagementMenu();
                break;
            case 2:
                buildingManagementMenu();
                break;
            case 3:
                roomManagementMenu();
                break;
            case 4:
                viewAllBookings();
                break;
            case 5:
                currentUser = null;
                break;
            case 6:
                System.out.println("Thank you for using the Smart Room Booking System!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
                waitForInput();
        }
    }

    private void customerMenu() {
        clearScreen();
        System.out.println("\n=== Customer Menu ===");
        System.out.println("1. View Available Rooms");
        System.out.println("2. Book a Room");
        System.out.println("3. Delete Booking");
        System.out.println("4. View My Bookings");
        System.out.println("5. Logout");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");

        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine();
            waitForInput();
            return;
        }
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                viewAllRooms();
                waitForInput();
                break;
            case 2:
                bookRoom();
                break;
            case 3:
                deleteBooking();
                break;
            case 4:
                viewMyBookings();
                break;
            case 5:
                currentUser = null;
                break;
            case 6:
                System.out.println("Thank you for using the Smart Room Booking System!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
                waitForInput();
        }
    }

    private void login() {
        clearScreen();
        System.out.println("\n=== Login ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        String password = "";
        try {
            java.io.Console console = System.console();
            if (console != null) {
                char[] pwd = console.readPassword("Enter Password: ");
                password = new String(pwd);
            } else {
                System.out.print("Enter Password: ");
                password = scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
        }

        User user = users.get(userId);
        if (user != null && user.getName().equals(name) && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful!");
            waitForInput();
        } else {
            System.out.println("Invalid credentials!");
            waitForInput();
        }
    }

    private String generateNextUserId(String role) {
        int maxId = 0;
        String prefix = role.equalsIgnoreCase("Admin") ? "A" : "C";
        for (String id : users.keySet()) {
            if (id.startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return prefix + String.format("%03d", maxId + 1);
    }

    private void register() {
        clearScreen();
        System.out.println("\n=== Register ===");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Role (Admin/Customer): ");
        String role = scanner.nextLine().trim();
        String password = "";
        try {
            java.io.Console console = System.console();
            if (console != null) {
                char[] pwd = console.readPassword("Enter Password: ");
                password = new String(pwd);
            } else {
                System.out.print("Enter Password: ");
                password = scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
        }

        String userId = generateNextUserId(role);

        User newUser;
        if (role.equalsIgnoreCase("Admin")) {
            newUser = new Admin(userId, name, password);
        } else {
            newUser = new Customer(userId, name, password);
        }

        users.put(userId, newUser);
        saveUsersToFile();
        System.out.println("Registration successful! Your User ID is: " + userId);
        waitForInput();
    }

    private void userManagementMenu() {
        clearScreen();
        System.out.println("\n=== User Management ===");
        System.out.println("1. Add User");
        System.out.println("2. Delete User");
        System.out.println("3. View All Users");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                deleteUser();
                break;
            case 3:
                viewAllUsers();
                waitForInput();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                waitForInput();
        }
    }

    private void deleteUser() {
        clearScreen();
        System.out.println("\n=== Delete User ===");
        viewAllUsers();
        System.out.print("Enter User ID to delete: ");
        String userId = scanner.nextLine();

        if (userId.equals("A000")) {
            System.out.println("Cannot delete default admin!");
            waitForInput();
            return;
        }

        if (users.remove(userId) != null) {
            saveUsersToFile();
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User not found!");
        }
        waitForInput();
    }

    private void viewAllUsers() {
       System.out.println("=== All Users ===");
        int index = 1;
        for (User u : users.values()) {
            System.out.println("User " + index + ":");
            index++;
            System.out.printf("ID: %-10s Name: %-25s\t", u.getUserId(), u.getName());
            u.displayRole();
            System.out.println("");
        }
    }

    private void buildingManagementMenu() {
        clearScreen();
        System.out.println("\n=== Building Management ===");
        System.out.println("1. Add Building");
        System.out.println("2. Delete Building");
        System.out.println("3. View All Buildings");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                addBuilding();
                break;
            case 2:
                deleteBuilding();
                break;
            case 3:
                viewAllBuildings();
                waitForInput();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                waitForInput();
        }
    }

    private void addBuilding() {
        clearScreen();
        System.out.println("\n=== Add Building ===");
        System.out.print("Enter Building Name: ");
        String name = scanner.nextLine();
        branches.add(new Branch(name));
        System.out.println("Building added successfully!");
        waitForInput();
    }

    private void deleteBuilding() {
        clearScreen();
        System.out.println("\n=== Delete Building ===");
        viewAllBuildings();
        System.out.print("Enter building number to delete: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); 

        if (index >= 0 && index < branches.size()) {
            branches.remove(index);
            System.out.println("Building deleted successfully!");
        } else {
            System.out.println("Invalid building number!");
        }
        waitForInput();
    }

    private void viewAllBuildings() {
        System.out.println("\n=== All Buildings ===");
        for (int i = 0; i < branches.size(); i++) {
            System.out.println((i + 1) + ". " + branches.get(i).getName());
        }
    }

    private void roomManagementMenu() {
        clearScreen();
        System.out.println("\n=== Room Management ===");
        System.out.println("1. Add Room");
        System.out.println("2. Delete Room");
        System.out.println("3. View All Rooms");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                addRoom();
                break;
            case 2:
                deleteRoom();
                break;
            case 3:
                viewAllRooms();
                waitForInput();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                waitForInput();
        }
    }

    private void addRoom() {
        clearScreen();
        System.out.println("\n=== Add Room ===");
        viewAllBuildings();
        System.out.print("Select building number: ");
        int buildingIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 
        if (buildingIndex < 0 || buildingIndex >= branches.size()) {
            System.out.println("Invalid building selection!");
            waitForInput();
            return;
        }

        System.out.print("Enter Room ID: ");
        String roomId = scanner.nextLine();
        System.out.print("Enter Room Type (Small/Large): ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter Room Capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine(); 

        SmartRoom room = new SmartRoom(roomId, type.substring(0,1).toUpperCase() + type.substring(1).toLowerCase(), capacity);
        branches.get(buildingIndex).addRoom(room);
        System.out.println("Room added successfully!");
        waitForInput();
    }

    private void deleteRoom() {
        clearScreen();
        System.out.println("\n=== Delete Room ===");
        viewAllBuildings();
        System.out.print("Select building number: ");
        int buildingIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 

        if (buildingIndex < 0 || buildingIndex >= branches.size()) {
            System.out.println("Invalid building selection!");
            waitForInput();
            return;
        }

        Branch branch = branches.get(buildingIndex);
        branch.displayRooms();
        System.out.print("Enter Room ID to delete: ");
        String roomId = scanner.nextLine();

        if (branch.deleteRoom(roomId)) {
            System.out.println("Room deleted successfully!");
        } else {
            System.out.println("Room not found!");
        }
        waitForInput();
    }

    private void viewAllRooms() {
        System.out.println("\n=== All Rooms ===");
        for (Branch branch : branches) {
            branch.displayRooms();
        }
    }

    private void bookRoom() {
        clearScreen();
        System.out.println("\n=== Book a Room ===");
        viewAllBuildings();
        int buildingIndex = -1;
        while (true) {
            try {
                System.out.print("Select building number: ");
                buildingIndex = scanner.nextInt() - 1;
                scanner.nextLine(); 
                if (buildingIndex < 0 || buildingIndex >= branches.size()) {
                    System.out.println("Invalid building selection!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }

        Branch selectedBranch = branches.get(buildingIndex);
        System.out.print("Enter room type (Small/Large): ");
        String roomType = scanner.nextLine().trim();
        roomType = roomType.substring(0,1).toUpperCase() + roomType.substring(1).toLowerCase();

        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format!");
            waitForInput();
            return;
        }

        System.out.print("Enter time (HH:mm): ");
        String timeStr = scanner.nextLine();
        LocalTime time;
        try {
            time = LocalTime.parse(timeStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format!");
            waitForInput();
            return;
        }

        List<SmartRoom> availableRooms = selectedBranch.getAvailableRooms(roomType, date, time);
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms found!");
            waitForInput();
            return;
        }

        int roomIndex = -1;
        while (true) {
            try {
                System.out.println("\nAvailable Rooms:");
                for (int i = 0; i < availableRooms.size(); i++) {
                    System.out.println((i + 1) + ". " + availableRooms.get(i));
                }
                System.out.print("Select room number: ");
                roomIndex = scanner.nextInt() - 1;
                scanner.nextLine(); 
                if (roomIndex < 0 || roomIndex >= availableRooms.size()) {
                    System.out.println("Invalid room selection!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }

        try {
            Booking booking = bookingManager.createBooking((Customer) currentUser, 
                selectedBranch, availableRooms.get(roomIndex), date, time);
            saveBookingsToFile();
            System.out.println("Booking successful!");
            booking.display();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
        waitForInput();
    }

    private void deleteBooking() {
        clearScreen();
        System.out.println("\n=== Delete Booking ===");
        System.out.print("Enter Booking ID: ");
        String bookingId = scanner.nextLine();

        if (bookingManager.deleteBooking(bookingId)) {
            saveBookingsToFile();
            System.out.println("Booking deleted successfully!");
        } else {
            System.out.println("Booking not found!");
        }
        waitForInput();
    }

    private void viewMyBookings() {
        clearScreen();
        System.out.println("\n=== My Bookings ===");
        List<Booking> userBookings = bookingManager.getBookingsByUserName(currentUser.getName());
        if (userBookings.isEmpty()) {
            System.out.println("No bookings found!");
        } else {
            for (Booking booking : userBookings) {
                booking.display();
            }
        }
        waitForInput();
    }

    private void viewAllBookings() {
        clearScreen();
        System.out.println("\n=== All Bookings ===");
        bookingManager.viewAllBookings();
        waitForInput();
    }

    public void loadUsersFromFile() {
        users.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String userId = parts[0];
                    String name = parts[1];
                    String role = parts[2];
                    String password = parts[3];
                    User user = role.equalsIgnoreCase("Admin") ? new Admin(userId, name, password) : new Customer(userId, name, password);
                    users.put(userId, user);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }

    public void saveUsersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                String role = (user instanceof Admin) ? "Admin" : "Customer";
                bw.write(user.getUserId() + "," + user.getName() + "," + role + "," + user.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users file: " + e.getMessage());
        }
    }

    public void loadBookingsFromFile() {
        bookingManager.clearBookings();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String bookingId = parts[0];
                    String customerId = parts[1];
                    String branchName = parts[2];
                    String roomId = parts[3];
                    LocalDate date = LocalDate.parse(parts[4]);
                    LocalTime time = LocalTime.parse(parts[5]);
                    User user = users.get(customerId);
                    if (user instanceof Customer) {
                        Branch branch = null;
                        for (Branch b : branches) {
                            if (b.getName().equals(branchName)) {
                                branch = b;
                                break;
                            }
                        }
                        if (branch != null) {
                            SmartRoom room = branch.getRoomById(roomId);
                            if (room != null) {
                                bookingManager.addBookingFromFile(bookingId, (Customer) user, branch, room, date, time);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Error reading bookings file: " + e.getMessage());
        }
    }

    public void saveBookingsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking booking : bookingManager.getAllBookings()) {
                bw.write(booking.getBookingId() + "," + booking.getCustomer().getUserId() + "," +
                        booking.getBranch().getName() + "," + booking.getRoom().getRoomId() + "," +
                        booking.getDate() + "," + booking.getTime());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings file: " + e.getMessage());
        }
    }
} 