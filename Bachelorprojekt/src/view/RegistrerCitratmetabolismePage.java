package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegistrerCitratmetabolismePage {
    public static void launch() {
        JFrame citratFrame = new JFrame("Citrat Metabolisme Page");
        citratFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        citratFrame.setSize(450, 200);
        citratFrame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0)); // Add padding to the sides

        JLabel citratLabel = new JLabel("Indtast Citrat-metabolisme-parametre", SwingConstants.CENTER);
        citratLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = { "Calciumdosis:", "Citratdosis:" };
        String[] units = { "mmol/l", "mmol/l" };

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
            panel.add(textField, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.3;
            JLabel unitLabel = new JLabel(units[i]);
            unitLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            panel.add(unitLabel, gbc);
        }

        JButton saveButton = new JButton("Gem");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the values to the database
                saveToDatabase();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        citratFrame.add(citratLabel, BorderLayout.NORTH);
        citratFrame.add(panel, BorderLayout.CENTER);
        citratFrame.add(buttonPanel, BorderLayout.SOUTH);
        citratFrame.setVisible(true);
    }

    private static JTextField createNumericTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size and style
        Dimension size = new Dimension(120, 30); // Set preferred width and height
        textField.setPreferredSize(size);
        textField.setMinimumSize(size);
        textField.setMaximumSize(size);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume(); // Ignore non-digit characters
                }
            }
        });
        return textField;
    }

    private static void saveToDatabase() {
        // Implement the database save logic here
        System.out.println("Values saved to the database.");
    }

    public static void main(String[] args) {
        launch();
    }
}
