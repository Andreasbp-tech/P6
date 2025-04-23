package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import utilities.DatabaseConnection;
import view.ValgStue;

public class TabelCRRT {
    public static JPanel tablePanel;
    // private static JTable table1;

    static {
        tablePanel = new JPanel(new BorderLayout());

        // Create table with parametrene horisontalt and tidspunkterne vertikalt
        String[] rowNames = { "Dialysatflow", "Blodflow", "Væsketræk", "Indløbstryk",
                "Returtryk", "Præfiltertryk", "Heparin",
                "Citratdosis", "Calciumdosis" };
        DefaultTableModel model1 = new DefaultTableModel();
        JTable table1 = new JTable(model1);
        table1.setRowHeight(20); // Set the row height to 30 pixels

        // Set custom cell renderer for alternating row colors
        table1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    cell.setBackground(Color.LIGHT_GRAY);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                return cell;
            }
        });

        // Fetch data and populate table
        fetchDataAndPopulateTables(model1, rowNames);

        // Set fixed width for the first column
        TableColumn firstColumn = table1.getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        JScrollPane scrollPane1 = new JScrollPane(table1);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(scrollPane1, BorderLayout.CENTER);

    }

    private static void fetchDataAndPopulateTables(DefaultTableModel model1, String[] rowNames) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM CRRT WHERE CPR_nr = '" + ValgStue.cprNr
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
                double dialysatflow = resultSet.getDouble("dialysatflow");
                double blodflow = resultSet.getDouble("blodflow");
                double væsketræk = resultSet.getDouble("væsketræk");
                double indløbstryk = resultSet.getDouble("indløbstryk");
                double returtryk = resultSet.getDouble("returtryk");
                double præfiltertryk = resultSet.getDouble("præfiltertryk");
                double heparin = resultSet.getDouble("heparin");
                double citratdosis = resultSet.getDouble("citratdosis");
                double calciumdosis = resultSet.getDouble("calciumdosis");

                data1[0][colIndex] = dialysatflow;
                data1[1][colIndex] = blodflow;
                data1[2][colIndex] = væsketræk;
                data1[3][colIndex] = indløbstryk;
                data1[4][colIndex] = returtryk;
                data1[5][colIndex] = præfiltertryk;
                data1[6][colIndex] = heparin;
                data1[7][colIndex] = citratdosis;
                data1[8][colIndex] = calciumdosis;

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

    /*
     * public static void clearTable() {
     * DefaultTableModel model = (DefaultTableModel) table1.getModel();
     * model.setRowCount(0); // Clear all rows
     * model.setColumnCount(0); // Clear all columns
     * }
     */

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
