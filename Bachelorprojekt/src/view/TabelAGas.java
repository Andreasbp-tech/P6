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
import model.ValgStueModel;

public class TabelAGas {
    public static JPanel tablePanel;
    private static JTable table1; // Class-level variable

    static {
        tablePanel = new JPanel(new GridLayout(1, 1));

        // Create table with parametrene horisontalt and tidspunkterne vertikalt
        String[] rowNames = { "pH", "BE", "HCO3", "SystemiskCa", "PostfilterCa" };
        DefaultTableModel model1 = new DefaultTableModel();
        table1 = new JTable(model1); // Initialize table1
        table1.setRowHeight(20); // Set the row height to 20 pixels

        JScrollPane scrollPane1 = new JScrollPane(table1);
        tablePanel.add(scrollPane1);

        // Create an instance of ValgStueModel
        ValgStueModel model = new ValgStueModel();
        model.setValgtStue(1); // Example value, you can set it dynamically
        model.getPatientData(1); // Load patient data

        // Fetch data and populate table
        fetchDataAndPopulateTables(model1, rowNames, model);

        // Set custom cell renderer for alternating row colors and bold text
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
                if (column == 0 /* || row == 0 */) {
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                }
                return cell;
            }
        });

        // Set fixed width for the first column
        if (table1.getColumnModel().getColumnCount() > 0) {
            TableColumn firstColumn = table1.getColumnModel().getColumn(0);
            firstColumn.setPreferredWidth(100);
            firstColumn.setMinWidth(100);
            firstColumn.setMaxWidth(100);
        }
    }

    private static void fetchDataAndPopulateTables(DefaultTableModel model1, String[] rowNames,
            ValgStueModel valgStueModel) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM A_gas WHERE CPR_nr = '" + valgStueModel.getCprNr()
                    + "' ORDER BY tidspunkt DESC LIMIT 24";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println(valgStueModel.getCprNr());

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
                double pH = resultSet.getDouble("pH");
                double BE = resultSet.getDouble("BE");
                double HCO3 = resultSet.getDouble("HCO3");
                double systemiskCa = resultSet.getDouble("SystemiskCa");
                double postfilterCa = resultSet.getDouble("PostfilterCa");

                data1[0][colIndex] = pH;
                data1[1][colIndex] = BE;
                data1[2][colIndex] = HCO3;
                data1[3][colIndex] = systemiskCa;
                data1[4][colIndex] = postfilterCa;

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

    public static void clearTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Clear all rows
        model.setColumnCount(0); // Clear all columns
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
