package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import utilities.ProgramIcon;

public class AuthenticationView {
    private JFrame frame;
    private JTextField medarbejderIDField;
    private JButton loginButton;

    public AuthenticationView() {
        frame = new JFrame("Login");
        medarbejderIDField = new JTextField(16);
        medarbejderIDField.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        loginButton = new JButton("Login");

        // Use the universal header pane
        JPanel headerPanel = utilities.HeaderPanelUtil.createHeaderPanel();

        // Set the layout manager to GridBagLayout for better control over component
        // placement
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Indtast MedarbejderID:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(medarbejderIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        frame.add(panel);
        frame.add(headerPanel, BorderLayout.NORTH); // Add header panel to the top
        frame.setSize(600, 300); // Set a larger size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        ProgramIcon.setIconToWindow(frame); // Set icon to the window

    }

    public void show() {
        frame.setVisible(true);
    }

    public String getMedarbejderID() {
        return medarbejderIDField.getText();
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
        medarbejderIDField.addActionListener(listener); // Enable pressing the ENTER button
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void dispose() {
        frame.dispose();
    }
}
