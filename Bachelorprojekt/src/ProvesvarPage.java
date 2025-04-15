import javax.swing.*;

public class ProvesvarPage {
    public static JPanel createPanel(int valgtStue) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Prøvesvar vises her for stue " + valgtStue));
        // Add more components and functionality as needed
        return panel;
    }
}
