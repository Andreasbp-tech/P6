package model;

import java.sql.*;

import utilities.DatabaseConnection;

public class MedarbejderModel {
    private static String loggedInMedarbejderID;

    public boolean isValidMedarbejderID(String medarbejderID) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT COUNT(*) FROM Medarbejdere WHERE MedarbejderID = ?")) {
            stmt.setString(1, medarbejderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void logUserLogin(String medarbejderID) {
        loggedInMedarbejderID = medarbejderID; // Set the logged-in user ID
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT Rolle, Efternavn FROM Medarbejdere WHERE MedarbejderID = ?")) {
            stmt.setString(1, medarbejderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String rolle = rs.getString("Rolle");
                String efternavn = rs.getString("Efternavn");

                try (PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO LoginHistorie (MedarbejderID, Rolle, Efternavn, LoginTime) VALUES (?, ?, ?, ?)")) {
                    insertStmt.setString(1, medarbejderID);
                    insertStmt.setString(2, rolle);
                    insertStmt.setString(3, efternavn);
                    insertStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static String getLoggedInMedarbejderID() {
        return loggedInMedarbejderID;
    }
}
