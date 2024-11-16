import java.sql.*;
import java.util.Scanner;

class FeeManagement {

    public void payFee(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.println("Enter Student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.println("Enter Amount Paid: ");
            double amountPaid = scanner.nextDouble();

            if (studentId <= 0 || amountPaid <= 0) {
                throw new IllegalArgumentException("Student ID and amount paid must be positive numbers.");
            }

            String query = "INSERT INTO Fees (studentId, amountPaid, paymentDate) VALUES (?, ?, CURDATE())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            statement.setDouble(2, amountPaid);
            statement.executeUpdate();

            System.out.println("Fee paid successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }

    public void viewFeeStatus(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.println("Enter Student ID to view fees: ");
            int studentId = scanner.nextInt();

            if (studentId <= 0) {
                throw new IllegalArgumentException("Invalid Student ID.");
            }

            String query = "SELECT * FROM Fees WHERE studentId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No fees paid by this student.");
            } else {
                do {
                    System.out.println("Amount Paid: " + resultSet.getDouble("amountPaid") +
                            ", Date: " + resultSet.getDate("paymentDate"));
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }
}
