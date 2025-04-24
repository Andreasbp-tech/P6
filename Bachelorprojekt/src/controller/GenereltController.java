package controller;

import model.ValgStueModel;
import view.GenereltView;
import view.ParameterPopupView;
import view.ValgStueView;
import view.SeOrdinationerPage;

public class GenereltController {
    private GenereltView view;
    private ValgStueModel model;

    public GenereltController(int valgtStue, ValgStueModel model) {
        this.model = model;
        this.view = new GenereltView(valgtStue, model);

        // Load patient data
        model.getPatientData(valgtStue);

        // Add action listeners
        view.getStueButton().addActionListener(e -> {
            ValgStueModel valgStueModel = new ValgStueModel();
            ValgStueView valgStueView = new ValgStueView();
            ValgStueController valgStueController = new ValgStueController(valgStueModel, valgStueView);
            valgStueController.showView(); // Start ValgStue klassen
            System.out.println("GenereltController");
        });

        view.getRegistrerParameterButton().addActionListener(e -> {
            ParameterPopupView parameterPopupView = new ParameterPopupView();
            ParameterPopupController parameterPopupController = new ParameterPopupController(parameterPopupView, model);
            parameterPopupController.showView(); // Open the ParameterPopupView
        });

        view.getSeOrdinationerButton().addActionListener(e -> SeOrdinationerPage.launch());
    }

    public GenereltView getView() {
        return view;
    }
}
