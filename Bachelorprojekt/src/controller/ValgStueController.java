package controller;

import model.ValgStueModel;
import view.ValgStueView;
import view.GenereltPage; // Import GenereltPage class
import view.MainPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ValgStueController {
    private ValgStueModel model;
    private ValgStueView view;

    public ValgStueController(ValgStueModel model, ValgStueView view) {
        this.model = model;
        this.view = view;

        for (int i = 1; i <= 20; i++) {
            JButton stueButton = new JButton("Stue " + i);
            stueButton.setPreferredSize(new Dimension(150, 50)); // Set button size
            stueButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    model.setValgtStue(Integer.parseInt(stueButton.getText().split(" ")[1]));
                    view.dispose(); // Close the selection screen
                    model.getPatientData(model.getValgtStue());
                    MainPage.launch(model.getValgtStue()); // Open the next screen with the selected Stue
                }
            });
            view.addButton(stueButton);
        }
    }

    public void showView() {
        view.show();
    }
}
