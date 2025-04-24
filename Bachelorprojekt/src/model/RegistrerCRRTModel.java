package model;

import utilities.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrerCRRTModel {
    public void saveToDatabase(String dialysatflow, String blodflow, String vaesketraek, String indloebstryk,
            String returtryk, String praefiltertryk, String heparin) {
        String query = "INSERT INTO CRRT (dialysatflow, blodflow, væsketræk, indløbstryk, returtryk, præfiltertryk, heparin) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dialysatflow);
            pstmt.setString(2, blodflow);
            pstmt.setString(3, vaesketraek);
            pstmt.setString(4, indloebstryk);
            pstmt.setString(5, returtryk);
            pstmt.setString(6, praefiltertryk);
            pstmt.setString(7, heparin);

            pstmt.executeUpdate();
            System.out.println("Values saved to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
