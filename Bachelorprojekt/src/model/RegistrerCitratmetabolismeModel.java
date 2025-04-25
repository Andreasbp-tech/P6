package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import utilities.DatabaseConnection;

public class RegistrerCitratmetabolismeModel {
    private static final Logger logger = Logger.getLogger(RegistrerCitratmetabolismeModel.class.getName());

    public void saveToDatabase(String cprNr, String calciumdosis, String citratdosis) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "INSERT INTO Citratmetabolisme (CPR_nr, tidspunkt, calciumdosis, citratdosis) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            String tidspunkt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            statement.setString(1, cprNr);
            statement.setString(2, tidspunkt);
            statement.setDouble(3, Double.parseDouble(calciumdosis));
            statement.setDouble(4, Double.parseDouble(citratdosis));

            statement.executeUpdate();
            statement.close();
            conn.close();

            logger.info("Citratdata gemt for CPR: " + cprNr);
        } catch (Exception e) {
            logger.severe("Fejl ved gemning: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
