import java.sql.*;
import java.util.Scanner;

class StudentManagement {

    public static void enrollStudent(Scanner scanner) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.print("Enter Student Name: ");
            scanner.nextLine();
            String name = scanner.nextLine();
    
            System.out.print("Enter the DOB of Student (YYYY-MM-DD): ");
            String dob = scanner.nextLine();
    
            System.out.print("Enter Grade Level: ");
            int gradeLevel = scanner.nextInt();
            scanner.nextLine();  
    
            System.out.print("Enter Parent Contact: ");
            String parentContact = scanner.nextLine();
    
            System.out.print("Enter Parent Name: ");
            String parentName = scanner.nextLine();
    
            System.out.print("Enter Your Permanent Address: ");
            String address = scanner.nextLine();
    
            System.out.print("Enter Parent Annual Income: ");
            int income = scanner.nextInt();
            scanner.nextLine();
    
            System.out.print("Enter the DOB of Parent (YYYY-MM-DD): ");
            String parentDOB = scanner.nextLine();
    
            System.out.print("Parent Occupation: ");
            String occupation = scanner.nextLine();
    
            System.out.print("Enter your community: ");
            String community = scanner.nextLine();
    
            // Validate input
            if (name.isEmpty() || gradeLevel <= 0 || parentContact.isEmpty()) {
                throw new IllegalArgumentException("Invalid input: Name, grade, and contact must be valid.");
            }
    
            String query = "INSERT INTO Students (name, student_dob, gradeLevel, parentContact, parent_name, address, income, parent_dob, occupation, community) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, dob);
            statement.setInt(3, gradeLevel);
            statement.setString(4, parentContact);
            statement.setString(5, parentName);
            statement.setString(6, address);
            statement.setInt(7, income);
            statement.setString(8, parentDOB);
            statement.setString(9, occupation);
            statement.setString(10, community);
            
            statement.executeUpdate();
    
            // Retrieve generated student_id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int studentId = generatedKeys.getInt(1);
                System.out.println("Student enrolled successfully. Student ID: " + studentId);
            } else {
                System.out.println("Enrollment failed, no ID obtained.");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }
    

    
    public static void viewStudents(int id) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM Students WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);  // Set the id parameter in the query
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                 System.out.println("Name: " + resultSet.getString("name"));
                 System.out.println("DOB: " + resultSet.getString("student_dob"));
                 System.out.println("Grade Level: " + resultSet.getInt("gradeLevel"));
                 System.out.println("Community: "+resultSet.getString("community"));
                 System.out.println("Parent Contact: " + resultSet.getString("parentContact"));
                 System.out.println("Parent Name: " + resultSet.getString("parent_name"));
                 System.out.println("Address: " + resultSet.getString("address"));
                 System.out.println("Income: " + resultSet.getInt("income"));
                 System.out.println("Parent DOB: " + resultSet.getString("parent_dob"));
                 System.out.println("Occupation: " + resultSet.getString("occupation"));
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
    
}
