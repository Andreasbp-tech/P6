package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TabelAGasView {
    private JPanel tablePanel;
    private JTable table;
    private boolean[][] outlierMatrix;

    public TabelAGasView() {
        tablePanel = new JPanel(new BorderLayout());
        table = new JTable(new DefaultTableModel());
        table.setRowHeight(20);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Standard tekstfarve og baggrund
                setForeground(Color.BLACK);
                setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);

                // Tjek for outlier og farv teksten rød
                if (column > 0 && outlierMatrix != null
                        && row < outlierMatrix.length && column < outlierMatrix[row].length) {
                    if (outlierMatrix[row][column]) {
                        setForeground(Color.RED); // Kun tekst bliver rød
                    }
                }

                // Justering og font
                if (column == 0) {
                    setHorizontalAlignment(LEFT);
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setHorizontalAlignment(CENTER);
                }

                return this;
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

    public void setOutlierMatrix(boolean[][] matrix) {
        this.outlierMatrix = matrix;
    }
}
