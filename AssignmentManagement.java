import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.DriverManager;

class AssignmentManagement {
    
    public void createAssignment(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            if (connection == null) {
                System.err.println("Failed to establish a database connection.");
                return;
            }

            System.out.println("Enter Assignment Title: ");
            scanner.nextLine();
            String title = scanner.nextLine();

            System.out.println("Enter Description: ");
            String description = scanner.nextLine();

            System.out.println("Enter Due Date (YYYY-MM-DD): ");
            String dueDate = scanner.nextLine();

            System.out.println("Enter Student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String query = "INSERT INTO Assignments (title, description, due_date, student_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, dueDate);
            statement.setInt(4, studentId);
            statement.executeUpdate();

            System.out.println("Assignment created successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

    }
}
class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/schooldb"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASSWORD = "12345"; // Database password

    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }
}
