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

        // Tilføj tom første kolonne
        tableModel.addColumn("");

        // Tilføj kolonneoverskrifter med timestamp + dato
        for (int i = 0; i < model.getTimestamps().size(); i++) {
            String ts = model.getTimestamps().get(i);
            String date = model.getDates().get(i);
            String header = "<html><center>" + ts + "<br>" + date + "</center></html>";
            tableModel.addColumn(header);
        }

        // Tilføj data-rækker
        for (Object[] row : model.getData()) {
            tableModel.addRow(row);
        }

        // Justér første kolonne
        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        // Refresh
        view.getTable().revalidate();
        view.getTable().repaint();
    }

    public TabelCitratmetabolismeView getView() {
        return view;
    }
}
