package view;

import javax.swing.*;
import java.awt.*;

public class ProvesvarView {
    private JPanel mainPanel;
    private JPanel agasSection;
    private JLabel agasLabel;
    private JPanel blodproveSection;
    private JLabel blodproveLabel;

    public ProvesvarView() {
        mainPanel = new JPanel(new BorderLayout());

        agasSection = new JPanel(new BorderLayout());
        agasLabel = new JLabel("A-gas og maskinprøver", SwingConstants.CENTER);
        agasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        agasSection.add(agasLabel, BorderLayout.NORTH);

        mainPanel.add(agasSection, BorderLayout.CENTER);
    }

    public void addBlodprovePanel(JPanel blodPanel) {
        blodproveSection = new JPanel(new BorderLayout());
        blodproveLabel = new JLabel("Blodprøver", SwingConstants.CENTER);
        blodproveLabel.setFont(new Font("Arial", Font.BOLD, 16));
        blodproveSection.add(blodproveLabel, BorderLayout.NORTH);
        blodPanel.setPreferredSize(new Dimension(800, 200));
        blodproveSection.add(blodPanel, BorderLayout.CENTER);

        mainPanel.add(blodproveSection, BorderLayout.SOUTH);
    }

    public void addAgasTablePanel(JPanel agasPanel) {
        agasPanel.setPreferredSize(new Dimension(800, 400));
        agasSection.add(agasPanel, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
