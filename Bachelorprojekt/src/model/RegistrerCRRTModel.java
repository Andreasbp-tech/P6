package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import utilities.DatabaseConnection;

public class RegistrerCRRTModel {
    private static final Logger logger = Logger.getLogger(RegistrerCRRTModel.class.getName());
    private NormalvaerdierModel normalvaerdierModel;

    public RegistrerCRRTModel(NormalvaerdierModel normalvaerdierModel) {
        this.normalvaerdierModel = normalvaerdierModel;
    }

    public void saveToDatabase(String cprNr, String tidspunkt, String dialysatflow, String blodflow, String vaesketraek,
            String indloebstryk, String returtryk, String praefiltertryk) {
        String query = "INSERT INTO CRRT (CPR_nr, tidspunkt, dialysatflow, blodflow, væsketræk, indløbstryk, returtryk, præfiltertryk) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cprNr);
            pstmt.setString(2, tidspunkt);
            pstmt.setDouble(3, Double.parseDouble(dialysatflow));
            pstmt.setDouble(4, Double.parseDouble(blodflow));
            pstmt.setDouble(5, Double.parseDouble(vaesketraek));
            pstmt.setDouble(6, Double.parseDouble(indloebstryk));
            pstmt.setDouble(7, Double.parseDouble(returtryk));
            pstmt.setDouble(8, Double.parseDouble(praefiltertryk));

            pstmt.executeUpdate();
            logger.info("Values saved to the database for CPR: " + cprNr);
        } catch (SQLException e) {
            logger.severe("Error saving values to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
