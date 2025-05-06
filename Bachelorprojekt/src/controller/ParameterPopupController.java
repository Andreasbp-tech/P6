package controller;

import model.NormalvaerdierModel;
import model.RegistrerCRRTModel;
import model.RegistrerVaeskekoncentrationModel;
import model.ValgStueModel;
import view.ParameterPopupView;
import view.RegistrerCRRTView;
import view.RegistrerVaeskekoncentrationView;

public class ParameterPopupController {
    private ParameterPopupView view;
    private ValgStueModel valgStueModel;
    private TabelCRRTController tabelController;
    private TabelVaeskekoncentrationController citratController;

    public ParameterPopupController(ParameterPopupView view,
            ValgStueModel valgStueModel,
            TabelCRRTController tabelController,
            TabelVaeskekoncentrationController citratController) {
        this.view = view;
        this.valgStueModel = valgStueModel;
        this.tabelController = tabelController;
        this.citratController = citratController;

        // Create NormalvaerdierModel to be passed to the RegistrerCRRTController
        NormalvaerdierModel normalvaerdierModel = new NormalvaerdierModel();

        // Handle CRRT button
        view.getCrrtButton().addActionListener(e -> {
            view.close();
            // Pass the NormalvaerdierModel to RegistrerCRRTModel constructor
            RegistrerCRRTModel model = new RegistrerCRRTModel(normalvaerdierModel); // Pass NormalvaerdierModel
            RegistrerCRRTView crrtView = new RegistrerCRRTView();
            // Pass the NormalvaerdierModel to RegistrerCRRTController
            RegistrerCRRTController crrtCtrl = new RegistrerCRRTController(
                    model, crrtView, valgStueModel.getCprNr(), this.tabelController, normalvaerdierModel);
            crrtCtrl.showView();
        });

        // Handle Citrat button
        view.getCitratButton().addActionListener(e -> {
            view.close();
            // Create the model and view for CitratMetabolisme
            RegistrerVaeskekoncentrationModel model = new RegistrerVaeskekoncentrationModel();
            RegistrerVaeskekoncentrationView citratView = new RegistrerVaeskekoncentrationView();
            // Pass the NormalvaerdierModel to RegistrerCitratmetabolismeController
            RegistrerVaeskekoncentrationController controller = new RegistrerVaeskekoncentrationController(
                    model, citratView, valgStueModel.getCprNr(), this.citratController, normalvaerdierModel); // Pass
                                                                                                              // NormalvaerdierModel
            controller.showView();
        });

        // Handle cancel button
        view.getCancelButton().addActionListener(e -> view.close());
    }

    public void showView() {
        view.setVisible(true);
    }
}
