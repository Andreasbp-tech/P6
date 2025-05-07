package view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class RegistrerVaeskekoncentrationView {
    private JFrame frame;
    private JPanel panel;
    private JLabel citratLabel;
    private JTextField[] textFields;
    private JButton saveButton;

    public RegistrerVaeskekoncentrationView() {
        frame = new JFrame("Væskekoncentrationer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 250); // Øget størrelsen for at rumme heparin feltet
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));

        citratLabel = new JLabel("Indtast væskekoncentrationer", SwingConstants.CENTER);
        citratLabel.setFont(new Font("Arial", Font.BOLD, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = { "Calciumdosis:", "Citratdosis:", "Heparin:" }; // Tilføjet Heparin
        String[] units = { "mmol/l", "mmol/l", "ie/ml" }; // Enhed for Heparin

        textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            panel.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            JTextField textField = createNumericTextField();
            textFields[i] = textField;
            panel.add(textField, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.3;
            JLabel unitLabel = new JLabel(units[i]);
            unitLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            panel.add(unitLabel, gbc);
        }

        saveButton = new JButton("Gem");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        // Set the save button as default to respond to ENTER
        frame.getRootPane().setDefaultButton(saveButton);

        frame.add(citratLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextField createNumericTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        Dimension size = new Dimension(120, 30);
        textField.setPreferredSize(size);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = textField.getText();

                if (Character.isDigit(c) || c == '\b') {
                    return; // allow digits and backspace
                }

                if (c == '.' && !text.contains(".")) {
                    return; // allow one dot
                }

                if (c == '-' && text.isEmpty()) {
                    return; // allow minus only as first character
                }

                e.consume(); // block everything else
            }
        });

        return textField;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JTextField[] getTextFields() {
        return textFields;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void close() {
        frame.dispose();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Fejl", JOptionPane.ERROR_MESSAGE);
    }

    // metode til at vise en bekræftelsesdialog
    public int showConfirmDialog(String message, String title, String[] options, int messageType) {
        return JOptionPane.showOptionDialog(
                frame,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                messageType, // ← nu kan du styre ikonen!
                null,
                options,
                options[0]);
    }

}
