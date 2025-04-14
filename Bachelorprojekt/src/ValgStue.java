import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ValgStue {
    private static int valgtStue;

    public static void launch() {
        JFrame frame = new JFrame("ICUview");
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
                    JOptionPane.showMessageDialog(frame, "Du har valgt stue " + valgtStue);
                    frame.dispose(); // Close the selection screen
                    //NextScreen.launch(valgtStue); // Open the next screen with the selected Stue
                }
            });
            buttonsPanel.add(stueButton);
        }
        frame.add(buttonsPanel, BorderLayout.CENTER);// Add buttons panel to the center
        frame.add(headerPanel, BorderLayout.NORTH); // Add header panel to the top
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window to fill the screen
        frame.setUndecorated(true); // Remove window decorations (title bar, borders, etc.)        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static int getSelectedStue() {
        return valgtStue;
    }
}
