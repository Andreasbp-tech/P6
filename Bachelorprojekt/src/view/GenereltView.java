package view;

import controller.TabelCRRTController;
import model.TabelCRRTModel;
import view.TabelCRRTView;
import model.ValgStueModel;
import controller.TabelAGasController;
import model.TabelAGasModel;
import view.TabelAGasView;
import utilities.HeaderPanelUtil;

import javax.swing.*;
import java.awt.*;
import javax.swing.BorderFactory;

public class GenereltView {
    private JPanel panel;
    private JButton stueButton;
    private JButton patientButton;
    private JButton cprButton;
    private JButton registrerParameterButton;
    private JButton seOrdinationerButton;

    private TabelCRRTController tabelCRRTController;

    public GenereltView(int valgtStue, ValgStueModel model) {
        model.getPatientData(valgtStue);

        panel = new JPanel(new BorderLayout());

        // Header and top buttons
        JPanel headerPanel = HeaderPanelUtil.createHeaderPanel();
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Add space above buttons

        stueButton = new JButton("Stue " + valgtStue);
        patientButton = new JButton("Patient: " + model.getFornavn() + " " + model.getEfternavn());
        cprButton = new JButton("CPR: " + model.getCprNr());
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        stueButton.setFont(buttonFont);
        patientButton.setFont(buttonFont);
        cprButton.setFont(buttonFont);
        topPanel.add(stueButton);
        topPanel.add(patientButton);
        topPanel.add(cprButton);

        JPanel combinedNorth = new JPanel(new BorderLayout());
        combinedNorth.add(topPanel, BorderLayout.CENTER);
        panel.add(combinedNorth, BorderLayout.NORTH);

        // Action buttons placed above tables
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        registrerParameterButton = new JButton("Registrer parametre");
        registrerParameterButton.setFont(buttonFont);
        seOrdinationerButton = new JButton("Se ordinationer");
        seOrdinationerButton.setFont(buttonFont);
        actionPanel.add(registrerParameterButton);
        actionPanel.add(seOrdinationerButton);

        // Tables panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        // CRRT section
        JLabel crrtLabel = new JLabel("CRRT-parametre", SwingConstants.CENTER);
        crrtLabel.setFont(new Font("Arial", Font.BOLD, 16));
        crrtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(crrtLabel);

        TabelCRRTModel tabelModel = new TabelCRRTModel();
        TabelCRRTView tabelView = new TabelCRRTView();
        this.tabelCRRTController = new TabelCRRTController(tabelModel, tabelView);
        this.tabelCRRTController.updateView(model.getCprNr());
        tablePanel.add(tabelView.getTablePanel());

        // A-gas section
        JLabel agasLabel = new JLabel("A-gas", SwingConstants.CENTER);
        agasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        agasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(agasLabel);

        TabelAGasModel agasModel = new TabelAGasModel();
        TabelAGasView agasView = new TabelAGasView();
        TabelAGasController agasCtrl = new TabelAGasController(agasModel, agasView);
        agasCtrl.updateView(model.getCprNr());
        tablePanel.add(agasView.getTablePanel());

        // Combine action panel and tables into center
        JPanel centerContainer = new JPanel();
        centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
        centerContainer.add(actionPanel);
        centerContainer.add(tablePanel);
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
}
