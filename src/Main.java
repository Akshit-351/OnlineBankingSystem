import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("💰 Welcome to the Online Banking System 💰");

        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        scanner.nextLine(); // consume newline

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (choice == 1) {
            AccountManager.register(username, password);
        } else if (choice == 2) {
            User user = AccountManager.login(username, password);
            if (user != null) {
                System.out.println("✅ Welcome " + user.getUsername());
                System.out.println("Your balance: ₹" + user.getBalance());

                System.out.println("\nChoose an action:");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Transfer");
                System.out.println("4. View Transaction History");
                int action = scanner.nextInt();


                if (action == 1 || action == 2) {
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();

                    if (action == 1) {
                        TransactionManager.deposit(user, amount);
                    } else {
                        TransactionManager.withdraw(user, amount);
                    }

                } else if (action == 3) {
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter recipient's username: ");
                    String recipient = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double amount = scanner.nextDouble();

                    TransactionManager.transfer(user, recipient, amount);

                } else if (action == 4) {
                    TransactionManager.viewTransactionHistory(user);
                }
            }
        }
    }
}
