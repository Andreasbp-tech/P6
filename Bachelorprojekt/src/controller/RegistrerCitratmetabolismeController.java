package controller;

import model.RegistrerCitratmetabolismeModel;
import view.RegistrerCitratmetabolismeView;

public class RegistrerCitratmetabolismeController {
    private RegistrerCitratmetabolismeModel model;
    private RegistrerCitratmetabolismeView view;

    public RegistrerCitratmetabolismeController(RegistrerCitratmetabolismeModel model,
            RegistrerCitratmetabolismeView view) {
        this.model = model;
        this.view = view;

        this.view.getSaveButton().addActionListener(e -> {
            String calciumdosis = view.getTextFields()[0].getText();
            String citratdosis = view.getTextFields()[1].getText();
            model.saveToDatabase(calciumdosis, citratdosis);
        });
    }

    public void showView() {
        view.setVisible(true);
    }
}
