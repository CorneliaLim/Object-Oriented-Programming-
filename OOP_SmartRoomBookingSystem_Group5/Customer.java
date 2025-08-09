public class Customer extends User {
    public Customer(String userId, String name, String password) {
        super(userId, name, password);
    }

    public void displayRole() {
        System.out.println("Role: Customer");
    }
} 