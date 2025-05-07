package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import utilities.DatabaseConnection;

public class TabelCRRTModel {
    private List<String> timestamps;
    private List<String> dates;
    private Object[][] data;
    private static final Logger logger = Logger.getLogger(TabelCRRTModel.class.getName());

    public void fetchData(String cprNr) {
        timestamps = new ArrayList<>();
        dates = new ArrayList<>();
        List<Object[]> dataList = new ArrayList<>();
        String[] rowNames = { "Dialysatflow", "Blodflow", "Væsketræk", "Indløbstryk", "Returtryk", "Præfiltertryk" };

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM CRRT WHERE CPR_nr = '" + cprNr + "' ORDER BY tidspunkt DESC LIMIT 18";
            ResultSet resultSet = statement.executeQuery(query);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next()) {
                String tidspunkt = resultSet.getString("tidspunkt");

                LocalDateTime datetime = LocalDateTime.parse(tidspunkt, formatter);
                String timePart = datetime.format(DateTimeFormatter.ofPattern("HH:mm"));
                String datePart = datetime.format(DateTimeFormatter.ofPattern("dd-MM-yy"));

                timestamps.add(timePart);
                dates.add(datePart);
            }

            java.util.Collections.reverse(timestamps);
            java.util.Collections.reverse(dates);

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
                colIndex--;
            }

            resultSet.close();
            statement.close();
            conn.close();

            // logger.info("Data fetched successfully for CPR: " + cprNr);
        } catch (Exception e) {
            logger.severe("Error fetching data: " + e.getMessage());
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
