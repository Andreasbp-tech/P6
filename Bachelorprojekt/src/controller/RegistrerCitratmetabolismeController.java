/*package controller;

import model.RegistrerCitratmetabolismeModel;
import view.RegistrerCitratmetabolismeView;

public class RegistrerCitratmetabolismeController {
    private RegistrerCitratmetabolismeModel model;
    private RegistrerCitratmetabolismeView view;
    private TabelCitratmetabolismeController citratTabelCtrl;

    private String cprNr;

    public RegistrerCitratmetabolismeController(
            RegistrerCitratmetabolismeModel model,
            RegistrerCitratmetabolismeView view,
            String cprNr,
            TabelCitratmetabolismeController citratTabelCtrl) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;
        this.citratTabelCtrl = citratTabelCtrl;

        this.view.getSaveButton().addActionListener(e -> {
            String calciumdosis = view.getTextFields()[0].getText();
            String citratdosis = view.getTextFields()[1].getText();

            // Validate inputs
            if (calciumdosis.isEmpty() || citratdosis.isEmpty()) {
                view.showError("Alle felter skal udfyldes.");
                return;
            }
            model.saveToDatabase(cprNr, calciumdosis, citratdosis);
            citratTabelCtrl.updateView(cprNr);
            view.close();
        });
    }

    public void showView() {
        view.setVisible(true);
    }
}*/
package controller;

import model.NormalvaerdierModel;
import model.RegistrerCitratmetabolismeModel;
import view.RegistrerCitratmetabolismeView;

public class RegistrerCitratmetabolismeController {
    private RegistrerCitratmetabolismeModel model;
    private RegistrerCitratmetabolismeView view;
    private TabelCitratmetabolismeController citratTabelCtrl;
    private NormalvaerdierModel normalvaerdierModel;  // Added NormalvaerdierModel

    private String cprNr;

    public RegistrerCitratmetabolismeController(
            RegistrerCitratmetabolismeModel model,
            RegistrerCitratmetabolismeView view,
            String cprNr,
            TabelCitratmetabolismeController citratTabelCtrl,
            NormalvaerdierModel normalvaerdierModel) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;
        this.citratTabelCtrl = citratTabelCtrl;
        this.normalvaerdierModel = normalvaerdierModel;  // Initialize NormalvaerdierModel

        this.view.getSaveButton().addActionListener(e -> saveData());
    }

    private void saveData() {
        try {
            String[] parameterNames = { "Calciumdosis", "Citratdosis" };
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
                    int choice = view.showConfirmDialog(errorMessage, "Beslutningsstøtte", new String[]{"Ændre", "Fortsæt"});
                    if (choice == 0) {
                        return; // User wants to change the value
                    }
                }
            }

            // Save the data to the database
            model.saveToDatabase(cprNr, values[0], values[1]);
            citratTabelCtrl.updateView(cprNr);
            view.close();

        } catch (Exception ex) {
            view.showError("En fejl opstod under gemning af data.");
        }
    }

    public void showView() {
        view.setVisible(true);
    }
}

