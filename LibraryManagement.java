import java.sql.*;
import java.util.Scanner;

class LibraryManagement {

    public void borrowBook(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.println("Enter Student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.println("Enter Book ID: ");
            String bookId = scanner.nextLine();

            if (bookId.isEmpty() || studentId <= 0) {
                throw new IllegalArgumentException("Invalid Student ID or Book ID.");
            }

            String checkQuery = "SELECT * FROM Library WHERE studentId = ? AND bookId = ? AND returned = FALSE";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, studentId);
            checkStatement.setString(2, bookId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("This book is already borrowed and not returned.");
                return;
            }

            String query = "INSERT INTO Library (studentId, bookId, borrowedDate) VALUES (?, ?, CURDATE())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            statement.setString(2, bookId);
            statement.executeUpdate();

            System.out.println("Book borrowed successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }

    public void returnBook(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.println("Enter Student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();  

            System.out.println("Enter Book ID: ");
            String bookId = scanner.nextLine();

            if (bookId.isEmpty()) {
                throw new IllegalArgumentException("Book ID cannot be empty.");
            }

            String query = "UPDATE Library SET returned = TRUE WHERE studentId = ? AND bookId = ? AND returned = FALSE";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            statement.setString(2, bookId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("No such borrowed book found.");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }
}
