package utilities;

import javax.swing.*;
import java.awt.*;

public class ProgramIcon {

    // Metode til at oprette og returnere et ImageIcon
    public static ImageIcon getProgramIcon() {
        ImageIcon icon = new ImageIcon("src/utilities/logo_ICUview.png");
        return icon;
    }

    // Metode til at sætte programikonet på et JFrame
    public static void setIconToWindow(JFrame frame) {
        frame.setIconImage(getProgramIcon().getImage());
    }
}
