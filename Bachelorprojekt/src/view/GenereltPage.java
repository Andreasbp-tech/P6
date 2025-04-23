package view;

import java.awt.*;
import javax.swing.*;

import model.ValgStueModel;
import view.TabelBlodproeve;
import view.TabelAGas;
import view.TabelCRRT;
import view.ValgStueView;
import view.RegistrerParametrePage;
import view.SeOrdinationerPage;
import controller.ValgStueController;
import utilities.HeaderPanelUtil;

public class GenereltPage {
    public static JPanel createPanel(int valgtStue, ValgStueModel model) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Generelt Content for Stue " + valgtStue), BorderLayout.CENTER);

        // Use the universal header pane
        JPanel headerPanel = utilities.HeaderPanelUtil.createHeaderPanel();

        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Fælles font til alle knapper

        // Første række knapper (Stue, Patient, CPR) – lige store og fylder hele bredden
        JPanel buttonPanelTop = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton stueButton = new JButton("Stue " + valgtStue);
        stueButton.setFont(buttonFont);
        stueButton.addActionListener(e -> {
            ValgStueModel valgStueModel = new ValgStueModel();
            ValgStueView valgStueView = new ValgStueView();
            ValgStueController valgStueController = new ValgStueController(valgStueModel, valgStueView);
            valgStueController.showView(); // Start ValgStue klassen
        });
        buttonPanelTop.add(stueButton);

        JButton patientButton = new JButton("Patient: " + model.getFornavn() + " " + model.getEfternavn());
        patientButton.setFont(buttonFont);
        patientButton.setHorizontalAlignment(SwingConstants.LEFT);
        buttonPanelTop.add(patientButton);

        JButton cprButton = new JButton("CPR: " + model.getCprNr());
        cprButton.setFont(buttonFont);
        cprButton.setHorizontalAlignment(SwingConstants.LEFT);
        buttonPanelTop.add(cprButton);

        // Anden række knapper
        JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton RegistrerParameterButton = new JButton("Registrer parametre");
        RegistrerParameterButton.setFont(buttonFont);
        RegistrerParameterButton.addActionListener(e -> RegistrerParametrePage.launch());
        buttonPanelBottom.add(RegistrerParameterButton);

        JButton SeOrdinationerButton = new JButton("Se ordinationer");
        SeOrdinationerButton.setFont(buttonFont);
        SeOrdinationerButton.addActionListener(e -> SeOrdinationerPage.launch());
        buttonPanelBottom.add(SeOrdinationerButton);

        // Kombiner begge rækker i én vertikal container
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(buttonPanelTop);
        topContainer.add(buttonPanelBottom);

        panel.add(topContainer, BorderLayout.NORTH);

        // Add tables to separate sections with labels using BoxLayout
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        JLabel crrtLabel = new JLabel("CRRT-parametre", SwingConstants.CENTER);
        crrtLabel.setFont(new Font("Arial", Font.BOLD, 16));
        crrtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(crrtLabel);
        tablePanel.add(TabelCRRT.tablePanel);

        JLabel agasLabel = new JLabel("A-gas", SwingConstants.CENTER);
        agasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        agasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(agasLabel);
        tablePanel.add(TabelAGas.tablePanel);

        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    public static void launch(int valgtStue, ValgStueModel model) {
        JFrame frame = new JFrame("Generelt Page");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(createPanel(valgtStue, model));
        frame.setVisible(true);
    }
}
