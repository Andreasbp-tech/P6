package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import utilities.ProgramIcon;

public class TabelCRRTView {
    private JPanel tablePanel;
    private JTable table;
    private boolean[][] outlierMatrix;

    public TabelCRRTView() {
        tablePanel = new JPanel(new BorderLayout());
        table = new JTable(new DefaultTableModel());
        table.setRowHeight(20);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Standard baggrund
                cell.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);

                // Sæt tekstfarve til sort som standard
                cell.setForeground(Color.BLACK);

                if (column == 0) {
                    setHorizontalAlignment(LEFT);
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                } else {
                    setHorizontalAlignment(CENTER);

                    // Hvis outlierMatrix markerer denne celle som outlier → rød tekst
                    if (outlierMatrix != null
                            && row < outlierMatrix.length
                            && column < outlierMatrix[row].length
                            && outlierMatrix[row][column]) {
                        cell.setForeground(Color.RED);
                    }
                }

                return cell;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }

    public JTable getTable() {
        return table;
    }

    public void setOutlierMatrix(boolean[][] outlierMatrix) {
        this.outlierMatrix = outlierMatrix;
    }
}
