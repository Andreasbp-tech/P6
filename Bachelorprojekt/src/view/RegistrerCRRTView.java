package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegistrerCRRTView {
    private JFrame frame;
    private JPanel panel;
    private JLabel crrtLabel;
    private JTextField[] textFields;
    private JButton saveButton;

    public RegistrerCRRTView() {
        frame = new JFrame("CRRT Page");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 430);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));

        crrtLabel = new JLabel("Indtast CRRT-parametre", SwingConstants.CENTER);
        crrtLabel.setFont(new Font("Arial", Font.BOLD, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = { "Dialysatflow:", "Blodflow:", "Væsketræk:", "Indløbstryk:", "Returtryk:", "Præfiltertryk:",
                "Heparin:" };
        String[] units = { "ml/time", "ml/time", "ml/time", "mmHg", "mmHg", "mmHg", "500 ie/ml" };

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

        frame.add(crrtLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextField createNumericTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        Dimension size = new Dimension(120, 30);
        textField.setPreferredSize(size);
        textField.setMinimumSize(size);
        textField.setMaximumSize(size);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
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
}
