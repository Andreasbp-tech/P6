
package controller;

import model.RegistrerCRRTModel;
import model.RegistrerCitratmetabolismeModel;
import model.TabelCRRTModel; // Add this import statement
import model.ValgStueModel; // Add this import statement
import view.ParameterPopupView;
import view.RegistrerCRRTView;
import view.RegistrerCitratmetabolismeView;
import view.TabelCRRTView;

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
            
            // Create instances of TabelCRRTModel and TabelCRRTView
            TabelCRRTModel tabelModel = new TabelCRRTModel();
            TabelCRRTView tabelView = new TabelCRRTView();
            TabelCRRTController tabelController = new TabelCRRTController(tabelModel, tabelView);
            
            RegistrerCRRTController crrtController = new RegistrerCRRTController(model, crrtView,
                    valgStueModel.getCprNr(), tabelController); // Pass the tabelController instance
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
