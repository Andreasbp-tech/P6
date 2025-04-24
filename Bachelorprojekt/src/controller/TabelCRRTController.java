package controller;

import model.TabelCRRTModel;
import view.TabelCRRTView;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn; // Add this import statement

public class TabelCRRTController {
    private TabelCRRTModel model;
    private TabelCRRTView view;

    public TabelCRRTController(TabelCRRTModel model, TabelCRRTView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView(String cprNr) {
        model.fetchData(cprNr);
        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        tableModel.addColumn("");
        for (String timestamp : model.getTimestamps()) {
            tableModel.addColumn(timestamp);
        }

        for (Object[] row : model.getData()) {
            tableModel.addRow(row);
        }

        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);
    }
}
