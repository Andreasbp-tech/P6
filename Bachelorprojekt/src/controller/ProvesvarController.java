package controller;

import model.TabelAGasModel;
import model.TabelBlodproveModel;
import view.TabelAGasView;
import view.TabelBlodproveView;
import view.ProvesvarView;

public class ProvesvarController {
    private ProvesvarView provesvarView;
    private TabelAGasModel agasModel;
    private TabelAGasView agasView;
    private TabelAGasController agasController;

    private TabelBlodproveModel blodModel;
    private TabelBlodproveView blodView;
    private TabelBlodproveController blodController;

    public ProvesvarController(String cprNr) {
        provesvarView = new ProvesvarView();

        // A-gas
        agasModel = new TabelAGasModel();
        agasView = new TabelAGasView();
        agasController = new TabelAGasController(agasModel, agasView);

        agasController.updateView(cprNr);

        provesvarView.addAgasTablePanel(agasView.getTablePanel());

        // Blodprøver
        blodModel = new TabelBlodproveModel();
        blodView = new TabelBlodproveView();
        blodController = new TabelBlodproveController(blodModel, blodView);
        blodController.updateView(cprNr);
        provesvarView.addBlodprovePanel(blodView.getPanel());
    }

    public ProvesvarView getView() {
        return provesvarView;
    }
}
