package view;

import java.awt.*;
import javax.swing.*;

import model.ValgStueModel;
import utilities.HeaderPanelUtil;
import view.GenereltPage;

public class MainPage {
    public static void launch(int valgtStue) {
        JFrame frame = new JFrame("ICUview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());

        // Use the universal header pane
        JPanel headerPanel = HeaderPanelUtil.createHeaderPanel();

        // Top panel with buttons
        JPanel topPanel = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns
        JButton genereltButton = new JButton("Generelt");
        JButton prøvesvarButton = new JButton("Prøvesvar");
        JButton trendsButton = new JButton("Trends");

        // Set preferred size and font for buttons
        Dimension buttonSize = new Dimension(200, 50); // Adjust size as needed
        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Adjust font size as needed

        genereltButton.setPreferredSize(buttonSize);
        genereltButton.setFont(buttonFont);
        prøvesvarButton.setPreferredSize(buttonSize);
        prøvesvarButton.setFont(buttonFont);
        trendsButton.setPreferredSize(buttonSize);
        trendsButton.setFont(buttonFont);

        // Set initial button colors to indicate active page
        genereltButton.setBackground(Color.DARK_GRAY);
        genereltButton.setForeground(Color.WHITE);
        prøvesvarButton.setBackground(Color.LIGHT_GRAY);
        prøvesvarButton.setForeground(Color.BLACK);
        trendsButton.setBackground(Color.LIGHT_GRAY);
        trendsButton.setForeground(Color.BLACK);

        topPanel.add(genereltButton);
        topPanel.add(prøvesvarButton);
        topPanel.add(trendsButton);

        // Combine headerPanel and topPanel into one panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(headerPanel, BorderLayout.NORTH);
        combinedPanel.add(topPanel, BorderLayout.CENTER);

        frame.add(combinedPanel, BorderLayout.NORTH); // Add combined panel to the top

        // Create an instance of ValgStueModel
        ValgStueModel model = new ValgStueModel();
        model.setValgtStue(valgtStue);
        model.getPatientData(valgtStue); // Load patient data

        // Main panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());
        JPanel genereltPanel = GenereltPage.createPanel(valgtStue, model);
        JPanel prøvesvarPanel = ProvesvarPage.createPanel(valgtStue);
        JPanel trendsPanel = TrendsPage.createPanel(valgtStue);

        mainPanel.add(genereltPanel, "Generelt");
        mainPanel.add(prøvesvarPanel, "Prøvesvar");
        mainPanel.add(trendsPanel, "Trends");

        frame.add(mainPanel, BorderLayout.CENTER);

        // Action listeners for navigation buttons
        genereltButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "Generelt");
            genereltButton.setBackground(Color.DARK_GRAY);
            genereltButton.setForeground(Color.WHITE);
            prøvesvarButton.setBackground(Color.LIGHT_GRAY);
            prøvesvarButton.setForeground(Color.BLACK);
            trendsButton.setBackground(Color.LIGHT_GRAY);
            trendsButton.setForeground(Color.BLACK);
        });
        prøvesvarButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "Prøvesvar");
            genereltButton.setBackground(Color.LIGHT_GRAY);
            genereltButton.setForeground(Color.BLACK);
            prøvesvarButton.setBackground(Color.DARK_GRAY);
            prøvesvarButton.setForeground(Color.WHITE);
            trendsButton.setBackground(Color.LIGHT_GRAY);
            trendsButton.setForeground(Color.BLACK);
        });
        trendsButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "Trends");
            genereltButton.setBackground(Color.LIGHT_GRAY);
            genereltButton.setForeground(Color.BLACK);
            prøvesvarButton.setBackground(Color.LIGHT_GRAY);
            prøvesvarButton.setForeground(Color.BLACK);
            trendsButton.setBackground(Color.DARK_GRAY);
            trendsButton.setForeground(Color.WHITE);
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window to fill the screen
        frame.setUndecorated(false); // Remove window decorations (title bar, borders, etc.)
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    // Nedenstående er bare så man kan launche herfra, og slippe login hver gang.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> launch(1)); // Example launch with "Stue 1"
    }
}
