package controller;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.NormalvaerdierModel;
import model.TabelAGasModel;
import view.TabelAGasView;

public class TabelAGasController {
    private TabelAGasModel model;
    private TabelAGasView view;
    private NormalvaerdierModel normalvaerdierModel = new NormalvaerdierModel();

    public TabelAGasController(TabelAGasModel model, TabelAGasView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView(String cprNr) {
        model.fetchData(cprNr);
        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        // Første kolonne: tom
        tableModel.addColumn("");

        // Tilføj kolonneoverskrifter med tidspunkt + dato (HTML = to linjer)
        for (int i = 0; i < model.getTimestamps().size(); i++) {
            String ts = model.getTimestamps().get(i);
            String date = model.getDates().get(i);
            String header = "<html><center>" + ts + "<br>" + date + "</center></html>";
            tableModel.addColumn(header);
        }

        // Tilføj data-rækker (pH, BE, osv.)
        Object[][] data = model.getData();
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        // Udtræk parameternavne og analyser outliers
        String[] parametre = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            parametre[i] = data[i][0].toString();
        }
        boolean[][] outliers = normalvaerdierModel.analyserDataNormalvaerdi(data, cprNr);
        view.setOutlierMatrix(outliers);

        // Justér første kolonnebredde
        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        view.getTable().revalidate();
        view.getTable().repaint();
    }
}
