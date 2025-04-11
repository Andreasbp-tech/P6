import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertValues {
    public static void main(String[] args) {
        try {
            // Use DatabaseConnection to get the connection
            Connection conn = DatabaseConnection.getConnection();
            
            // SQL query to insert values into the table
            String sql = "INSERT INTO Users (ID, Forname, Surname, Age) VALUES (?, ?, ?, ?)";
            
            // Create a prepared statement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Set values
            pstmt.setInt(1, 2); // ID
            pstmt.setString(2, "Anne"); // Forname
            pstmt.setString(3, "Storeør"); // Surname
            pstmt.setInt(4, 16); // Age
            
            // Execute the query
            pstmt.executeUpdate();
            
            System.out.println("Values inserted successfully.");
            
            // Close the connection
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
