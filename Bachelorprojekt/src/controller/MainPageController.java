package controller;

import model.MainPageModel;
import view.MainPageView;
import view.GenereltView;
import view.TrendsPage;

public class MainPageController {
    private MainPageModel model;
    private MainPageView view;

    public MainPageController(MainPageModel model, MainPageView view) {
        this.model = model;
        this.view = view;

        model.loadPatientData(model.getValgtStue());

        // Generelt
        GenereltController genereltController = new GenereltController(model.getValgtStue(), model);
        view.addPanel(genereltController.getView().getPanel(), "Generelt");

        // Prøvesvar
        ProvesvarController provesvarController = new ProvesvarController(model.getValgtStue(), model);
        view.addPanel(provesvarController.getView().getMainPanel(), "Prøvesvar");

        // Trends
        view.addPanel(TrendsPage.createPanel(model.getValgtStue()), "Trends");

        view.getGenereltButton().addActionListener(e -> {
            System.out.println("Generelt button clicked");
            view.showPanel("Generelt");
            view.setButtonColors(view.getGenereltButton());
        });

        view.getPrøvesvarButton().addActionListener(e -> {
            System.out.println("Prøvesvar button clicked");
            view.showPanel("Prøvesvar");
            view.setButtonColors(view.getPrøvesvarButton());
        });

        view.getTrendsButton().addActionListener(e -> {
            System.out.println("Trends button clicked");
            view.showPanel("Trends");
            view.setButtonColors(view.getTrendsButton());
        });
    }

    public void showView() {
        view.show();
    }
}
