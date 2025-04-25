package controller;

import model.ValgStueModel;
import view.ParameterPopupView;
import model.RegistrerCRRTModel;
import view.RegistrerCRRTView;
import controller.RegistrerCRRTController;
import controller.TabelCRRTController;

public class ParameterPopupController {
    private ParameterPopupView view;
    private ValgStueModel valgStueModel;
    private TabelCRRTController tabelController;

    public ParameterPopupController(ParameterPopupView view,
            ValgStueModel valgStueModel,
            TabelCRRTController tabelController) {
        this.view = view;
        this.valgStueModel = valgStueModel;
        this.tabelController = tabelController;

        view.getCrrtButton().addActionListener(e -> {
            view.close();
            RegistrerCRRTModel model = new RegistrerCRRTModel();
            RegistrerCRRTView crrtView = new RegistrerCRRTView();
            RegistrerCRRTController crrtCtrl = new RegistrerCRRTController(
                    model, crrtView, valgStueModel.getCprNr(), this.tabelController);
            crrtCtrl.showView();
        });

        view.getCitratButton().addActionListener(e -> {
            view.close();
            // existing citrat logic...
        });

        view.getCancelButton().addActionListener(e -> view.close());
    }

    public void showView() {
        view.setVisible(true);
    }
}
