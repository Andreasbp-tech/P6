/*package view;

import java.awt.*;
import javax.swing.*;
import view.TabelBlodproeve;
import view.TabelAGas;

public class GenereltPage {
    public static JPanel createPanel(int valgtStue) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Generelt Content for Stue " + valgtStue), BorderLayout.CENTER);

        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Fælles font til alle knapper

        // Første række knapper (Stue, Patient, CPR) – lige store og fylder hele bredden
        JPanel buttonPanelTop = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton stueButton = new JButton("Stue " + valgtStue);
        stueButton.setFont(buttonFont);
        stueButton.addActionListener(e -> ValgStue.launch());
        buttonPanelTop.add(stueButton);

        JButton patientButton = new JButton("Patient: " + ValgStue.fornavn + " " + ValgStue.efternavn);
        patientButton.setFont(buttonFont);
        patientButton.setHorizontalAlignment(SwingConstants.LEFT);
        buttonPanelTop.add(patientButton);

        JButton cprButton = new JButton("CPR: " + ValgStue.cprNr);
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

        // Add tables to separate sections
        JPanel tablePanel = new JPanel(new GridLayout(2, 1));
        tablePanel.add(TabelBlodproeve.tablePanel);
        tablePanel.add(TabelAGas.tablePanel);

        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }
}
*/

package view;

import java.awt.*;
import javax.swing.*;
import view.TabelBlodproeve;
import view.TabelAGas;

public class GenereltPage {
    public static JPanel createPanel(int valgtStue) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Generelt Content for Stue " + valgtStue), BorderLayout.CENTER);

        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Fælles font til alle knapper

        // Første række knapper (Stue, Patient, CPR) – lige store og fylder hele bredden
        JPanel buttonPanelTop = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton stueButton = new JButton("Stue " + valgtStue);
        stueButton.setFont(buttonFont);
        stueButton.addActionListener(e -> ValgStue.launch());
        buttonPanelTop.add(stueButton);

        JButton patientButton = new JButton("Patient: " + ValgStue.fornavn + " " + ValgStue.efternavn);
        patientButton.setFont(buttonFont);
        patientButton.setHorizontalAlignment(SwingConstants.LEFT);
        buttonPanelTop.add(patientButton);

        JButton cprButton = new JButton("CPR: " + ValgStue.cprNr);
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

        // Add tables to separate sections with labels
        JPanel tablePanel = new JPanel(new GridLayout(4, 1));

        JLabel crrtLabel = new JLabel("CRRT-parametre", SwingConstants.CENTER);
        crrtLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tablePanel.add(crrtLabel);
        tablePanel.add(TabelBlodproeve.tablePanel);

        JLabel agasLabel = new JLabel("A-gas", SwingConstants.CENTER);
        agasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tablePanel.add(agasLabel);
        tablePanel.add(TabelAGas.tablePanel);

        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }
}
