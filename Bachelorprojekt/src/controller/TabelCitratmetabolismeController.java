package controller;

import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.TabelCitratmetabolismeModel;
import model.NormalvaerdiCheck;
import view.TabelCitratmetabolismeView;

public class TabelCitratmetabolismeController {
    private TabelCitratmetabolismeModel model;
    private TabelCitratmetabolismeView view;
    private static final Logger logger = Logger.getLogger(TabelCitratmetabolismeController.class.getName());

    public TabelCitratmetabolismeController(TabelCitratmetabolismeModel model, TabelCitratmetabolismeView view) {
        this.model = model;
        this.view = view;
    }

    public TabelCitratmetabolismeView getView() {
        return view;
    }

    public void updateView(String cprNr) {
        logger.info("Opdaterer citratmetabolisme-tabel for CPR: " + cprNr);
        model.fetchData(cprNr);

        // 1) Analyse for outliers
        NormalvaerdiCheck checker = new NormalvaerdiCheck();
        boolean[][] outliers = checker.analyserDataNormalvardi(model.getData());
        view.setOutlierMatrix(outliers);

        // 2) Rebuild table model
        DefaultTableModel tm = new DefaultTableModel();
        view.getTable().setModel(tm);

        // empty first column
        tm.addColumn("");
        // headers with two‐line timestamp+date via HTML
        for (int i = 0; i < model.getTimestamps().size(); i++) {
            String ts = model.getTimestamps().get(i);
            String dt = model.getDates().get(i);
            tm.addColumn("<html><center>" + ts + "<br>" + dt + "</center></html>");
        }

        // data rows
        for (Object[] row : model.getData()) {
            tm.addRow(row);
        }

        // lock first column width
        TableColumn first = view.getTable().getColumnModel().getColumn(0);
        first.setPreferredWidth(100);
        first.setMinWidth(100);
        first.setMaxWidth(100);

        view.getTable().revalidate();
        view.getTable().repaint();
    }
}
