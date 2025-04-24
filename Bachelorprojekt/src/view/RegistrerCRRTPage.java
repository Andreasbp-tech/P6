package view;

import utilities.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrerCRRTPage {
    public static void launch() {
        JFrame crrtFrame = new JFrame("CRRT Page");
        crrtFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        crrtFrame.setSize(400, 430);
        crrtFrame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0)); // Add padding to the sides

        JLabel crrtLabel = new JLabel("Indtast CRRT-parametre", SwingConstants.CENTER);
        crrtLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = { "Dialysatflow:", "Blodflow:", "Væsketræk:", "Indløbstryk:", "Returtryk:", "Præfiltertryk:",
                "Heparin:" };
        String[] units = { "ml/time", "ml/time", "ml/time", "mmHg", "mmHg", "mmHg", "500 ie/ml" };

        JTextField[] textFields = new JTextField[labels.length];

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

        JButton saveButton = new JButton("Gem");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dialysatflow = textFields[0].getText();
                String blodflow = textFields[1].getText();
                String vaesketraek = textFields[2].getText();
                String indloebstryk = textFields[3].getText();
                String returtryk = textFields[4].getText();
                String praefiltertryk = textFields[5].getText();
                String heparin = textFields[6].getText();
                // Save the values to the database
                saveToDatabase(dialysatflow, blodflow, vaesketraek, indloebstryk, returtryk, praefiltertryk, heparin);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        crrtFrame.add(crrtLabel, BorderLayout.NORTH);
        crrtFrame.add(panel, BorderLayout.CENTER);
        crrtFrame.add(buttonPanel, BorderLayout.SOUTH);
        crrtFrame.setVisible(true);
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

    private static void saveToDatabase(String dialysatflow, String blodflow, String vaesketraek, String indloebstryk,
            String returtryk, String praefiltertryk, String heparin) {
        String query = "INSERT INTO CRRT (dialysatflow, blodflow, væsketræk, indløbstryk, returtryk, præfiltertryk, heparin) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dialysatflow);
            pstmt.setString(2, blodflow);
            pstmt.setString(3, vaesketraek);
            pstmt.setString(4, indloebstryk);
            pstmt.setString(5, returtryk);
            pstmt.setString(6, praefiltertryk);
            pstmt.setString(7, heparin);

            pstmt.executeUpdate();
            System.out.println("Values saved to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
