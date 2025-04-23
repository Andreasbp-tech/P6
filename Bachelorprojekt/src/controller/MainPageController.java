package controller;

import model.MainPageModel;
import view.MainPageView;
import view.GenereltPage;
import view.ProvesvarPage;
import view.TrendsPage;

public class MainPageController {
    private MainPageModel model;
    private MainPageView view;

    public MainPageController(MainPageModel model, MainPageView view) {
        this.model = model;
        this.view = view;

        model.loadPatientData(model.getValgtStue());

        view.addPanel(GenereltPage.createPanel(model.getValgtStue(), model), "Generelt");
        view.addPanel(ProvesvarPage.createPanel(model.getValgtStue()), "Prøvesvar");
        view.addPanel(TrendsPage.createPanel(model.getValgtStue()), "Trends");

        view.getGenereltButton().addActionListener(e -> {
            view.showPanel("Generelt");
            view.setButtonColors(view.getGenereltButton());
        });

        view.getPrøvesvarButton().addActionListener(e -> {
            view.showPanel("Prøvesvar");
            view.setButtonColors(view.getPrøvesvarButton());
        });

        view.getTrendsButton().addActionListener(e -> {
            view.showPanel("Trends");
            view.setButtonColors(view.getTrendsButton());
        });
    }

    public void showView() {
        view.show();
    }
}
