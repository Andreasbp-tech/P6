package controller;

import model.RegistrerCRRTModel;
import view.RegistrerCRRTView;

public class RegistrerCRRTController {
    private RegistrerCRRTModel model;
    private RegistrerCRRTView view;

    public RegistrerCRRTController(RegistrerCRRTModel model, RegistrerCRRTView view) {
        this.model = model;
        this.view = view;

        this.view.getSaveButton().addActionListener(e -> {
            String dialysatflow = view.getTextFields()[0].getText();
            String blodflow = view.getTextFields()[1].getText();
            String vaesketraek = view.getTextFields()[2].getText();
            String indloebstryk = view.getTextFields()[3].getText();
            String returtryk = view.getTextFields()[4].getText();
            String praefiltertryk = view.getTextFields()[5].getText();
            String heparin = view.getTextFields()[6].getText();
            model.saveToDatabase(dialysatflow, blodflow, vaesketraek, indloebstryk, returtryk, praefiltertryk, heparin);
        });
    }

    public void showView() {
        view.setVisible(true);
    }
}
