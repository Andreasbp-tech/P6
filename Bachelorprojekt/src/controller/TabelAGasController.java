package controller;

import model.NormalvaerdierModel;
import model.TabelAGasModel;
import model.BeslutningsstotteModel;
import view.TabelAGasView;
import model.ValgStueModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabelAGasController {
    private TabelAGasModel model;
    private TabelAGasView view;
    private NormalvaerdierModel normalvaerdierModel = new NormalvaerdierModel();
    private BeslutningsstotteModel beslutningsstotteModel = new BeslutningsstotteModel(normalvaerdierModel);
    private ValgStueModel valgStueModel;
    private String valgtDato;
    private String cprNr; // Gem CPR-nummeret her

    public TabelAGasController(TabelAGasModel model, TabelAGasView view, ValgStueModel valgStueModel) {
        this.model = model;
        this.view = view;
        this.valgStueModel = valgStueModel;
        addMouseListener();
    }

    private void addMouseListener() {
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTable().rowAtPoint(e.getPoint());
                int col = view.getTable().columnAtPoint(e.getPoint());

                boolean[][] outlierMatrix = model.getOutlierMatrix();
                if (row >= 0 && col > 0 && outlierMatrix != null &&
                        row < outlierMatrix.length && col < outlierMatrix[row].length &&
                        outlierMatrix[row][col]) {

                    String header = view.getTable().getColumnModel().getColumn(col).getHeaderValue().toString();
                    String cleanText = header.replaceAll("<[^>]*>", "").trim();
                    if (cleanText.length() >= 13) {
                        String rawDate = cleanText.substring(5, 13); // "06-05-25"
                        String[] dateParts = rawDate.split("-");
                        if (dateParts.length == 3) {
                            valgtDato = "20" + dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
                        }
                    }

                    // Hent CPR-nummer fra ValgStueModel
                    cprNr = valgStueModel.getCprNr(); // CPR-nummeret er nu gemt i cprNr

                    // Hent kreatinin og carbamid fra databasen
                    Double[] resultater = BeslutningsstotteModel.hentKreatininOgCarbamid(cprNr, valgtDato);
                    Double kreatinin = resultater[0];
                    Double carbamid = resultater[1];

                    // Vælg hvilken type dialog der skal vises
                    Object pH = view.getTable().getValueAt(0, col);
                    Object BE = view.getTable().getValueAt(1, col);
                    Object HCO3 = view.getTable().getValueAt(2, col);
                    Object systemiskCa = view.getTable().getValueAt(3, col);
                    Object postfilterCa = view.getTable().getValueAt(4, col);

                    // Brug kreatinin og carbamid i analysen
                    switch (row) {
                        case 0, 2 -> showAcidBaseDialog(pH, BE, HCO3, kreatinin, carbamid);
                        case 3 -> showSystemiskCaDialog(systemiskCa);
                        case 4 -> showPostCaDialog(postfilterCa);
                    }
                }
            }

            private void showAcidBaseDialog(Object pH, Object BE, Object HCO3, Double kreatinin, Double carbamid) {
                String interpretation = beslutningsstotteModel.analyserAcidBase(pH, BE, HCO3, kreatinin, carbamid);

                String message = "<html>"
                        + "Syre-base uregelmæssighed:<br><br>"
                        + "pH: " + value(pH) + "<br>"
                        + "BE: " + value(BE) + "<br>"
                        + "HCO₃⁻: " + value(HCO3) + "<br>"
                        + "Kreatinin: " + value(kreatinin) + "<br>"
                        + "Carbamid: " + value(carbamid) + "<br><br>"
                        + "<b>Beslutningsstøtte:</b><br>"
                        + interpretation;

                JOptionPane.showMessageDialog(view.getTable(), message, "Beslutningsstøtte",
                        JOptionPane.WARNING_MESSAGE);
            }

            private void showPostCaDialog(Object ca) {
                String result = beslutningsstotteModel.analyserPostfilterCa(ca);
                JOptionPane.showMessageDialog(view.getTable(),
                        "<html><b>Postfilter Calcium:</b> " + value(ca) + " mmol/l<br><br>"
                                + "<b>Beslutningsstøtte:</b><br>"
                                + "<font color='red'>" + result.replace("\n", "<br>") + "</font></html>",
                        "Beslutningsstøtte", JOptionPane.WARNING_MESSAGE);
            }

            private void showSystemiskCaDialog(Object ca) {
                String result = beslutningsstotteModel.analyserSystemiskCa(ca);
                JOptionPane.showMessageDialog(view.getTable(),
                        "<html><b>Systemisk Calcium:</b> " + value(ca) + " mmol/l<br><br>"
                                + "<b>Beslutningsstøtte:</b><br>"
                                + "<font color='red'>" + result.replace("\n", "<br>") + "</font></html>",
                        "Beslutningsstøtte", JOptionPane.WARNING_MESSAGE);
            }

            private String value(Object o) {
                return o != null ? o.toString() : "N/A";
            }
        });
    }

    public void updateView(String cprNr) {
        model.fetchData(cprNr);
        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        tableModel.addColumn(""); // første kolonne

        for (int i = 0; i < model.getTimestamps().size(); i++) {
            String header = "<html><center>" + model.getTimestamps().get(i) + "<br>" + model.getDates().get(i)
                    + "</center></html>";
            tableModel.addColumn(header);
        }

        Object[][] data = model.getData();
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        String[] parametre = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            parametre[i] = data[i][0].toString();
        }

        boolean[][] outliers = normalvaerdierModel.analyserDataNormalvaerdi(data, cprNr);
        view.setOutlierMatrix(outliers);
        model.setOutlierMatrix(outliers);

        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        view.getTable().revalidate();
        view.getTable().repaint();
    }

    public String getValgtDato() {
        return valgtDato;
    }
}
