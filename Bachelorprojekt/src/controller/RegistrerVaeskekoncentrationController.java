package controller;

import javax.swing.JOptionPane;

import model.NormalvaerdierModel;
import model.RegistrerVaeskekoncentrationModel;
import view.RegistrerVaeskekoncentrationView;

public class RegistrerVaeskekoncentrationController {
    private RegistrerVaeskekoncentrationModel model;
    private RegistrerVaeskekoncentrationView view;
    private TabelVaeskekoncentrationController citratTabelCtrl;
    private NormalvaerdierModel normalvaerdierModel; // Added NormalvaerdierModel

    private String cprNr;

    public RegistrerVaeskekoncentrationController(
            RegistrerVaeskekoncentrationModel model,
            RegistrerVaeskekoncentrationView view,
            String cprNr,
            TabelVaeskekoncentrationController citratTabelCtrl,
            NormalvaerdierModel normalvaerdierModel) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;
        this.citratTabelCtrl = citratTabelCtrl;
        this.normalvaerdierModel = normalvaerdierModel; // Initialize NormalvaerdierModel

        this.view.getSaveButton().addActionListener(e -> saveData());
    }

    private void saveData() {
        try {
            String[] parameterNames = { "Calciumdosis", "Citratdosis", "Heparin" };
            String[] values = new String[view.getTextFields().length];

            for (int i = 0; i < view.getTextFields().length; i++) {
                values[i] = view.getTextFields()[i].getText();
                if (values[i].isEmpty()) {
                    view.showError("Alle felter skal udfyldes.");
                    return;
                }
            }

            StringBuilder warningMessage = new StringBuilder();
            boolean requiresDoctorNotification = false;

            // Tjek for normalværdi-afvigelser på heparin
            for (int i = 2; i < 3; i++) {
                double parsedValue = Double.parseDouble(values[i]);
                if (!normalvaerdierModel.isValueNormal(parameterNames[i], parsedValue)) {
                    double[] range = normalvaerdierModel.getRange(parameterNames[i]);
                    warningMessage.append(String.format("%s er udenfor standardoser (0 eller %.2f).\n",
                            parameterNames[i], range[1]));
                }
            }

            // Tjek for afvigelser ift. tidligere måling (calcium og citrat)
            double[] latest = model.getLatestValues(cprNr);
            double[] CadosisAendringRange = normalvaerdierModel.getRange("CadosisÆndring");
            double[] CitratdosisAendringRange = normalvaerdierModel.getRange("CitratdosisÆndring");

            if (latest != null) {
                double currentCalcium = Double.parseDouble(values[0]);
                double currentCitrat = Double.parseDouble(values[1]);

                if (Math.abs(currentCalcium - latest[0]) >= CadosisAendringRange[1]) {
                    warningMessage.append(
                            "Calciumdosis afviger med " + CadosisAendringRange[1]
                                    + " eller mere fra seneste måling.\n");
                    requiresDoctorNotification = true;
                }
                if (Math.abs(currentCitrat - latest[1]) >= CitratdosisAendringRange[1]) {
                    warningMessage.append("Citratdosis afviger med " + CitratdosisAendringRange[1]
                            + " eller mere fra seneste måling.\n");
                    requiresDoctorNotification = true;
                }
            }

            if (requiresDoctorNotification) {
                warningMessage.append("\nHusk at informere en læge.");
            }

            // Hvis der er nogen advarsler, vis samlet popup
            if (warningMessage.length() > 0) {
                int choice = view.showConfirmDialog(warningMessage.toString(), "Beslutningsstøtte",
                        new String[] { "Ændre", "Gem alligevel" }, JOptionPane.WARNING_MESSAGE);
                if (choice == 0) {
                    return; // Brugeren vil ændre værdierne
                }
            }

            // Gem data
            model.saveToDatabase(cprNr, values[0], values[1], values[2]);
            view.showConfirmDialog("Data er gemt korrekt.", "Bekræftelse", new String[] { "OK" },
                    JOptionPane.INFORMATION_MESSAGE);

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
