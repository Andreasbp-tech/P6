package view;
import javax.swing.*;

public class TrendsPage {
    public static JPanel createPanel(int valgtStue) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Trends vises her for stue " + valgtStue));
        // Add more components and functionality as needed
        return panel;
    }
}
