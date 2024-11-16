import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/schooldb";
    private static final String USER = "root";  // Your DB username
    private static final String PASSWORD = "12345";  // Your DB password

    private Connection connection;
    private FeeManagement feeManagement;
    private LibraryManagement libraryManagement;
    @SuppressWarnings("unused")
    private StudentManagement studentManagement;
    private TransportManagement transportManagement;
    private TimetableManagement timetableManagement;
    private AssignmentManagement assignmentManagement;

    // Constructor to establish a database connection
    public Main() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");

            // Initialize management classes
            feeManagement = new FeeManagement();
            libraryManagement = new LibraryManagement();
            studentManagement = new StudentManagement();
            transportManagement = new TransportManagement();
            timetableManagement = new TimetableManagement();
            assignmentManagement = new AssignmentManagement();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Main sms = new Main();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nSchool Management System:");
            System.out.println("1. Register Student");
            System.out.println("2. Record Attendance");
            System.out.println("3. Pay Fee");
            System.out.println("4. View Student Info");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Fee Status");
            System.out.println("8. Assign Bus");
            System.out.println("9. View Bus Assignment");
            System.out.println("10. Add Timetable Entry");
            System.out.println("11. View Timetable");
            System.out.println("12. Create Assignment");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    StudentManagement.enrollStudent(scanner);
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Is Present (true/false): ");
                    boolean isPresent = scanner.nextBoolean();
                    sms.recordAttendance(studentId, isPresent);
                    break;
                case 3:
                    sms.feeManagement.payFee(scanner);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextInt();
                    StudentManagement.viewStudents(studentId);
                    //sms.viewStudentInfo(studentId);
                    break;
                case 5:
                    sms.libraryManagement.borrowBook(scanner);
                    break;
                case 6:
                    sms.libraryManagement.returnBook(scanner);
                    break;
                case 7:
                    sms.feeManagement.viewFeeStatus(scanner);
                    break;
                case 8:
                    sms.transportManagement.assignBus(scanner);
                    break;
                case 9:
                    sms.transportManagement.viewBusAssignment(scanner);
                    break;
                case 10:
                    sms.timetableManagement.addTimetableEntry(scanner);
                    break;
                case 11:
                    sms.timetableManagement.viewTimetable();
                    break;
                case 12:
                    sms.assignmentManagement.createAssignment(scanner);
                    break;
                case 13:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 13);
        scanner.close();
    }

    // Method to record attendance (this can be implemented as per your requirements)
    public void recordAttendance(int studentId, boolean isPresent) {
        String query = "INSERT INTO Attendance (studentId, date, present) VALUES (?, CURRENT_DATE, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setBoolean(2, isPresent);
            stmt.executeUpdate();
            System.out.println("Attendance recorded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view student information (this is used in case 4)
    public void viewStudentInfo(int studentId) {
        String query = "SELECT * FROM Students WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Grade Level: " + rs.getInt("gradeLevel"));
                System.out.println("Parent Contact: " + rs.getString("parentContact"));
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
