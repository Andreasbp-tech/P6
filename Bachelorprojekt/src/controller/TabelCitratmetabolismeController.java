/*package controller;

import model.TabelCitratmetabolismeModel;
import view.TabelCitratmetabolismeView;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.logging.Logger;

public class TabelCitratmetabolismeController {
    private TabelCitratmetabolismeModel model;
    private TabelCitratmetabolismeView view;
    private static final Logger logger = Logger.getLogger(TabelCitratmetabolismeController.class.getName());

    public TabelCitratmetabolismeController(TabelCitratmetabolismeModel model, TabelCitratmetabolismeView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView(String cprNr) {
        logger.info("Opdaterer citratmetabolisme-tabel for CPR: " + cprNr);
        model.fetchData(cprNr);

        DefaultTableModel tableModel = new DefaultTableModel();
        view.getTable().setModel(tableModel);

        tableModel.addColumn("");
        for (String timestamp : model.getTimestamps()) {
            tableModel.addColumn(timestamp);
        }

        for (Object[] row : model.getData()) {
            tableModel.addRow(row);
        }

        TableColumn firstCol = view.getTable().getColumnModel().getColumn(0);
        firstCol.setPreferredWidth(100);
        firstCol.setMinWidth(100);
        firstCol.setMaxWidth(100);

        view.getTable().revalidate();
        view.getTable().repaint();
    }

    public TabelCitratmetabolismeView getView() {
        return view;
    }
}*/
package controller;

import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.TabelCitratmetabolismeModel;
import view.TabelCitratmetabolismeView;

public class TabelCitratmetabolismeController {
    private TabelCitratmetabolismeModel model;
    private TabelCitratmetabolismeView view;
    private static final Logger logger = Logger.getLogger(TabelCitratmetabolismeController.class.getName());

    public TabelCitratmetabolismeController(TabelCitratmetabolismeModel model, TabelCitratmetabolismeView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView(String cprNr) {
        logger.info("Opdaterer citratmetabolisme-tabel for CPR: " + cprNr);
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
        }

        // Set column width for the first column
        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        // Refresh table view
        view.getTable().revalidate();
        view.getTable().repaint();
    }

    public TabelCitratmetabolismeView getView() {
        return view;
    }
}

