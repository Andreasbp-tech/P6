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
