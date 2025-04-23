/*package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

import utilities.HeaderPanelUtil;
import utilities.DatabaseConnection;

public class ValgStueView {
    private static int valgtStue;
    public static String fornavn;
    public static String efternavn;
    public static String cprNr;

    public static void launch() {
        JFrame frame = new JFrame("Vælg stue");
        frame.setLayout(new BorderLayout());

        // Header
        // Use the universal header pane
        JPanel headerPanel = HeaderPanelUtil.createHeaderPanel();

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(10, 2, 10, 10)); // 10 rows, 2 columns, 10px gaps
        for (int i = 1; i <= 20; i++) {
            JButton stueButton = new JButton("Stue " + i);
            stueButton.setPreferredSize(new Dimension(150, 50)); // Set button size
            stueButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    valgtStue = Integer.parseInt(stueButton.getText().split(" ")[1]);
                    frame.dispose(); // Close the selection screen
                    getPatientData(valgtStue);
                    MainPage.launch(valgtStue); // Open the next screen with the selected Stue
                }
            });
            buttonsPanel.add(stueButton);
        }
        frame.add(buttonsPanel, BorderLayout.CENTER);// Add buttons panel to the center
        frame.add(headerPanel, BorderLayout.NORTH); // Add header panel to the top
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window to fill the screen
        frame.setUndecorated(false); // Remove window decorations (title bar, borders, etc.)
                                     // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static void getPatientData(int valgtStue) {
        String query = "SELECT Fornavn, Efternavn, CPR_nr FROM Patienter WHERE Stue = ?";

        boolean found = false;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, valgtStue);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                do {
                    fornavn = rs.getString("Fornavn");
                    efternavn = rs.getString("Efternavn");
                    cprNr = rs.getString("CPR_nr");

                    System.out.println("Fornavn: " + fornavn + ", Efternavn: " + efternavn + ", CPR_nr: " + cprNr);
                    found = true;
                } while (rs.next());
            }

            if (!found) {
                fornavn = " ";
                efternavn = " ";
                cprNr = " ";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int valgtStue = view.ValgStueView.getSelectedStue();
        getPatientData(valgtStue);
    }

    public static int getSelectedStue() {
        return valgtStue;
    }
}
*/

package view;

import java.awt.*;
import javax.swing.*;
import utilities.HeaderPanelUtil;

public class ValgStueView {
    private JFrame frame;
    private JPanel buttonsPanel;

    public ValgStueView() {
        frame = new JFrame("Vælg stue");
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = HeaderPanelUtil.createHeaderPanel();

        // Buttons Panel
        buttonsPanel = new JPanel(new GridLayout(10, 2, 10, 10)); // 10 rows, 2 columns, 10px gaps

        frame.add(buttonsPanel, BorderLayout.CENTER); // Add buttons panel to the center
        frame.add(headerPanel, BorderLayout.NORTH); // Add header panel to the top
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window to fill the screen
        frame.setUndecorated(false); // Remove window decorations (title bar, borders, etc.)
        frame.setLocationRelativeTo(null); // Center the frame on the screen
    }

    public void addButton(JButton button) {
        buttonsPanel.add(button);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }
}
