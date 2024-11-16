import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/schooldb";  // Change to the correct database
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = "12345";  // Replace with your MySQL password

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
