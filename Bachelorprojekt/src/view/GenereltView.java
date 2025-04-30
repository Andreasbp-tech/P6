package view;

import controller.TabelAGasController;
import controller.TabelCRRTController;
import controller.TabelCitratmetabolismeController;
import java.awt.*;
import javax.swing.*;
import model.TabelAGasModel;
import model.TabelCRRTModel;
import model.TabelCitratmetabolismeModel;
import model.ValgStueModel;
import utilities.HeaderPanelUtil;

public class GenereltView {
    private JPanel panel;
    private JButton stueButton;
    private JButton patientButton;
    private JButton cprButton;
    private JButton registrerParameterButton;
    private JButton seOrdinationerButton;

    private TabelCRRTController tabelCRRTController;
    private TabelCitratmetabolismeController tabelCitratmetabolismeController;

    public GenereltView(int valgtStue, ValgStueModel model) {
        model.getPatientData(valgtStue);
        panel = new JPanel(new BorderLayout());

        // Skærm dimensioner
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int) screenSize.getHeight();
        int screenWidth = (int) screenSize.getWidth();

        // Header og topknapper
        JPanel headerPanel = HeaderPanelUtil.createHeaderPanel();
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        stueButton = new JButton("Stue " + valgtStue);
        stueButton.setFont(buttonFont);
        patientButton = new JButton("Patient: " + model.getFornavn() + " " + model.getEfternavn());
        patientButton.setFont(buttonFont);
        cprButton = new JButton("CPR: " + model.getCprNr());
        cprButton.setFont(buttonFont);
        topPanel.add(stueButton);
        topPanel.add(patientButton);
        topPanel.add(cprButton);

        JPanel combinedNorth = new JPanel(new BorderLayout());
        combinedNorth.add(topPanel, BorderLayout.CENTER);
        panel.add(combinedNorth, BorderLayout.NORTH);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        registrerParameterButton = new JButton("Registrer parametre");
        registrerParameterButton.setFont(buttonFont);
        seOrdinationerButton = new JButton("Se ordinationer");
        seOrdinationerButton.setFont(buttonFont);
        actionPanel.add(registrerParameterButton);
        actionPanel.add(seOrdinationerButton);

        // Container til tabellerne
        JPanel tablesContainer = new JPanel();
        tablesContainer.setLayout(new BoxLayout(tablesContainer, BoxLayout.Y_AXIS));
        tablesContainer.setPreferredSize(new Dimension(screenWidth, screenHeight - 200)); // tilpasset skærmhøjde

        // --- CRRT Sektion ---
        JPanel crrtSection = new JPanel(new BorderLayout());
        JLabel crrtLabel = new JLabel("CRRT-parametre", SwingConstants.CENTER);
        crrtLabel.setFont(new Font("Arial", Font.BOLD, 16));
        crrtSection.add(crrtLabel, BorderLayout.NORTH);
        TabelCRRTModel tabelModel = new TabelCRRTModel();
        TabelCRRTView tabelView = new TabelCRRTView();
        this.tabelCRRTController = new TabelCRRTController(tabelModel, tabelView);
        this.tabelCRRTController.updateView(model.getCprNr());
        JPanel crrtTablePanel = tabelView.getTablePanel();
        crrtTablePanel.setPreferredSize(new Dimension(screenWidth, (int) (screenHeight * 0.4)));
        crrtSection.add(crrtTablePanel, BorderLayout.CENTER);
        tablesContainer.add(crrtSection);

        // --- Citrat Sektion --- //rettet til væskekoncentrationer
        JPanel citratSection = new JPanel(new BorderLayout());
        JLabel citratLabel = new JLabel("Væskekoncentrationer", SwingConstants.CENTER);
        citratLabel.setFont(new Font("Arial", Font.BOLD, 16));
        citratSection.add(citratLabel, BorderLayout.NORTH);
        TabelCitratmetabolismeModel citratModel = new TabelCitratmetabolismeModel();
        TabelCitratmetabolismeView citratView = new TabelCitratmetabolismeView();
        this.tabelCitratmetabolismeController = new TabelCitratmetabolismeController(citratModel, citratView);
        this.tabelCitratmetabolismeController.updateView(model.getCprNr());
        JPanel citratTablePanel = citratView.getTablePanel();
        citratTablePanel.setPreferredSize(new Dimension(screenWidth, (int) (screenHeight * 0.25)));
        citratSection.add(citratTablePanel, BorderLayout.CENTER);
        tablesContainer.add(citratSection);

        // --- A-gas Sektion --- // tilføjet maskinprøver
        JPanel agasSection = new JPanel(new BorderLayout());
        JLabel agasLabel = new JLabel("A-gas og maskinprøver", SwingConstants.CENTER);
        agasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        agasSection.add(agasLabel, BorderLayout.NORTH);
        TabelAGasModel agasModel = new TabelAGasModel();
        TabelAGasView agasView = new TabelAGasView();
        TabelAGasController agasCtrl = new TabelAGasController(agasModel, agasView);
        agasCtrl.updateView(model.getCprNr());
        JPanel agasTablePanel = agasView.getTablePanel();
        agasTablePanel.setPreferredSize(new Dimension(screenWidth, (int) (screenHeight * 0.35)));
        agasSection.add(agasTablePanel, BorderLayout.CENTER);
        tablesContainer.add(agasSection);

        // Combine midterpanel
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(actionPanel, BorderLayout.NORTH);
        centerContainer.add(tablesContainer, BorderLayout.CENTER);
        panel.add(centerContainer, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getStueButton() {
        return stueButton;
    }

    public JButton getRegistrerParameterButton() {
        return registrerParameterButton;
    }

    public JButton getSeOrdinationerButton() {
        return seOrdinationerButton;
    }

    public TabelCRRTController getTabelCRRTController() {
        return tabelCRRTController;
    }

    public TabelCitratmetabolismeController getTabelCitratmetabolismeController() {
        return tabelCitratmetabolismeController;
    }
}
