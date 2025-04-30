package model;

import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabelBlodproveModel {
    private List<String> timestamps;
    private List<String> dates;
    private Object[][] data;

    public void fetchData(String cprNr) {
        timestamps = new ArrayList<>();
        dates = new ArrayList<>();
        List<Object[]> dataList = new ArrayList<>();
        String[] rowNames = { "Total Ca", "Kreatinin", "Carbamid", "Kalium", "Infektionstal", "Calcium Ratio" };

        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM Blodprøve WHERE CPR_nr = '" + cprNr + "' ORDER BY tidspunkt DESC LIMIT 18";
            ResultSet rs = stmt.executeQuery(query);

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");

            while (rs.next()) {
                String tidspunkt = rs.getString("tidspunkt");
                LocalDateTime dt = LocalDateTime.parse(tidspunkt, inputFormatter);
                timestamps.add(dt.format(timeFormatter));
                dates.add(dt.format(dateFormatter));
            }

            Collections.reverse(timestamps);
            Collections.reverse(dates);

            rs.beforeFirst();
            data = new Object[rowNames.length][timestamps.size() + 1];
            for (int i = 0; i < rowNames.length; i++) {
                data[i][0] = rowNames[i];
            }

            int colIndex = timestamps.size();
            while (rs.next()) {
                data[0][colIndex] = rs.getDouble("totalCa");
                data[1][colIndex] = rs.getDouble("kreatinin");
                data[2][colIndex] = rs.getDouble("carbamid");
                data[3][colIndex] = rs.getDouble("kalium");
                data[4][colIndex] = rs.getDouble("infektionstal");
                data[5][colIndex] = rs.getDouble("calciumratio");
                colIndex--;
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
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
