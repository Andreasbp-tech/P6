package model;

import utilities.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TabelCitratmetabolismeModel {
    private List<String> timestamps;
    private Object[][] data;
    private static final Logger logger = Logger.getLogger(TabelCitratmetabolismeModel.class.getName());

    public void fetchData(String cprNr) {
        timestamps = new ArrayList<>();
        List<Object[]> dataList = new ArrayList<>();
        String[] rowNames = { "Calciumdosis", "Citratdosis" };

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM Citratmetabolisme WHERE CPR_nr = '" + cprNr
                    + "' ORDER BY tidspunkt DESC LIMIT 24";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String tidspunkt = rs.getString("tidspunkt");
                String timePart = tidspunkt.substring(11, 16);
                timestamps.add(timePart);
            }

            java.util.Collections.reverse(timestamps);
            rs.beforeFirst();

            data = new Object[rowNames.length][timestamps.size() + 1];
            for (int i = 0; i < rowNames.length; i++) {
                data[i][0] = rowNames[i];
            }

            int colIndex = timestamps.size();
            while (rs.next()) {
                data[0][colIndex] = rs.getDouble("calciumdosis");
                data[1][colIndex] = rs.getDouble("citratdosis");
                colIndex--;
            }

            rs.close();
            stmt.close();
            conn.close();
            logger.info("Citratmetabolisme data fetched for CPR: " + cprNr);
        } catch (Exception e) {
            logger.severe("Fejl ved hentning af citratmetabolisme-data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String> getTimestamps() {
        return timestamps;
    }

    public Object[][] getData() {
        return data;
    }
}
