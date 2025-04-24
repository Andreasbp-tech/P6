package controller;

import model.RegistrerCRRTModel;
import view.RegistrerCRRTView;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class RegistrerCRRTController {
    private RegistrerCRRTModel model;
    private RegistrerCRRTView view;
    private String cprNr;

    public RegistrerCRRTController(RegistrerCRRTModel model, RegistrerCRRTView view, String cprNr) {
        this.model = model;
        this.view = view;
        this.cprNr = cprNr;

        this.view.getSaveButton().addActionListener(e -> {
            String dialysatflow = view.getTextFields()[0].getText();
            String blodflow = view.getTextFields()[1].getText();
            String vaesketraek = view.getTextFields()[2].getText();
            String indloebstryk = view.getTextFields()[3].getText();
            String returtryk = view.getTextFields()[4].getText();
            String praefiltertryk = view.getTextFields()[5].getText();
            String heparin = view.getTextFields()[6].getText();

            // Generate the current timestamp in the format YYYY-MM-DD HH:MM:SS
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = sdf.format(timestamp);

            model.saveToDatabase(cprNr, formattedTimestamp, dialysatflow, blodflow, vaesketraek, indloebstryk,
                    returtryk, praefiltertryk, heparin);
        });
    }

    public void showView() {
        view.setVisible(true);
    }
}
