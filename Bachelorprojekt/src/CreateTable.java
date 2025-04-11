import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        try {
            // Use DatabaseConnection to get the connection
            Connection conn = DatabaseConnection.getConnection();
            
            // Create a statement
            Statement stmt = conn.createStatement();
            
            // SQL query to create a new table
            String sql = "CREATE TABLE Users (" +
                         "ID INT PRIMARY KEY," +
                         "Forname VARCHAR(255)," +
                         "Surname VARCHAR(255)," +
                         "Age INT)";
            
            // Execute the query
            stmt.executeUpdate(sql);
            
            System.out.println("Table created successfully.");
            
            // Close the connection
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
