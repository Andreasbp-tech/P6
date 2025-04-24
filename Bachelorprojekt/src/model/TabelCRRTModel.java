package model;

import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TabelCRRTModel {
    private List<String> timestamps;
    private Object[][] data;

    public void fetchData(String cprNr) {
        timestamps = new ArrayList<>();
        List<Object[]> dataList = new ArrayList<>();
        String[] rowNames = { "Dialysatflow", "Blodflow", "Væsketræk", "Indløbstryk", "Returtryk", "Præfiltertryk",
                "Heparin" };

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM CRRT WHERE CPR_nr = '" + cprNr + "' ORDER BY tidspunkt DESC LIMIT 24";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String tidspunkt = resultSet.getString("tidspunkt");
                String timePart = tidspunkt.substring(11, 16);
                timestamps.add(timePart);
            }

            java.util.Collections.reverse(timestamps);

            resultSet.beforeFirst();
            data = new Object[rowNames.length][timestamps.size() + 1];

            for (int i = 0; i < rowNames.length; i++) {
                data[i][0] = rowNames[i];
            }

            int colIndex = timestamps.size();
            while (resultSet.next()) {
                data[0][colIndex] = resultSet.getDouble("dialysatflow");
                data[1][colIndex] = resultSet.getDouble("blodflow");
                data[2][colIndex] = resultSet.getDouble("væsketræk");
                data[3][colIndex] = resultSet.getDouble("indløbstryk");
                data[4][colIndex] = resultSet.getDouble("returtryk");
                data[5][colIndex] = resultSet.getDouble("præfiltertryk");
                data[6][colIndex] = resultSet.getDouble("heparin");
                colIndex--;
            }

            resultSet.close();
            statement.close();
            // connection.close();
        } catch (Exception e) {
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
