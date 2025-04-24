package controller;

import view.ParameterPopupView;
import view.RegistrerCRRTPage;
import view.RegistrerCitratmetabolismePage;

public class ParameterPopupController {
    private ParameterPopupView view;

    public ParameterPopupController(ParameterPopupView view) {
        this.view = view;
        this.view.getCrrtButton().addActionListener(e -> {
            view.close();
            RegistrerCRRTPage.launch();
        });
        this.view.getCitratButton().addActionListener(e -> {
            view.close();
            RegistrerCitratmetabolismePage.launch();
        });
        this.view.getCancelButton().addActionListener(e -> view.close());
    }

    public void showView() {
        view.setVisible(true);
    }
}
