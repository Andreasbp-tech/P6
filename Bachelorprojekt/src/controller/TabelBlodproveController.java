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
        tableModel.setRowCount(0); // Fjern eksisterende rækker
        tableModel.setColumnCount(0); // Fjern eksisterende kolonner

        // Tilføj kolonneoverskrifter: første kolonne er tom (for rækkenavne), derefter
        // tidspunkter
        tableModel.addColumn("");
        for (String tidspunkt : model.getTidspunkter()) {
            tableModel.addColumn(tidspunkt);
        }

        // Tilføj data-rækkenavne (blodprøveparametre)
        for (int i = 0; i < model.getDates().size(); i++) {
            Object[] row = new Object[model.getTidspunkter().size() + 1]; // +1 for rækkenavnet
            row[0] = model.getDates().get(i); // Indsæt dato som rækkenavn
            for (int j = 0; j < model.getTidspunkter().size(); j++) {
                row[j + 1] = model.getData()[i][j]; // Hent de korrekte data for hver parameter
            }
            tableModel.addRow(row);
        }

        // Justér første kolonnebredde for at vise rækkenavnene ordentligt
        TableColumn firstColumn = view.getTable().getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(150);
        firstColumn.setMinWidth(150);
        firstColumn.setMaxWidth(150);

        view.getTable().revalidate();
        view.getTable().repaint();
    }
}
