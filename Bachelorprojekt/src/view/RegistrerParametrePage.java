package view;

import javax.swing.*;
import java.awt.*;

public class RegistrerParametrePage {

    public static void launch() {
        JFrame frame = new JFrame("Registrer Parametre");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the size to half the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a label with the text "HELLO ANNE"
        JLabel label = new JLabel("HELLO ANNE", SwingConstants.CENTER);
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
