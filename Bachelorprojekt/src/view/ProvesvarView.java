package view;

import javax.swing.*;
import java.awt.*;
import model.ValgStueModel;

public class ProvesvarView {
    private JPanel mainPanel;
    private JPanel agasSection;
    private JLabel agasLabel;
    private JPanel blodproveSection;
    private JLabel blodproveLabel;

    private JButton stueButton;
    private JButton patientButton;
    private JButton cprButton;

    public ProvesvarView(int valgtStue, ValgStueModel model) {
        // Hent patientdata
        model.getPatientData(valgtStue);

        mainPanel = new JPanel(new BorderLayout());

        // Øverste knapper (stue, navn, cpr)
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
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

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // A-gas sektion

    }

    public void addAgasTablePanel(JPanel agasPanel) {
        agasSection = new JPanel(new BorderLayout());
        agasSection.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0)); // Øger afstand fra top
        agasLabel = new JLabel("A-gas og maskinprøver", SwingConstants.CENTER);
        agasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        agasSection.add(agasLabel, BorderLayout.NORTH);
        mainPanel.add(agasSection, BorderLayout.CENTER);
        agasPanel.setPreferredSize(new Dimension(800, 400));
        agasSection.add(agasPanel, BorderLayout.CENTER);
    }

    public void addBlodprovePanel(JPanel blodPanel) {
        blodproveSection = new JPanel(new BorderLayout());
        blodproveSection.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Øger afstand fra agas og bund
        blodproveLabel = new JLabel("Blodprøver", SwingConstants.CENTER);
        blodproveLabel.setFont(new Font("Arial", Font.BOLD, 16));
        blodproveSection.add(blodproveLabel, BorderLayout.NORTH);
        blodPanel.setPreferredSize(new Dimension(800, 200));
        blodproveSection.add(blodPanel, BorderLayout.CENTER);
        mainPanel.add(blodproveSection, BorderLayout.SOUTH);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getStueButton() {
        return stueButton;
    }

    public JButton getPatientButton() {
        return patientButton;
    }

    public JButton getCprButton() {
        return cprButton;
    }
}
