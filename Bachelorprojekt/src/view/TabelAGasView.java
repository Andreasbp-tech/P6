package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.BeslutningsstotteModel;

public class TabelAGasView {
    private JPanel tablePanel;
    private JTable table;
    private boolean[][] outlierMatrix;
    private BeslutningsstotteModel beslutningsstotteModel;
    private String valgtDato;

    public TabelAGasView() {
        beslutningsstotteModel = new BeslutningsstotteModel();

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
                        setForeground(Color.RED);
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

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row >= 0 && col > 0 && outlierMatrix != null) {
                    if (row < outlierMatrix.length && col < outlierMatrix[row].length) {
                        if (outlierMatrix[row][col]) {
                            // ➤ Gem datoen fra kolonneoverskriften
                            Object headerValue = table.getColumnModel().getColumn(col).getHeaderValue();
                            if (headerValue != null) {
                                String headerHtml = headerValue.toString();
                                String cleanText = headerHtml.replaceAll("<[^>]*>", "").trim();

                                if (cleanText.length() >= 13) {
                                    String rawDate = cleanText.substring(5, 13); // "06-05-25"
                                    String[] dateParts = rawDate.split("-");
                                    if (dateParts.length == 3) {
                                        String day = dateParts[0];
                                        String month = dateParts[1];
                                        String year = "20" + dateParts[2]; // "25" → "2025"
                                        valgtDato = year + "-" + month + "-" + day;
                                        System.out.println("Dato: " + valgtDato);
                                    } else {
                                        valgtDato = "";
                                        System.out.println("Dato kunne ikke parses korrekt: " + rawDate);
                                    }
                                } else {
                                    valgtDato = "";
                                    System.out.println("CleanText for kort: " + cleanText);
                                }
                            }

                            Object pH = table.getValueAt(0, col);
                            Object BE = table.getValueAt(1, col);
                            Object HCO3 = table.getValueAt(2, col);
                            Object SystemiskCa = table.getValueAt(3, col);
                            Object PostCa = table.getValueAt(4, col);

                            if (row == 0 || row == 2) {
                                showAcidBaseDialog(pH, BE, HCO3);
                            } else if (row == 3) {
                                showSystemicCaDialog(SystemiskCa);
                            } else if (row == 4) {
                                showPostCaDialog(PostCa);
                            }
                        }
                    }
                }
            }

            private void showSystemicCaDialog(Object systemiskCa) {
                String interpretation = beslutningsstotteModel.analyserSystemiskCa(systemiskCa);
                String message = String.format(
                        "<html><b>Systemisk Calcium:</b> %s mmol/l<br><br>" +
                                "<b>Forslag til justering:</b><br><font color='red'>%s</font></html>",
                        valueString(systemiskCa), interpretation.replace("\n", "<br>"));
                JOptionPane.showMessageDialog(table, message, "Beslutningsstøtte", JOptionPane.WARNING_MESSAGE);
            }

            private void showPostCaDialog(Object postfilterCa) {
                String interpretation = beslutningsstotteModel.analyserPostfilterCa(postfilterCa);
                String message = String.format(
                        "<html><b>Postfilter Calcium:</b> %s mmol/l<br><br>" +
                                "<b>Forslag til justering:</b><br><font color='red'>%s</font></html>",
                        valueString(postfilterCa), interpretation.replace("\n", "<br>"));
                JOptionPane.showMessageDialog(table, message, "Beslutningsstøtte", JOptionPane.WARNING_MESSAGE);
            }

            private void showAcidBaseDialog(Object pH, Object BE, Object HCO3) {
                String interpretation = beslutningsstotteModel.analyserAcidBase(pH, BE, HCO3);
                String message = String.format(
                        "Syre-base uregelmæssighed detekteret:\n\npH: %s\nBE: %s\nHCO₃⁻: %s\n \n Forslag til justering: \n%s",
                        valueString(pH), valueString(BE), valueString(HCO3), interpretation);
                JOptionPane.showMessageDialog(table, message, "Syre-base evaluering", JOptionPane.WARNING_MESSAGE);
            }

            private String valueString(Object obj) {
                return obj != null ? obj.toString() : "N/A";
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

    public String getValgtDato() {
        return valgtDato;
    }

}
