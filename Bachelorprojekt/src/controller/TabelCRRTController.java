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

        // Create a new table model
        DefaultTableModel tableModel = new DefaultTableModel();
        view.getTable().setModel(tableModel);

        // Add columns
        tableModel.addColumn("");
        for (String timestamp : model.getTimestamps()) {
            tableModel.addColumn(timestamp);
        }

        // Add rows
        for (Object[] row : model.getData()) {
            tableModel.addRow(row);
            logger.info("Added row: " + java.util.Arrays.toString(row));
        }

        // Set column widths
        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        logger.info("Table updated successfully.");
        view.getTable().revalidate();
        view.getTable().repaint();
    }
}
