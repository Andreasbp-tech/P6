package controller;

import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.TabelCRRTModel;
import view.TabelCRRTView;
import model.NormalvaerdierModel;

public class TabelCRRTController {
    private TabelCRRTModel model;
    private TabelCRRTView view;
    private static final Logger logger = Logger.getLogger(TabelCRRTController.class.getName());

    public TabelCRRTController(TabelCRRTModel model, TabelCRRTView view) {
        this.model = model;
        this.view = view;
    }

    public TabelCRRTView getView() {
        return view;
    }

    public void updateView(String cprNr) {
        logger.info("Updating view with CPR: " + cprNr);
        model.fetchData(cprNr);

        // Tjek for normalværdier
        NormalvaerdierModel normalvaerdiModel = new NormalvaerdierModel();
        boolean[][] outliers = normalvaerdiModel.analyserDataNormalvaerdi(model.getData(), cprNr);
        view.setOutlierMatrix(outliers); // sørg for at TabelCRRTView har denne metode

        DefaultTableModel tableModel = new DefaultTableModel();
        view.getTable().setModel(tableModel);

        // Tilføj kolonner
        tableModel.addColumn(""); // Første kolonne - tom
        for (int i = 0; i < model.getTimestamps().size(); i++) {
            String timestamp = model.getTimestamps().get(i);
            String date = model.getDates().get(i);
            String header = "<html><center>" + timestamp + "<br>" + date + "</center></html>";
            tableModel.addColumn(header);
        }

        // Tilføj data rækker
        for (Object[] row : model.getData()) {
            tableModel.addRow(row);
            logger.info("Added row: " + java.util.Arrays.toString(row));
        }

        // Juster første kolonne
        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        view.getTable().revalidate();
        view.getTable().repaint();

        // Udskriv outliers i konsollen
        String[] parameterNames = new String[model.getData().length];
        for (int i = 0; i < model.getData().length; i++) {
            parameterNames[i] = model.getData()[i][0].toString();
        }
    }

}
