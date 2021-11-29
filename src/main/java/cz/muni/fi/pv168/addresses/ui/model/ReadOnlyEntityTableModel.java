package cz.muni.fi.pv168.addresses.ui.model;

import java.util.ArrayList;
import java.util.List;

public class ReadOnlyEntityTableModel<E> extends AbstractEntityTableModel<E> {

    private final List<E> rows = new ArrayList<>();

    public ReadOnlyEntityTableModel(List<Column<E, ?>> columns) {
        super(columns);
    }

    public void setRows(List<E> rows) {
        this.rows.clear();
        this.rows.addAll(rows);
        fireTableDataChanged();
    }

    public void addRows(List<E> rows) {
        if (!rows.isEmpty()) {
            int newRowIndex = rows.size();
            this.rows.addAll(rows);
            fireTableRowsInserted(newRowIndex, newRowIndex + rows.size() - 1);
        }
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public E getEntity(int rowIndex) {
        return rows.get(rowIndex);
    }
}
