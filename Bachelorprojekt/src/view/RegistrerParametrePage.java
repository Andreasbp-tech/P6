package view;

import javax.swing.*;
import java.awt.*;
import view.RegistrerCRRTPage;
import view.RegistrerCitratmetabolismePage;

public class RegistrerParametrePage {

    public static void launch() {
        JFrame frame = new JFrame("Nyt parametre");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the size to a narrower width
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create the title label
        JLabel titleLabel = new JLabel("Vælg parametertype", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some padding

        // Create the buttons
        JButton crrtButton = new JButton("CRRT");
        crrtButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        crrtButton.setMaximumSize(new Dimension(175, crrtButton.getMinimumSize().height));
        crrtButton.addActionListener(e -> {
            frame.dispose();
            RegistrerCRRTPage.launch(); // Launch the CRRT page
        }); // Add action listener to launch CRRT page

        JButton citratButton = new JButton("Citrat metabolisme");
        citratButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        citratButton.setMaximumSize(new Dimension(175, citratButton.getMinimumSize().height));
        citratButton.addActionListener(e -> {
            frame.dispose();
            RegistrerCitratmetabolismePage.launch(); // Launch the Citrat metabolisme page
        }); // Add action listener to launch Citrat page

        // Create the cancel button
        JButton cancelButton = new JButton("Annullér");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        cancelButton.setMaximumSize(new Dimension(100, cancelButton.getMinimumSize().height));
        cancelButton.addActionListener(e -> frame.dispose()); // Add action listener to close the window

        // Add components to the panel
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10)); // Add vertical space between components
        panel.add(crrtButton);
        panel.add(Box.createVerticalStrut(10)); // Add vertical space between components
        panel.add(citratButton);
        panel.add(Box.createVerticalGlue()); // Add vertical glue to push the button to the bottom
        panel.add(cancelButton);
        panel.add(Box.createVerticalStrut(5)); // Add vertical space between components

        // Add the panel to the frame
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
