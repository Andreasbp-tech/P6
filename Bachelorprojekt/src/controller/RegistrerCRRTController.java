package controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.NormalvaerdierModel;
import model.RegistrerCRRTModel;
import view.RegistrerCRRTView;

public class RegistrerCRRTController {
    private RegistrerCRRTModel model;
    private RegistrerCRRTView view;
    private String cprNr;
    private TabelCRRTController tabelController;
    private NormalvaerdierModel normalvaerdierModel;
    // private static final Logger logger =
    // Logger.getLogger(RegistrerCRRTController.class.getName());

    public RegistrerCRRTController(RegistrerCRRTModel model,
            RegistrerCRRTView view,
            String cprNr,
            TabelCRRTController tabelController,
            NormalvaerdierModel normalvaerdierModel) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;
        this.tabelController = tabelController;
        this.normalvaerdierModel = normalvaerdierModel;
        this.view.getSaveButton().addActionListener(e -> saveData());
    }

    private void saveData() {
        try {
            String[] parameterNames = {
                    "Dialysatflow", "Blodflow", "Væsketræk",
                    "Indløbstryk", "Returtryk", "Præfiltertryk"
            };
            String[] values = new String[view.getTextFields().length];

            // Hent input og tjek for tomme felter
            for (int i = 0; i < values.length; i++) {
                values[i] = view.getTextFields()[i].getText();
                if (values[i].isEmpty()) {
                    view.showError("Alle felter skal udfyldes.");
                    return;
                }
            }

            // Byg samlet fejlmeddelelse
            StringBuilder warning = new StringBuilder();
            boolean requiresDoctorNotification = false;
            for (int i = 0; i < values.length; i++) {
                double val = Double.parseDouble(values[i]);
                if (!normalvaerdierModel.isValueNormal(parameterNames[i], val)) {
                    double[] range = normalvaerdierModel.getRange(parameterNames[i]);
                    warning.append(String.format(
                            "%s (%.2f) er uden for normalområdet %.2f–%.2f.\n",
                            parameterNames[i], val, range[0], range[1]));
                }
            }

            // Hvis der er nogen fejl, vis samlet popup
            if (warning.length() > 0) {
                int choice = view.showConfirmDialog(
                        warning.toString(),
                        "Beslutningsstøtte",
                        new String[] { "Ændre", "Gem alligevel" }, JOptionPane.WARNING_MESSAGE);
                if (choice == 0) {
                    return; // Brugeren vil ændre værdierne
                }
            }

            // Gem data
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            String tid = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);
            model.saveToDatabase(
                    cprNr, tid,
                    values[0], values[1], values[2],
                    values[3], values[4], values[5]);
            // logger.info("Data saved successfully for CPR: " + cprNr);

            view.showConfirmDialog("Data er gemt korrekt.", "Bekræftelse", new String[] { "OK" },
                    JOptionPane.INFORMATION_MESSAGE);

            // Opdater tabelvisning
            SwingUtilities.invokeLater(() -> {
                tabelController.updateView(cprNr);
                tabelController.getView().getTable().revalidate();
                tabelController.getView().getTable().repaint();
            });

            view.close();

        } catch (Exception ex) {
            // logger.severe("Error saving data: " + ex.getMessage());
            view.showError("En fejl opstod under gemning af data.");
        }
    }

    public void showView() {
        view.setVisible(true);
    }
}
