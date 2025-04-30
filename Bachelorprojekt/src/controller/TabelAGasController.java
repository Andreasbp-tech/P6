package controller;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.TabelAGasModel;
import view.TabelAGasView;

public class TabelAGasController {
    private TabelAGasModel model;
    private TabelAGasView view;

    public TabelAGasController(TabelAGasModel model, TabelAGasView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView(String cprNr) {
        model.fetchData(cprNr);
        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        // Add columns: blank + timestamps
        tableModel.addColumn("");
        for (String ts : model.getTimestamps()) {
            tableModel.addColumn(ts);
        }

        // Add date row
        Object[] dateRow = new Object[model.getTimestamps().size() + 1];
        dateRow[0] = "";
        for (int i = 0; i < model.getDates().size(); i++) {
            dateRow[i + 1] = model.getDates().get(i);
        }
        tableModel.addRow(dateRow);

        // Add data rows
        for (Object[] row : model.getData()) {
            tableModel.addRow(row);
        }

        // Fix first column width
        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        view.getTable().revalidate();
        view.getTable().repaint();
    }
}
