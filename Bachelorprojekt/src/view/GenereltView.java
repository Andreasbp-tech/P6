package view;

import controller.TabelCRRTController;
import controller.TabelCitratmetabolismeController;
import controller.TabelAGasController;
import model.TabelCRRTModel;
import model.TabelCitratmetabolismeModel;
import model.TabelAGasModel;
import model.ValgStueModel;
import utilities.HeaderPanelUtil;
import view.TabelCRRTView;
import view.TabelCitratmetabolismeView;
import view.TabelAGasView;

import javax.swing.*;
import java.awt.*;

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

        // Header and top buttons with spacing
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
        // combinedNorth.add(headerPanel, BorderLayout.NORTH);
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

        // Tables container with weighted rows
        JPanel tablesContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;

        // CRRT section (weighty = 3)
        gbc.gridy = 0;
        gbc.weighty = 2.4;
        JPanel crrtSection = new JPanel(new BorderLayout());
        JLabel crrtLabel = new JLabel("CRRT-parametre", SwingConstants.CENTER);
        crrtLabel.setFont(new Font("Arial", Font.BOLD, 16));
        crrtSection.add(crrtLabel, BorderLayout.NORTH);
        TabelCRRTModel tabelModel = new TabelCRRTModel();
        TabelCRRTView tabelView = new TabelCRRTView();
        this.tabelCRRTController = new TabelCRRTController(tabelModel, tabelView);
        this.tabelCRRTController.updateView(model.getCprNr());
        crrtSection.add(tabelView.getTablePanel(), BorderLayout.CENTER);
        tablesContainer.add(crrtSection, gbc);

        // Citrat section (weighty = 1)
        gbc.gridy = 1;
        gbc.weighty = 0.7;
        JPanel citratSection = new JPanel(new BorderLayout());
        JLabel citratLabel = new JLabel("Citratmetabolisme", SwingConstants.CENTER);
        citratLabel.setFont(new Font("Arial", Font.BOLD, 16));
        citratSection.add(citratLabel, BorderLayout.NORTH);
        TabelCitratmetabolismeModel citratModel = new TabelCitratmetabolismeModel();
        TabelCitratmetabolismeView citratView = new TabelCitratmetabolismeView();
        this.tabelCitratmetabolismeController = new TabelCitratmetabolismeController(citratModel, citratView);
        this.tabelCitratmetabolismeController.updateView(model.getCprNr());
        citratSection.add(citratView.getTablePanel(), BorderLayout.CENTER);
        tablesContainer.add(citratSection, gbc);

        // A-gas section (weighty = 1)
        gbc.gridy = 2;
        gbc.weighty = 1.7;
        JPanel agasSection = new JPanel(new BorderLayout());
        JLabel agasLabel = new JLabel("A-gas", SwingConstants.CENTER);
        agasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        agasSection.add(agasLabel, BorderLayout.NORTH);
        TabelAGasModel agasModel = new TabelAGasModel();
        TabelAGasView agasView = new TabelAGasView();
        TabelAGasController agasCtrl = new TabelAGasController(agasModel, agasView);
        agasCtrl.updateView(model.getCprNr());
        agasSection.add(agasView.getTablePanel(), BorderLayout.CENTER);
        tablesContainer.add(agasSection, gbc);

        // Combine actionPanel and tablesContainer
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
