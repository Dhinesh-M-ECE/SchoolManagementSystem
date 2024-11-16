import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TimetableEntry {
    private String subject;
    private String day;
    private String time;

    public TimetableEntry(String subject, String day, String time) {
        this.subject = subject;
        this.day = day;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Subject: " + subject + ", Day: " + day + ", Time: " + time;
    }
}

class TimetableManagement {
    @SuppressWarnings("unused")
    private List<TimetableEntry> timetableEntries;
    private Connection connection;

    public TimetableManagement() {
        this.timetableEntries = new ArrayList<>();
        connectToDatabase();
    }

    // Method to establish a database connection
    private void connectToDatabase() {
        String dbUrl = "jdbc:mysql://localhost:3306/schooldb"; // Update with your DB URL
        String user = "root"; // Update with your DB username
        String password = "12345"; // Update with your DB password

        try {
            connection = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database.");
            e.printStackTrace();
        }
    }

    // Method to add a timetable entry to the database
    public void addTimetableEntry(Scanner scanner) {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter day: ");
        String day = scanner.nextLine();
        System.out.print("Enter time: ");
        String time = scanner.nextLine();

        // Save entry to the database
        String query = "INSERT INTO Timetable (subject, day, time) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subject);
            stmt.setString(2, day);
            stmt.setString(3, time);
            stmt.executeUpdate();
            System.out.println("Timetable entry added to the database successfully.");
        } catch (SQLException e) {
            System.out.println("Error saving timetable entry to the database.");
            e.printStackTrace();
        }
    }

    // Method to retrieve and view timetable entries from the database
    public void viewTimetable() {
        String query = "SELECT * FROM Timetable";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Timetable Entries from Database:");
            while (rs.next()) {
                String subject = rs.getString("subject");
                String day = rs.getString("day");
                String time = rs.getString("time");
                TimetableEntry entry = new TimetableEntry(subject, day, time);
                System.out.println(entry);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving timetable entries from the database.");
            e.printStackTrace();
        }
    }

    // Method to close database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing the database connection.");
            e.printStackTrace();
        }
    }
}
