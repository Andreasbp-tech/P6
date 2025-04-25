package controller;

import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.TabelCRRTModel;
import view.TabelCRRTView;

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

        DefaultTableModel tableModel = new DefaultTableModel();
        view.getTable().setModel(tableModel);

        // Tilføj kolonner
        tableModel.addColumn(""); // Første kolonne - tom
        for (String timestamp : model.getTimestamps()) {
            tableModel.addColumn(timestamp);
        }

        // Første række: Datoer
        Object[] dateRow = new Object[model.getTimestamps().size() + 1];
        dateRow[0] = "";
        for (int i = 0; i < model.getDates().size(); i++) {
            dateRow[i + 1] = model.getDates().get(i);
        }
        tableModel.addRow(dateRow);

        // Tilføj data rækker
        for (Object[] row : model.getData()) {
            tableModel.addRow(row);
            logger.info("Added row: " + java.util.Arrays.toString(row));
        }

        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        view.getTable().revalidate();
        view.getTable().repaint();
    }

}
