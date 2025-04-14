import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AuthenticationScreen {
    public static void launch() {
        JFrame frame = new JFrame("Authentication Screen");
        JTextField medarbejderIDField = new JTextField(16);
        medarbejderIDField.setPreferredSize(new Dimension(200, 30)); // Set preferred size

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String medarbejderID = medarbejderIDField.getText();
                if (medarbejderID.length() == 10 && isValidMedarbejderID(medarbejderID)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    // Proceed to the homescreen
                } else {
                    JOptionPane.showMessageDialog(frame, "Forkert MedarbejderID!");
                }
            }
        });

        //Gør dewt muligt at trykke på ENTER knappen
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
}
