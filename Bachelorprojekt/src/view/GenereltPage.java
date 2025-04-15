package view;
import java.awt.*;
import javax.swing.*;

public class GenereltPage {
    public static JPanel createPanel(int valgtStue) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Generelt Content for Stue " + valgtStue), BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 0, 0); // Add top margin to move the grid down
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Adjust font size as needed

        JButton stueButton = new JButton("Stue " + valgtStue);
        stueButton.setFont(buttonFont); // Set font for "Stue" button
        stueButton.addActionListener(e -> ValgStue.launch()); // Launch ValgStue on click
        buttonPanel.add(stueButton, gbc);

        gbc.gridx = 1;
        JButton patientButton = new JButton("Patient:");
        patientButton.setFont(buttonFont); // Set font for "Patient" button
        patientButton.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        buttonPanel.add(patientButton, gbc);

        gbc.gridx = 2;
        JButton cprButton = new JButton("CPR:");
        cprButton.setFont(buttonFont); // Set font for "CPR" button
        cprButton.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        buttonPanel.add(cprButton, gbc);

        panel.add(buttonPanel, BorderLayout.NORTH);

        // Add more components and functionality as needed
        return panel;
    }
}
