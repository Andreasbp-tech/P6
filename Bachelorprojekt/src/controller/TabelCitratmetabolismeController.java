package controller;

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
}
