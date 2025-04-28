package controller;

import model.NormalvaerdierModel;
import model.RegistrerCRRTModel;
import model.RegistrerCitratmetabolismeModel;
import model.ValgStueModel;
import view.ParameterPopupView;
import view.RegistrerCRRTView;
import view.RegistrerCitratmetabolismeView;

public class ParameterPopupController {
    private ParameterPopupView view;
    private ValgStueModel valgStueModel;
    private TabelCRRTController tabelController;
    private TabelCitratmetabolismeController citratController;

    public ParameterPopupController(ParameterPopupView view,
            ValgStueModel valgStueModel,
            TabelCRRTController tabelController,
            TabelCitratmetabolismeController citratController) {
        this.view = view;
        this.valgStueModel = valgStueModel;
        this.tabelController = tabelController;
        this.citratController = citratController;

        // Create NormalvaerdierModel to be passed to the RegistrerCRRTController
        NormalvaerdierModel normalvaerdierModel = new NormalvaerdierModel();

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

        view.getCitratButton().addActionListener(e -> {
            view.close();
            RegistrerCitratmetabolismeModel model = new RegistrerCitratmetabolismeModel();
            RegistrerCitratmetabolismeView citratView = new RegistrerCitratmetabolismeView();
            RegistrerCitratmetabolismeController controller = new RegistrerCitratmetabolismeController(
                    model, citratView, valgStueModel.getCprNr(), this.citratController);
            controller.showView();
        });

        view.getCancelButton().addActionListener(e -> view.close());
    }

    public void showView() {
        view.setVisible(true);
    }
}
