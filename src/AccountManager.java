import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {

    public static void register(String username, String password) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("✅ User registered successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Registration failed (username may already exist).");
        }
    }

    public static User login(String username, String password) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDouble("balance")
                );
            } else {
                System.out.println("❌ Invalid credentials.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Login error.");
            e.printStackTrace();
            return null;
        }
    }
}
