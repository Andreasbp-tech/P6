package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import utilities.DatabaseConnection;

public class RegistrerVaeskekoncentrationModel {
    private static final Logger logger = Logger.getLogger(RegistrerVaeskekoncentrationModel.class.getName());

    public void saveToDatabase(String cprNr, String calciumdosis, String citratdosis, String heparin) {

        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "INSERT INTO Væskekoncentration (CPR_nr, tidspunkt, calciumdosis, citratdosis, heparin) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            String tidspunkt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            statement.setString(1, cprNr);
            statement.setString(2, tidspunkt);
            statement.setDouble(3, Double.parseDouble(calciumdosis));
            statement.setDouble(4, Double.parseDouble(citratdosis));
            statement.setDouble(5, Double.parseDouble(heparin));

            statement.executeUpdate();
            statement.close();
            conn.close();

            logger.info("Citratdata gemt for CPR: " + cprNr);
        } catch (Exception e) {
            logger.severe("Fejl ved gemning: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public double[] getLatestValues(String cprNr) {
        double[] latestValues = null;

        String query = "SELECT calciumdosis, citratdosis FROM Væskekoncentration WHERE CPR_nr = ? ORDER BY tidspunkt DESC LIMIT 1";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cprNr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double calcium = rs.getDouble("calciumdosis");
                double citrat = rs.getDouble("citratdosis");
                latestValues = new double[] { calcium, citrat };
            }

            rs.close();
        } catch (Exception e) {
            logger.severe("Fejl ved hentning af seneste værdier: " + e.getMessage());
            e.printStackTrace();
        }

        return latestValues;
    }

}
