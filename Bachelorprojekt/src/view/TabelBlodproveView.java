package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TabelBlodproveView {
    private JPanel panel;
    private JTable table;

    public TabelBlodproveView() {
        panel = new JPanel(new BorderLayout());
        table = new JTable(new DefaultTableModel());
        table.setRowHeight(20);

        // Tilpas renderer for at centrere teksten og skifte baggrundsfarve på rækkerne
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Skiftende baggrund
                cell.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);

                // Justering og font
                if (column == 0) {
                    setHorizontalAlignment(LEFT); // Første kolonne venstrejusteret
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                } else {
                    setHorizontalAlignment(CENTER); // Øvrige kolonner centreret
                }

                return cell;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTable getTable() {
        return table;
    }
}
