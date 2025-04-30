package controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
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
    private static final Logger logger = Logger.getLogger(RegistrerCRRTController.class.getName());

    public RegistrerCRRTController(RegistrerCRRTModel model, RegistrerCRRTView view, String cprNr,
            TabelCRRTController tabelController, NormalvaerdierModel normalvaerdierModel) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;
        this.tabelController = tabelController;
        this.normalvaerdierModel = normalvaerdierModel;

        this.view.getSaveButton().addActionListener(e -> saveData());
    }

    private void saveData() {
        try {
            String[] parameterNames = { "Dialysatflow", "Blodflow", "Væsketræk", "Indløbstryk", "Returtryk",
                    "Præfiltertryk" };
            String[] values = new String[view.getTextFields().length];

            for (int i = 0; i < view.getTextFields().length; i++) {
                values[i] = view.getTextFields()[i].getText();
                if (values[i].isEmpty()) {
                    view.showError("Alle felter skal udfyldes.");
                    return;
                }
            }

            for (int i = 0; i < values.length; i++) {
                double parsedValue = Double.parseDouble(values[i]);
                if (!normalvaerdierModel.isValueNormal(parameterNames[i], parsedValue)) {
                    double[] range = normalvaerdierModel.getRange(parameterNames[i]);
                    String errorMessage = String.format(
                            "%s er udenfor normalområdet, som er %.2f - %.2f. \n Vil du ændre eller fortsætte?",
                            parameterNames[i], range[0], range[1]);
                    int choice = view.showConfirmDialog(errorMessage, "Beslutnigsstøtte",
                            new String[] { "Ændre", "Fortsæt" });
                    if (choice == 0) {
                        return;
                    }
                }
            }

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = sdf.format(timestamp);

            model.saveToDatabase(cprNr, formattedTimestamp,
                    values[0], values[1], values[2], values[3], values[4], values[5]);
            logger.info("Data saved successfully for CPR: " + cprNr);

            SwingUtilities.invokeLater(() -> {
                logger.info("Calling updateView on TabelCRRTController.");
                tabelController.updateView(cprNr);
                tabelController.getView().getTable().revalidate();
                tabelController.getView().getTable().repaint();
            });

            view.close();

        } catch (Exception ex) {
            logger.severe("Error saving data: " + ex.getMessage());
            view.showError("En fejl opstod under gemning af data.");
        }
    }

    public void showView() {
        view.setVisible(true);
    }
}
