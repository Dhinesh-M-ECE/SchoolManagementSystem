import java.sql.*;
import java.util.Scanner;

class AttendanceTracking {

    public void markAttendance(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.println("Enter Student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.println("Is the student present? (true/false): ");
            boolean present = scanner.nextBoolean();

            String query = "INSERT INTO Attendance (studentId, date, present) VALUES (?, CURDATE(), ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            statement.setBoolean(2, present);
            statement.executeUpdate();

            System.out.println("Attendance marked successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
