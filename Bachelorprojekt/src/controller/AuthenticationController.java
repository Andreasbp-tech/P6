package controller;

import model.MedarbejderModel;
import model.ValgStueModel;
import view.AuthenticationView;
import view.ValgStueView; // Import ValgStue class
import java.awt.event.ActionListener; // Import ActionListener

public class AuthenticationController {
    private MedarbejderModel model;
    private AuthenticationView view;

    public AuthenticationController(MedarbejderModel model, AuthenticationView view) {
        this.model = model;
        this.view = view;

        this.view.addLoginListener(e -> handleLogin());
    }

    private void handleLogin() {
        String medarbejderID = view.getMedarbejderID();
        if (model.isValidMedarbejderID(medarbejderID)) {
            model.logUserLogin(medarbejderID);
            // Gå til næste side
            ValgStueModel valgStueModel = new ValgStueModel();
            ValgStueView valgStueView = new ValgStueView();
            ValgStueController valgStueController = new ValgStueController(valgStueModel, valgStueView);
            valgStueController.showView(); // Start ValgStue klassen
            view.dispose(); // Luk login vinduet
        } else {
            view.showErrorMessage("Forkert MedarbejderID!");
        }
    }
}
