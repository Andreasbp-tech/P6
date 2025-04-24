package model;

import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TabelAGasModel {
    private List<String> timestamps;
    private Object[][] data;

    public void fetchData(String cprNr) {
        timestamps = new ArrayList<>();
        List<Object[]> dataList = new ArrayList<>();
        String[] rowNames = { "pH", "BE", "HCO3", "SystemiskCa", "PostfilterCa" };

        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM A_gas WHERE CPR_nr = '" + cprNr + "' ORDER BY tidspunkt DESC LIMIT 24";
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
                data[0][colIndex] = resultSet.getDouble("pH");
                data[1][colIndex] = resultSet.getDouble("BE");
                data[2][colIndex] = resultSet.getDouble("HCO3");
                data[3][colIndex] = resultSet.getDouble("SystemiskCa");
                data[4][colIndex] = resultSet.getDouble("PostfilterCa");
                colIndex--;
            }

            resultSet.close();
            statement.close();
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
