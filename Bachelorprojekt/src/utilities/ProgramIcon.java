package utilities;

import javax.swing.*;
import java.awt.*;

public class ProgramIcon {

    // Metode til at oprette og returnere et ImageIcon
    public static ImageIcon getProgramIcon() {
        // Brug relativ sti til ressourcen
        java.net.URL imgURL = ProgramIcon.class.getClassLoader().getResource("utilities/logo_ICUview.png");

        // Hvis URL'en er null, betyder det, at ressourcen ikke blev fundet
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.out.println("Billedet kunne ikke findes. Kontroller filens placering.");
            return null;
        }
    }

    // Metode til at sætte programikonet på et JFrame
    public static void setIconToWindow(JFrame frame) {
        ImageIcon icon = getProgramIcon();
        if (icon != null) {
            frame.setIconImage(icon.getImage());
        } else {
            System.out.println("Kunne ikke sætte programikonet.");
        }
    }
}
