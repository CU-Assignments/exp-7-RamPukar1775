import java.sql.*;

public class MySQLconn {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String password = "your_password";

        // SQL query
        String query = "SELECT EmpID, Name, Salary FROM Employee";

        // Establishing a connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            // Displaying results
            System.out.println("EmpID | Name | Salary");
            System.out.println("----------------------");
            while (rs.next()) {
                int empID = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.println(empID + " | " + name + " | " + salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
