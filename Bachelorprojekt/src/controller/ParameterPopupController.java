package controller;

import view.ParameterPopupView;
import view.RegistrerCitratmetabolismePage;
import model.RegistrerCRRTModel;
import view.RegistrerCRRTView;
import controller.RegistrerCRRTController;

public class ParameterPopupController {
    private ParameterPopupView view;

    public ParameterPopupController(ParameterPopupView view) {
        this.view = view;
        this.view.getCrrtButton().addActionListener(e -> {
            view.close();
            RegistrerCRRTModel model = new RegistrerCRRTModel();
            RegistrerCRRTView crrtView = new RegistrerCRRTView();
            RegistrerCRRTController crrtController = new RegistrerCRRTController(model, crrtView);
            crrtController.showView(); // Open the RegistrerCRRTView
        });
        this.view.getCitratButton().addActionListener(e -> {
            view.close();
            RegistrerCitratmetabolismePage.launch();
        });
        this.view.getCancelButton().addActionListener(e -> view.close());
    }

    public void showView() {
        view.setVisible(true);
    }
}
