package view;

import java.awt.*;
import javax.swing.*;

import utilities.HeaderPanelUtil;

public class MainPageView {
    private JFrame frame;
    private JPanel mainPanel;
    private JButton genereltButton;
    private JButton prøvesvarButton;
    private JButton trendsButton;

    public MainPageView() {
        frame = new JFrame("ICUview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());

        // Use the universal header pane
        JPanel headerPanel = HeaderPanelUtil.createHeaderPanel();

        // Top panel with buttons
        JPanel topPanel = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns
        genereltButton = new JButton("Generelt");
        prøvesvarButton = new JButton("Prøvesvar");
        trendsButton = new JButton("Trends");

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

        // Main panel with CardLayout
        mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window to fill the screen
        frame.setUndecorated(false); // Remove window decorations (title bar, borders, etc.)
        frame.setLocationRelativeTo(null); // Center the frame on the screen
    }

    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }

    public void showPanel(String name) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, name);
    }

    public void setButtonColors(JButton activeButton) {
        genereltButton.setBackground(Color.LIGHT_GRAY);
        genereltButton.setForeground(Color.BLACK);
        prøvesvarButton.setBackground(Color.LIGHT_GRAY);
        prøvesvarButton.setForeground(Color.BLACK);
        trendsButton.setBackground(Color.LIGHT_GRAY);
        trendsButton.setForeground(Color.BLACK);

        activeButton.setBackground(Color.DARK_GRAY);
        activeButton.setForeground(Color.WHITE);
    }

    public void show() {
        frame.setVisible(true);
    }

    public JButton getGenereltButton() {
        return genereltButton;
    }

    public JButton getPrøvesvarButton() {
        return prøvesvarButton;
    }

    public JButton getTrendsButton() {
        return trendsButton;
    }
}
