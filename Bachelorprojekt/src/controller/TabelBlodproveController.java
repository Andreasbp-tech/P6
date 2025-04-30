package controller;

import model.TabelBlodproveModel;
import view.TabelBlodproveView;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TabelBlodproveController {
    private TabelBlodproveModel model;
    private TabelBlodproveView view;

    public TabelBlodproveController(TabelBlodproveModel model, TabelBlodproveView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView(String cprNr) {
        model.fetchData(cprNr);

        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        // Tilføj tom kolonne + tidspunkter
        tableModel.addColumn("");
        for (String ts : model.getTimestamps()) {
            tableModel.addColumn(ts);
        }

        // Tilføj datorække
        Object[] dateRow = new Object[model.getTimestamps().size() + 1];
        dateRow[0] = "";
        for (int i = 0; i < model.getDates().size(); i++) {
            dateRow[i + 1] = model.getDates().get(i);
        }
        tableModel.addRow(dateRow);

        // Tilføj data
        for (Object[] row : model.getData()) {
            tableModel.addRow(row);
        }

        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMinWidth(100);
        firstColumn.setMaxWidth(100);

        view.getTable().revalidate();
        view.getTable().repaint();
    }
}
