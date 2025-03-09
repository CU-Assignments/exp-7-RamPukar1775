import java.sql.*;
import java.util.Scanner;

// Model class
class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getMarks() {
        return marks;
    }
}

// Controller class
class StudentController {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Students (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            pstmt.setDouble(4, student.getMarks());
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        }
    }

    public static void viewStudents() throws SQLException {
        String query = "SELECT * FROM Students";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("StudentID | Name | Department | Marks");
            System.out.println("--------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("StudentID") + " | " + rs.getString("Name") + " | "
                        + rs.getString("Department") + " | " + rs.getDouble("Marks"));
            }
        }
    }

    public static void updateStudent(int id, double marks) throws SQLException {
        String query = "UPDATE Students SET Marks = ? WHERE StudentID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, marks);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully.");
        }
    }

    public static void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM Students WHERE StudentID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Student deleted successfully.");
        }
    }
}

// View class
public class exp7c {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                System.out.println("\nMenu:");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student Marks");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = scanner.nextInt();
                        System.out.print("Enter Name: ");
                        String name = scanner.next();
                        System.out.print("Enter Department: ");
                        String department = scanner.next();
                        System.out.print("Enter Marks: ");
                        double marks = scanner.nextDouble();
                        Student student = new Student(id, name, department, marks);
                        StudentController.addStudent(student);
                        break;
                    case 2:
                        StudentController.viewStudents();
                        break;
                    case 3:
                        System.out.print("Enter Student ID to update: ");
                        int updateID = scanner.nextInt();
                        System.out.print("Enter new Marks: ");
                        double newMarks = scanner.nextDouble();
                        StudentController.updateStudent(updateID, newMarks);
                        break;
                    case 4:
                        System.out.print("Enter Student ID to delete: ");
                        int deleteID = scanner.nextInt();
                        StudentController.deleteStudent(deleteID);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
