package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.ValgStueModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import utilities.DatabaseConnection;
import view.ValgStueView;

public class TabelBlodproeve {
    public static JPanel tablePanel;

    static {
        tablePanel = new JPanel(new GridLayout(1, 1));

        // Create table with parametrene horisontalt and tidspunkterne vertikalt
        String[] rowNames = { "Total Ca", "Creatinin", "Carbamid", "Kalium", "Infektionstal" };
        DefaultTableModel model1 = new DefaultTableModel();
        JTable table1 = new JTable(model1);
        JScrollPane scrollPane1 = new JScrollPane(table1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tablePanel.add(scrollPane1);

        // Fetch data and populate table
        ValgStueModel model = new ValgStueModel();
        fetchDataAndPopulateTables(model1, rowNames, model);

        // Set preferred width for the first column after populating the table
        if (table1.getColumnModel().getColumnCount() > 0) {
            TableColumn firstColumn = table1.getColumnModel().getColumn(0);
            firstColumn.setPreferredWidth(500); // Set the width as needed

            // Set preferred width for other columns
            for (int i = 1; i < table1.getColumnModel().getColumnCount(); i++) {
                TableColumn column = table1.getColumnModel().getColumn(i);
                column.setPreferredWidth(300); // Adjust the width as needed
            }
        }
    }

    private static void fetchDataAndPopulateTables(DefaultTableModel model1, String[] rowNames,
            ValgStueModel valgStueModel) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM Blodprøve WHERE CPR_nr = '" + valgStueModel.getCprNr()
                    + "' ORDER BY tidspunkt DESC LIMIT 24";
            ResultSet resultSet = statement.executeQuery(query);

            // Add column names (timestamps) dynamically
            model1.addColumn("");
            java.util.List<String> timestamps = new java.util.ArrayList<>();
            while (resultSet.next()) {
                String tidspunkt = resultSet.getString("tidspunkt");
                String timePart = tidspunkt.substring(11, 16); // Extracts the time part (HH:MM)
                timestamps.add(timePart);
            }

            // Reverse the order of timestamps
            java.util.Collections.reverse(timestamps);
            for (String timePart : timestamps) {
                model1.addColumn(timePart);
            }

            // Reset the result set cursor to the beginning
            resultSet.beforeFirst();

            // Initialize data arrays
            Object[][] data1 = new Object[rowNames.length][model1.getColumnCount()];

            // Fill the first column with row names
            for (int i = 0; i < rowNames.length; i++) {
                data1[i][0] = rowNames[i];
            }

            // Populate data arrays with values from the result set
            int colIndex = model1.getColumnCount() - 1;
            while (resultSet.next()) {
                double totalCa = resultSet.getDouble("totalCa");
                double kreatinin = resultSet.getDouble("kreatinin");
                double carbamid = resultSet.getDouble("carbamid");
                double kalium = resultSet.getDouble("kalium");
                double infektionstal = resultSet.getDouble("infektionstal");

                data1[0][colIndex] = totalCa;
                data1[1][colIndex] = kreatinin;
                data1[2][colIndex] = carbamid;
                data1[3][colIndex] = kalium;
                data1[4][colIndex] = infektionstal;

                colIndex--;
            }

            // Add data arrays to the model
            for (Object[] row : data1) {
                model1.addRow(row);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Patient Data");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.add(tablePanel);
            frame.setVisible(true);
        });
    }
}
