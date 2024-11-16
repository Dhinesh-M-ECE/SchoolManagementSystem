import java.sql.*;
import java.util.Scanner;

class TransportManagement {

    public void assignBus(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.println("Enter Student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.println("Enter Bus Number: ");
            String busNumber = scanner.nextLine();

            System.out.println("Enter Route: ");
            String route = scanner.nextLine();

            if (studentId <= 0 || busNumber.isEmpty() || route.isEmpty()) {
                throw new IllegalArgumentException("Invalid inputs. Make sure Bus Number, Route, and Student ID are valid.");
            }

            String query = "INSERT INTO Transport (studentId, busNumber, route) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            statement.setString(2, busNumber);
            statement.setString(3, route);
            statement.executeUpdate();

            System.out.println("Bus assigned successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }

    public void viewBusAssignment(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.println("Enter Student ID to view bus assignment: ");
            int studentId = scanner.nextInt();

            if (studentId <= 0) {
                throw new IllegalArgumentException("Invalid Student ID.");
            }

            String query = "SELECT * FROM Transport WHERE studentId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No bus assigned to this student.");
            } else {
                System.out.println("Bus Number: " + resultSet.getString("busNumber") + 
                                   ", Route: " + resultSet.getString("route"));
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }
}
