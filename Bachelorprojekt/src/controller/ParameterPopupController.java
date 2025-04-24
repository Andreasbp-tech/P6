package controller;

import view.ParameterPopupView;
import model.RegistrerCRRTModel;
import view.RegistrerCRRTView;
import controller.RegistrerCRRTController;
import model.RegistrerCitratmetabolismeModel;
import view.RegistrerCitratmetabolismeView;
import controller.RegistrerCitratmetabolismeController;
import model.ValgStueModel;

public class ParameterPopupController {
    private ParameterPopupView view;
    private ValgStueModel valgStueModel;

    public ParameterPopupController(ParameterPopupView view, ValgStueModel valgStueModel) {
        this.view = view;
        this.valgStueModel = valgStueModel;

        this.view.getCrrtButton().addActionListener(e -> {
            view.close();
            RegistrerCRRTModel model = new RegistrerCRRTModel();
            RegistrerCRRTView crrtView = new RegistrerCRRTView();
            RegistrerCRRTController crrtController = new RegistrerCRRTController(model, crrtView,
                    valgStueModel.getCprNr());
            crrtController.showView(); // Open the RegistrerCRRTView
        });
        this.view.getCitratButton().addActionListener(e -> {
            view.close();
            RegistrerCitratmetabolismeModel model = new RegistrerCitratmetabolismeModel();
            RegistrerCitratmetabolismeView citratView = new RegistrerCitratmetabolismeView();
            RegistrerCitratmetabolismeController citratController = new RegistrerCitratmetabolismeController(model,
                    citratView);
            citratController.showView(); // Open the RegistrerCitratmetabolismeView
        });
        this.view.getCancelButton().addActionListener(e -> view.close());
    }

    public void showView() {
        view.setVisible(true);
    }
}
