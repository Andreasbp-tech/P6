package utilities;
import java.awt.*;
import javax.swing.*;

public class HeaderPanelUtil {
    public static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel textPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0); // No padding between labels

        JLabel icuLabel = new JLabel("ICU");
        icuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        icuLabel.setForeground(Color.BLUE); // Set "ICU" text color to blue

        JLabel viewLabel = new JLabel("view");
        viewLabel.setFont(new Font("Arial", Font.BOLD, 24));
        viewLabel.setForeground(Color.BLACK); // Set "view" text color to black

        gbc.gridx = 0;
        textPanel.add(icuLabel, gbc);

        gbc.gridx = 1;
        textPanel.add(viewLabel, gbc);

        JPanel rightAlignedPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Add spacing to the right
        rightAlignedPanel.add(textPanel);

        headerPanel.add(rightAlignedPanel, BorderLayout.EAST); // Add the text panel to the right side

        return headerPanel;
    }
}
