package controller;

import model.TabelAGasModel;
import model.TabelBlodproveModel;
import model.ValgStueModel;
import view.TabelAGasView;
import view.TabelBlodproveView;
import view.ProvesvarView;
import view.ValgStueView;
import javax.swing.*;
import java.awt.*;
import java.awt.Window;
import javax.swing.SwingUtilities;

public class ProvesvarController {
    private ProvesvarView provesvarView;
    private TabelAGasModel agasModel;
    private TabelAGasView agasView;
    private TabelAGasController agasController;

    private TabelBlodproveModel blodModel;
    private TabelBlodproveView blodView;
    private TabelBlodproveController blodController;

    public ProvesvarController(int valgtStue, ValgStueModel model) {
        model.getPatientData(valgtStue);
        provesvarView = new ProvesvarView(valgtStue, model);

        // A-gas
        agasModel = new TabelAGasModel();
        agasView = new TabelAGasView();
        agasController = new TabelAGasController(agasModel, agasView);
        agasController.updateView(model.getCprNr());
        provesvarView.addAgasTablePanel(agasView.getTablePanel());

        // Blodprøver
        blodModel = new TabelBlodproveModel();
        blodView = new TabelBlodproveView();
        blodController = new TabelBlodproveController(blodModel, blodView);
        blodController.updateView(model.getCprNr());
        provesvarView.addBlodprovePanel(blodView.getPanel());

        // Tilføj navigation tilbage til valg af stue
        provesvarView.getStueButton().addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(provesvarView.getMainPanel());
            if (window != null) {
                window.dispose();
            }

            ValgStueModel vsModel = new ValgStueModel();
            ValgStueView vsView = new ValgStueView();
            ValgStueController vsController = new ValgStueController(vsModel, vsView);
            vsController.showView();
        });
    }

    public ProvesvarView getView() {
        return provesvarView;
    }
}
