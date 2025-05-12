package view;

import javax.swing.*;
import java.awt.*;

public class TrendsPage {
    public static JPanel createPanel(int valgtStue) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tekst øverst
        // JLabel label = new JLabel("Trends vises her for stue " + valgtStue,
        // SwingConstants.CENTER);
        // panel.add(label, BorderLayout.NORTH);

        // Indlæs og skaler billede
        ImageIcon originalIcon = new ImageIcon(TrendsPage.class.getResource("/utilities/Trends-proto.png"));

        // Ændr disse værdier for ønsket størrelse
        int width = 1000;
        int height = 600;

        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Tilføj til JLabel
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        return panel;
    }
}
