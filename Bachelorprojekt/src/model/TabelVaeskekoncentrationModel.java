package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import utilities.DatabaseConnection;

public class TabelVaeskekoncentrationModel {
    private List<String> timestamps;
    private List<String> dates;
    private Object[][] data;
    private static final Logger logger = Logger.getLogger(TabelVaeskekoncentrationModel.class.getName());

    public void fetchData(String cprNr) {
        timestamps = new ArrayList<>();
        dates = new ArrayList<>();
        String[] rowNames = { "Calciumdosis", "Citratdosis", "Heparin" };

        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM Citratmetabolisme WHERE CPR_nr = ? ORDER BY tidspunkt DESC LIMIT 18";
            PreparedStatement preparedStatement = conn.prepareStatement(
                    query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, cprNr);

            ResultSet rs = preparedStatement.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                String tidspunkt = rs.getString("tidspunkt");
                LocalDateTime datetime = LocalDateTime.parse(tidspunkt, formatter);
                String timePart = datetime.format(DateTimeFormatter.ofPattern("HH:mm"));
                String datePart = datetime.format(DateTimeFormatter.ofPattern("dd-MM-yy"));

                timestamps.add(timePart);
                dates.add(datePart);
            }

            java.util.Collections.reverse(timestamps);
            java.util.Collections.reverse(dates);

            rs.beforeFirst();
            data = new Object[rowNames.length][timestamps.size() + 1];

            for (int i = 0; i < rowNames.length; i++) {
                data[i][0] = rowNames[i];
            }

            int colIndex = timestamps.size();
            while (rs.next()) {
                data[0][colIndex] = rs.getDouble("calciumdosis");
                data[1][colIndex] = rs.getDouble("citratdosis");
                data[2][colIndex] = rs.getDouble("heparin");
                colIndex--;
            }

            rs.close();
            preparedStatement.close();
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

    public List<String> getDates() {
        return dates;
    }

    public Object[][] getData() {
        return data;
    }
}
