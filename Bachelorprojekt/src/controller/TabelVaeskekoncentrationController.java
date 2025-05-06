package controller;

import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.TabelVaeskekoncentrationModel;
import model.NormalvaerdierModel;
import view.TabelVaeskekoncentrationView;

public class TabelVaeskekoncentrationController {
    private TabelVaeskekoncentrationModel model;
    private TabelVaeskekoncentrationView view;
    private static final Logger logger = Logger.getLogger(TabelVaeskekoncentrationController.class.getName());

    public TabelVaeskekoncentrationController(TabelVaeskekoncentrationModel model, TabelVaeskekoncentrationView view) {
        this.model = model;
        this.view = view;
    }

    public TabelVaeskekoncentrationView getView() {
        return view;
    }

    public void updateView(String cprNr) {
        logger.info("Opdaterer citratmetabolisme-tabel for CPR: " + cprNr);
        model.fetchData(cprNr);

        // 1) Analyse for outliers
        NormalvaerdierModel normalvaerdierModel = new NormalvaerdierModel();
        boolean[][] outliers = normalvaerdierModel.analyserDataNormalvaerdi(model.getData(), cprNr);
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
