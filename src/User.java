public class User {
    private int id;
    private String username;
    private String password;
    private double balance;

    // Constructors
    public User(int id, String username, String password, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }

    // Setters (if needed)
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
