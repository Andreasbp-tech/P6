/*package controller;

import model.MainPageModel;
import view.MainPageView;
import view.GenereltView;
import view.ProvesvarPage;
import view.TrendsPage;

public class MainPageController {
    private MainPageModel model;
    private MainPageView view;

    public MainPageController(MainPageModel model, MainPageView view) {
        this.model = model;
        this.view = view;

        model.loadPatientData(model.getValgtStue());

        view.addPanel(GenereltView.createPanel(model.getValgtStue(), model), "Generelt");
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
*/

package controller;

import model.MainPageModel;
import view.MainPageView;
import view.GenereltView;
import view.ProvesvarPage;
import view.TrendsPage;

public class MainPageController {
    private MainPageModel model;
    private MainPageView view;

    public MainPageController(MainPageModel model, MainPageView view) {
        this.model = model;
        this.view = view;

        model.loadPatientData(model.getValgtStue());

        // Instantiate GenereltController and add its view to the main page
        GenereltController genereltController = new GenereltController(model.getValgtStue(), model);
        view.addPanel(genereltController.getView().getPanel(), "Generelt");

        view.addPanel(ProvesvarPage.createPanel(model.getValgtStue()), "Prøvesvar");
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
