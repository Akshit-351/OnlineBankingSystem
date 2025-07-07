import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/banking";
            String user = "root"; // Your MySQL username
            String password = "Admin@123"; // Your MySQL password

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to database");
            e.printStackTrace();
            return null;
        }
    }
}
