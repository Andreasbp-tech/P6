package view;

import javax.swing.*;
import java.awt.*;

public class ParameterPopupView {
    private JFrame frame;
    private JPanel panel;
    private JLabel titleLabel;
    private JButton crrtButton;
    private JButton citratButton;
    private JButton cancelButton;

    public ParameterPopupView() {
        frame = new JFrame("Nyt parametre");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Vælg parametertype", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        crrtButton = new JButton("CRRT");
        crrtButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        crrtButton.setMaximumSize(new Dimension(175, crrtButton.getMinimumSize().height));

        citratButton = new JButton("Citrat metabolisme");
        citratButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        citratButton.setMaximumSize(new Dimension(175, citratButton.getMinimumSize().height));

        cancelButton = new JButton("Annullér");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setMaximumSize(new Dimension(100, cancelButton.getMinimumSize().height));

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(crrtButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(citratButton);
        panel.add(Box.createVerticalGlue());
        panel.add(cancelButton);
        panel.add(Box.createVerticalStrut(5));

        frame.add(panel);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JButton getCrrtButton() {
        return crrtButton;
    }

    public JButton getCitratButton() {
        return citratButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void close() {
        frame.dispose();
    }
}
