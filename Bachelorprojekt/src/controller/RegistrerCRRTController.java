/*
package controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import model.RegistrerCRRTModel;
import view.RegistrerCRRTView;

public class RegistrerCRRTController {
    private RegistrerCRRTModel model;
    private RegistrerCRRTView view;
    private String cprNr;
    private TabelCRRTController tabelController; // Add this field
    private static final Logger logger = Logger.getLogger(RegistrerCRRTController.class.getName());

    public RegistrerCRRTController(RegistrerCRRTModel model, RegistrerCRRTView view, String cprNr,
            TabelCRRTController tabelController) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;
        this.tabelController = tabelController; // Initialize this field

        this.view.getSaveButton().addActionListener(e -> {
            try {
                String dialysatflow = view.getTextFields()[0].getText();
                String blodflow = view.getTextFields()[1].getText();
                String vaesketraek = view.getTextFields()[2].getText();
                String indloebstryk = view.getTextFields()[3].getText();
                String returtryk = view.getTextFields()[4].getText();
                String praefiltertryk = view.getTextFields()[5].getText();
                String heparin = view.getTextFields()[6].getText();

                // Validate inputs
                if (dialysatflow.isEmpty() || blodflow.isEmpty() || vaesketraek.isEmpty() ||
                        indloebstryk.isEmpty() || returtryk.isEmpty() || praefiltertryk.isEmpty()
                        || heparin.isEmpty()) {
                    view.showError("Alle felter skal udfyldes.");
                    return;
                }

                // Generate the current timestamp in the format YYYY-MM-DD HH:MM:SS
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedTimestamp = sdf.format(timestamp);

                // Save to database
                model.saveToDatabase(cprNr, formattedTimestamp, dialysatflow, blodflow, vaesketraek, indloebstryk,
                        returtryk, praefiltertryk, heparin);
                logger.info("Data saved successfully for CPR: " + cprNr);

                // Update the table view with new data on the EDT
                SwingUtilities.invokeLater(() -> {
                    logger.info("Calling updateView on TabelCRRTController.");
                    tabelController.updateView(cprNr);
                    tabelController.getView().getTable().revalidate();
                    tabelController.getView().getTable().repaint();
                });

                // Close the window
                view.close();

            } catch (Exception ex) {
                logger.severe("Error saving data: " + ex.getMessage());
                view.showError("An error occurred while saving data.");
            }
        });
    }

    public void showView() {
        view.setVisible(true);
    }
}
*/
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
    private NormalvaerdierModel normalvaerdierModel;  // Added the NormalvaerdierModel
    private static final Logger logger = Logger.getLogger(RegistrerCRRTController.class.getName());

    public RegistrerCRRTController(RegistrerCRRTModel model, RegistrerCRRTView view, String cprNr,
                                   TabelCRRTController tabelController, NormalvaerdierModel normalvaerdierModel) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;
        this.tabelController = tabelController;
        this.normalvaerdierModel = normalvaerdierModel; // Initializing NormalvaerdierModel

        this.view.getSaveButton().addActionListener(e -> saveData());
    }

    private void saveData() {
        try {
            String[] parameterNames = { "Dialysatflow", "Blodflow", "Væsketræk", "Indløbstryk", "Returtryk", "Præfiltertryk", "Heparin" };
            String[] values = new String[view.getTextFields().length];
    
            // Fetching values from the view
            for (int i = 0; i < view.getTextFields().length; i++) {
                values[i] = view.getTextFields()[i].getText();
                if (values[i].isEmpty()) {
                    view.showError("Alle felter skal udfyldes.");
                    return;
                }
            }
    
            // Validate values based on the normal range
            for (int i = 0; i < values.length; i++) {
                double parsedValue = Double.parseDouble(values[i]);
                if (!normalvaerdierModel.isValueNormal(parameterNames[i], parsedValue)) {
                    double[] range = normalvaerdierModel.getRange(parameterNames[i]);
                    String errorMessage = String.format("%s er udenfor normalområdet, som er %.2f - %.2f. \n Vil du ændre eller fortsætte?", 
                            parameterNames[i], range[0], range[1]);
                    
                    // Show a confirmation dialog with the error message and options to change or continue
                    int choice = view.showConfirmDialog(errorMessage, "Beslutnigsstøtte", new String[]{"Ændre", "Fortsæt"});
                    if (choice == 0) {
                        return; // User wants to change the value
                    }
                }
            }
    
            // Generate current timestamp for the record
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = sdf.format(timestamp);
    
            // Save the data to the database
            model.saveToDatabase(cprNr, formattedTimestamp,
                values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
            logger.info("Data saved successfully for CPR: " + cprNr);
    
            // Update the table view with new data
            SwingUtilities.invokeLater(() -> {
                logger.info("Calling updateView on TabelCRRTController.");
                tabelController.updateView(cprNr);
                tabelController.getView().getTable().revalidate();
                tabelController.getView().getTable().repaint();
            });
    
            // Close the view after saving
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
