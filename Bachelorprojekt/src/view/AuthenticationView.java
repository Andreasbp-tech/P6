/*package view;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import utilities.DatabaseConnection;
import utilities.HeaderPanelUtil;

public class AuthenticationView {
    private static String loggedInMedarbejderID;

    public static void launch() {
        JFrame frame = new JFrame("Login");
        JTextField medarbejderIDField = new JTextField(16);
        medarbejderIDField.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        // Use the universal header pane
        JPanel headerPanel = HeaderPanelUtil.createHeaderPanel();
      
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String medarbejderID = medarbejderIDField.getText();
                if (isValidMedarbejderID(medarbejderID)) {
                    logUserLogin(medarbejderID); // Log the user login
                    ValgStue.launch(); // If login is approved, go to ValgStue
                    frame.dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(frame, "Forkert MedarbejderID!");
                }
            }
        });

        // Enable pressing the ENTER button
        medarbejderIDField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick(); // Simulate button click
            }
        });

        // Set the layout manager to GridBagLayout for better control over component placement
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
        frame.setVisible(true);
    }

    private static boolean isValidMedarbejderID(String medarbejderID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Medarbejdere WHERE MedarbejderID = ?")) {
            stmt.setString(1, medarbejderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private static void logUserLogin(String medarbejderID) {
        loggedInMedarbejderID = medarbejderID; // Set the logged-in user ID
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT Rolle, Efternavn FROM Medarbejdere WHERE MedarbejderID = ?")) {
            stmt.setString(1, medarbejderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String rolle = rs.getString("Rolle");
                String efternavn = rs.getString("Efternavn");

                try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO LoginHistorie (MedarbejderID, Rolle, Efternavn, LoginTime) VALUES (?, ?, ?, ?)")) {
                    insertStmt.setString(1, medarbejderID);
                    insertStmt.setString(2, rolle);
                    insertStmt.setString(3, efternavn);
                    insertStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static String getLoggedInMedarbejderID() {
        return loggedInMedarbejderID;
    }
}
*/

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
