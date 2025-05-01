package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import utilities.DatabaseConnection;

public class NormalvaerdierModel {
    private Map<String, double[]> ranges;

    public NormalvaerdierModel() {
        ranges = new HashMap<>();
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        String query = "SELECT parameter_name, min_value, max_value FROM Normalværdier";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String parameter = rs.getString("parameter_name");
                double min = rs.getDouble("min_value");
                double max = rs.getDouble("max_value");
                ranges.put(parameter, new double[] { min, max });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kunne ikke hente normalområder fra databasen.");
        }
    }

    public double[] getRange(String parameterName) {
        return ranges.get(parameterName);
    }

    public boolean isValueNormal(String parameterName, double value) {
        parameterName = parameterName.trim(); // sikkerhed

        double[] range = ranges.get(parameterName);
        if (range == null) {
            throw new IllegalArgumentException("Ukendt parameter: " + parameterName);
        }

        // Tjek om kaldet kommer fra TabelCitratmetabolismeController
        boolean calledFromCitrat = false;
        for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
            if (elem.getClassName().contains("TabelCitratmetabolismeController")) {
                calledFromCitrat = true;
                break;
            }
        }

        // Hvis kaldt fra Citrat-controller: accepter også 0 som normalt
        if (calledFromCitrat) {
            return value == 0 || (value >= range[0] && value <= range[1]);
        } else {
            return value >= range[0] && value <= range[1];
        }
    }

    // Optional: allow updating normal ranges dynamically
    public void updateRange(String parameterName, double min, double max) {
        ranges.put(parameterName, new double[] { min, max });
        saveRangeToDatabase(parameterName, min, max);
    }

    private void saveRangeToDatabase(String parameterName, double min, double max) {
        String query = "UPDATE Normalværdier SET min_value = ?, max_value = ? WHERE parameter_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, min);
            pstmt.setDouble(2, max);
            pstmt.setString(3, parameterName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kunne ikke opdatere normalområde i databasen.");
        }
    }
}
