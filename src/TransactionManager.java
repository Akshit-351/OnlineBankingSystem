import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionManager {

    public static void deposit(User user, double amount) {
        try (Connection conn = Database.getConnection()) {
            String updateBalance = "UPDATE users SET balance = balance + ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateBalance);
            stmt.setDouble(1, amount);
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();

            recordTransaction(user.getId(), "DEPOSIT", amount);
            System.out.println("✅ ₹" + amount + " deposited successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Deposit failed.");
            e.printStackTrace();
        }
    }

    public static void withdraw(User user, double amount) {
        if (user.getBalance() < amount) {
            System.out.println("❌ Insufficient balance.");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            String updateBalance = "UPDATE users SET balance = balance - ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateBalance);
            stmt.setDouble(1, amount);
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();

            recordTransaction(user.getId(), "WITHDRAW", amount);
            System.out.println("✅ ₹" + amount + " withdrawn successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Withdrawal failed.");
            e.printStackTrace();
        }
    }
    public static void transfer(User sender, String recipientUsername, double amount) {
        System.out.println("✅ Transfer started...");

        if (sender.getUsername().equals(recipientUsername)) {
            System.out.println("❌ Cannot transfer to yourself.");
            return;
        }

        if (sender.getBalance() < amount) {
            System.out.println("❌ Insufficient balance.");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);
            System.out.println("✅ Database connection opened...");

            String findRecipientSql = "SELECT * FROM users WHERE username=?";
            PreparedStatement findStmt = conn.prepareStatement(findRecipientSql);
            findStmt.setString(1, recipientUsername);
            ResultSet rs = findStmt.executeQuery();
            System.out.println("✅ Recipient lookup executed...");

            if (!rs.next()) {
                System.out.println("❌ Recipient not found.");
                conn.rollback();
                return;
            }

            int recipientId = rs.getInt("id");
            System.out.println("✅ Recipient found with ID: " + recipientId);

            String deductSql = "UPDATE users SET balance = balance - ? WHERE id = ?";
            PreparedStatement deductStmt = conn.prepareStatement(deductSql);
            deductStmt.setDouble(1, amount);
            deductStmt.setInt(2, sender.getId());
            deductStmt.executeUpdate();
            System.out.println("✅ Deducted from sender...");

            String addSql = "UPDATE users SET balance = balance + ? WHERE id = ?";
            PreparedStatement addStmt = conn.prepareStatement(addSql);
            addStmt.setDouble(1, amount);
            addStmt.setInt(2, recipientId);
            addStmt.executeUpdate();
            System.out.println("✅ Added to recipient...");

            recordTransaction(sender.getId(), "TRANSFER_SENT", amount);
            recordTransaction(recipientId, "TRANSFER_RECEIVED", amount);
            System.out.println("✅ Transactions recorded...");

            conn.commit();
            System.out.println("✅ ₹" + amount + " transferred to " + recipientUsername + " successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Transfer failed.");
            e.printStackTrace();
        }
    }


    private static void recordTransaction(int userId, String type, double amount) {
        try (Connection conn = Database.getConnection()) {
            String insertTransaction = "INSERT INTO transactions (user_id, type, amount) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertTransaction);
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to record transaction.");
            e.printStackTrace();
        }
    }
    public static void viewTransactionHistory(User user) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getId());

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n📄 Transaction History:");
            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String date = rs.getString("date");

                System.out.println("[" + date + "] " + type + " ₹" + amount);
            }

        } catch (SQLException e) {
            System.out.println("❌ Could not fetch transaction history.");
            e.printStackTrace();
        }
    }

}

