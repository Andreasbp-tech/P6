package controller;

import java.awt.Window;
import javax.swing.SwingUtilities;
import model.ValgStueModel;
import view.GenereltView;
import view.ParameterPopupView;
import controller.ParameterPopupController;
import controller.TabelCRRTController;
import view.ValgStueView;
import controller.ValgStueController;

public class GenereltController {
    private GenereltView view;
    private ValgStueModel model;

    public GenereltController(int valgtStue, ValgStueModel model) {
        this.model = model;
        this.view = new GenereltView(valgtStue, model);

        // Navigate back to room selection
        view.getStueButton().addActionListener(e -> {
            // Close the current main window
            Window window = SwingUtilities.getWindowAncestor(view.getPanel());
            if (window != null) {
                window.dispose();
            }

            // Open the ValgStue screen
            ValgStueModel vsModel = new ValgStueModel();
            ValgStueView vsView = new ValgStueView();
            ValgStueController vsController = new ValgStueController(vsModel, vsView);
            vsController.showView();
        });

        // Show registration popup
        view.getRegistrerParameterButton().addActionListener(e -> {

            ParameterPopupView popupView = new ParameterPopupView();
            TabelCRRTController tcCtrl = view.getTabelCRRTController();
            TabelVaeskekoncentrationController citratCtrl = view.getTabelCitratmetabolismeController();
            ParameterPopupController popupCtrl = new ParameterPopupController(popupView, model, tcCtrl, citratCtrl);
            popupCtrl.showView();
        });
    }

    public GenereltView getView() {
        return view;
    }
}