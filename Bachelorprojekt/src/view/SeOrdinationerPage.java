package view;

import javax.swing.*;
import java.awt.*;
import utilities.ProgramIcon;

public class SeOrdinationerPage {

    public static void launch() {
        JFrame frame = new JFrame("Se Ordinationer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ProgramIcon.setIconToWindow(frame); // Set icon to the window

        // Set the size to half the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a label with the text "HELLO MARIE"
        JLabel label = new JLabel("HELLO MARIE", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style

        // Add the label to the frame
        frame.add(label);

        // Make the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
