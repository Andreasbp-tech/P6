package model;

import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TabelBlodproveModel {
    private List<String> tidspunkter; // Tidspunkter til kolonner
    private List<String> dates; // Datoer til rækkenavne
    private Object[][] data; // Data-array

    public TabelBlodproveModel() {
        tidspunkter = new ArrayList<>();
        dates = new ArrayList<>();
    }

    public void fetchData(String cprNr) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Change ResultSet type to TYPE_SCROLL_INSENSITIVE for backwards and forwards
            // navigation
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT tidspunkt, totalCa, kreatinin, carbamid, kalium, infektionstal, calciumratio " +
                    "FROM Blodprøve WHERE CPR_nr = '" + cprNr + "' ORDER BY tidspunkt DESC LIMIT 18";
            ResultSet rs = stmt.executeQuery(query);

            // Clear previous data
            tidspunkter.clear();
            dates.clear();

            // Add column names for the row (blodprøveparametre)
            dates.add("Total Ca");
            dates.add("Kreatinin");
            dates.add("Carbamid");
            dates.add("Kalium");
            dates.add("Infektionstal");
            dates.add("Calcium Ratio");

            // Add timestamps as column names
            while (rs.next()) {
                String timestamp = rs.getString("tidspunkt");
                tidspunkter.add(timestamp);
            }

            // Initialize data array based on the size of timestamps
            data = new Object[dates.size()][tidspunkter.size()];

            // Move cursor back to the beginning of the result set and fill data array
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                // This assumes the columns in the result set match the order in the `dates`
                // list
                data[0][i] = rs.getDouble("totalCa");
                data[1][i] = rs.getDouble("kreatinin");
                data[2][i] = rs.getDouble("carbamid");
                data[3][i] = rs.getDouble("kalium");
                data[4][i] = rs.getDouble("infektionstal");
                data[5][i] = rs.getDouble("calciumratio");
                i++;
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getTidspunkter() {
        return tidspunkter;
    }

    public List<String> getDates() {
        return dates;
    }

    public Object[][] getData() {
        return data;
    }
}
