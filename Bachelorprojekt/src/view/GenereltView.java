package view;

import controller.TabelAGasController;
import controller.TabelCRRTController;
import java.awt.*;
import javax.swing.*;
import model.TabelAGasModel;
import model.TabelCRRTModel;
import model.ValgStueModel;
import utilities.HeaderPanelUtil;

public class GenereltView {
    private JPanel panel;
    private JButton stueButton;
    private JButton patientButton;
    private JButton cprButton;
    private JButton registrerParameterButton;
    private JButton seOrdinationerButton;

    public GenereltView(int valgtStue, ValgStueModel model) {
        model.getPatientData(valgtStue);

        panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Generelt Content for Stue " + valgtStue), BorderLayout.CENTER);

        JPanel headerPanel = HeaderPanelUtil.createHeaderPanel();
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        JPanel buttonPanelTop = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        stueButton = new JButton("Stue " + valgtStue);
        stueButton.setFont(buttonFont);
        buttonPanelTop.add(stueButton);

        patientButton = new JButton("Patient: " + model.getFornavn() + " " + model.getEfternavn());
        patientButton.setFont(buttonFont);
        patientButton.setHorizontalAlignment(SwingConstants.LEFT);
        buttonPanelTop.add(patientButton);

        cprButton = new JButton("CPR: " + model.getCprNr());
        cprButton.setFont(buttonFont);
        cprButton.setHorizontalAlignment(SwingConstants.LEFT);
        buttonPanelTop.add(cprButton);

        JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        registrerParameterButton = new JButton("Registrer parametre");
        registrerParameterButton.setFont(buttonFont);
        buttonPanelBottom.add(registrerParameterButton);

        seOrdinationerButton = new JButton("Se ordinationer");
        seOrdinationerButton.setFont(buttonFont);
        buttonPanelBottom.add(seOrdinationerButton);

        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(buttonPanelTop);
        topContainer.add(buttonPanelBottom);

        panel.add(topContainer, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        JLabel crrtLabel = new JLabel("CRRT-parametre", SwingConstants.CENTER);
        crrtLabel.setFont(new Font("Arial", Font.BOLD, 16));
        crrtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(crrtLabel);

        TabelCRRTModel tabelCRRTModel = new TabelCRRTModel();
        TabelCRRTView tabelCRRTView = new TabelCRRTView();
        TabelCRRTController tabelCRRTController = new TabelCRRTController(tabelCRRTModel, tabelCRRTView);
        tabelCRRTController.updateView(model.getCprNr());

        tablePanel.add(tabelCRRTView.getTablePanel());

        JLabel agasLabel = new JLabel("A-gas", SwingConstants.CENTER);
        agasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        agasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(agasLabel);

        TabelAGasModel tabelAGasModel = new TabelAGasModel();
        TabelAGasView tabelAGasView = new TabelAGasView();
        TabelAGasController tabelAGasController = new TabelAGasController(tabelAGasModel, tabelAGasView);
        tabelAGasController.updateView(model.getCprNr());

        tablePanel.add(tabelAGasView.getTablePanel());

        panel.add(tablePanel, BorderLayout.CENTER);
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

    // Static method to create the panel
    public static JPanel createPanel(int valgtStue, ValgStueModel model) {
        GenereltView view = new GenereltView(valgtStue, model);
        return view.getPanel();
    }
}

