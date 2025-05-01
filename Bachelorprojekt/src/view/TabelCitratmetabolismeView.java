package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TabelCitratmetabolismeView {
    private JPanel tablePanel;
    private JTable table;
    private boolean[][] outlierMatrix;

    public TabelCitratmetabolismeView() {
        tablePanel = new JPanel(new BorderLayout());
        table = new JTable(new DefaultTableModel());
        table.setRowHeight(20);

        // Custom renderer: red text for outliers
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int col) {
                Component cell = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);

                // alternating background
                cell.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                // default text color
                cell.setForeground(Color.BLACK);

                if (col == 0) {
                    setHorizontalAlignment(LEFT);
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                } else {
                    setHorizontalAlignment(CENTER);
                    // if this cell is marked as an outlier, paint the text red
                    if (outlierMatrix != null
                            && row < outlierMatrix.length
                            && col < outlierMatrix[row].length
                            && outlierMatrix[row][col]) {
                        cell.setForeground(Color.RED);
                    }
                }
                return cell;
            }
        });

        JScrollPane scroll = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(scroll, BorderLayout.CENTER);
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }

    public JTable getTable() {
        return table;
    }

    /** Called by controller to hand over the matrix of outlier-flags */
    public void setOutlierMatrix(boolean[][] outlierMatrix) {
        this.outlierMatrix = outlierMatrix;
    }
}
