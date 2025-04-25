package controller;

import model.RegistrerCitratmetabolismeModel;
import view.RegistrerCitratmetabolismeView;

public class RegistrerCitratmetabolismeController {
    private RegistrerCitratmetabolismeModel model;
    private RegistrerCitratmetabolismeView view;

    private String cprNr;

    public RegistrerCitratmetabolismeController(RegistrerCitratmetabolismeModel model,
            RegistrerCitratmetabolismeView view, String cprNr) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;

        this.view.getSaveButton().addActionListener(e -> {
            String calciumdosis = view.getTextFields()[0].getText();
            String citratdosis = view.getTextFields()[1].getText();

            // Validate inputs
            if (calciumdosis.isEmpty() || citratdosis.isEmpty()) {
                view.showError("Alle felter skal udfyldes.");
                return;
            }
            model.saveToDatabase(cprNr, calciumdosis, citratdosis);
            view.close();
        });
    }

    public void showView() {
        view.setVisible(true);
    }
}
