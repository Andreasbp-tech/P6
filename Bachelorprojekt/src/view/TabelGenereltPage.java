package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import utilities.DatabaseConnection;
import view.ValgStue;

public class TabelGenereltPage {
    public static JPanel tablePanel;

    static {
        tablePanel = new JPanel(new GridLayout(2, 1));

        // Create tables with parametrene horisontalt and tidspunkterne vertikalt
        String[] rowNames = { "Total Ca", "Creatinin", "Carbamid", "Kalium", "Infektionstal" };
        DefaultTableModel model1 = new DefaultTableModel();
        JTable table1 = new JTable(model1);
        JScrollPane scrollPane1 = new JScrollPane(table1);

        DefaultTableModel model2 = new DefaultTableModel();
        JTable table2 = new JTable(model2);
        JScrollPane scrollPane2 = new JScrollPane(table2);

        tablePanel.add(scrollPane1);
        tablePanel.add(scrollPane2);

        // Fetch data and populate tables
        fetchDataAndPopulateTables(model1, model2, rowNames);
    }

    private static void fetchDataAndPopulateTables(DefaultTableModel model1, DefaultTableModel model2,
            String[] rowNames) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM Blodprøve WHERE CPR_nr = '" + ValgStue.cprNr + "'";
            ResultSet resultSet = statement.executeQuery(query);

            // Add column names (timestamps) dynamically
            model1.addColumn("");
            model2.addColumn("");
            while (resultSet.next()) {
                String tidspunkt = resultSet.getString("tidspunkt");
                model1.addColumn(tidspunkt);
                model2.addColumn(tidspunkt);
            }

            // Reset the result set cursor to the beginning
            resultSet.beforeFirst();

            // Initialize data arrays
            Object[][] data1 = new Object[rowNames.length][model1.getColumnCount()];
            Object[][] data2 = new Object[rowNames.length][model2.getColumnCount()];

            // Fill the first column with row names
            for (int i = 0; i < rowNames.length; i++) {
                data1[i][0] = rowNames[i];
                data2[i][0] = rowNames[i];
            }

            // Populate data arrays with values from the result set
            int colIndex = 1;
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

                data2[0][colIndex] = totalCa;
                data2[1][colIndex] = kreatinin;
                data2[2][colIndex] = carbamid;
                data2[3][colIndex] = kalium;
                data2[4][colIndex] = infektionstal;

                colIndex++;
            }

            // Add data arrays to the models
            for (Object[] row : data1) {
                model1.addRow(row);
            }
            for (Object[] row : data2) {
                model2.addRow(row);
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
