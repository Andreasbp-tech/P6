package controller;

//import model.NormalvaerdierModel;
import model.NormalvaerdierModel;
import model.TabelBlodproveModel;
import view.TabelBlodproveView;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TabelBlodproveController {
    private TabelBlodproveModel model;
    private TabelBlodproveView view;
    private NormalvaerdierModel normalvaerdierModel;

    public TabelBlodproveController(TabelBlodproveModel model, TabelBlodproveView view) {
        this.model = model;
        this.view = view;
        this.normalvaerdierModel = new NormalvaerdierModel();
    }

    public void updateView(String cprNr) {
        model.fetchData(cprNr);

        Object[][] data = model.getData();
        boolean[][] outlierMatrix = normalvaerdierModel.analyserDataNormalvaerdi(data, cprNr);

        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        // Første kolonne: tom
        tableModel.addColumn("");

        // Tilføj kolonner med tidspunkt + dato i to linjer
        for (int i = 0; i < model.getTimestamps().size(); i++) {
            String ts = model.getTimestamps().get(i);
            String date = model.getDates().get(i);
            String header = "<html><center>" + ts + "<br>" + date + "</center></html>";
            tableModel.addColumn(header);
        }

        // Tilføj rækker med data
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        view.setOutlierMatrix(outlierMatrix);

        view.getTable().revalidate();
        view.getTable().repaint();
    }
}
