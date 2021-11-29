package cz.muni.fi.pv168.addresses.ui.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

abstract class AbstractEntityTableModel<E> extends AbstractTableModel implements EntityTableModel<E> {

    private final List<Column<E, ?>> columns;

    protected AbstractEntityTableModel(List<Column<E, ?>> columns) {
        this.columns = columns;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var entity = getEntity(rowIndex);
        return getColumn(columnIndex).getValue(entity);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return getColumn(columnIndex).getColumnName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getColumn(columnIndex).getColumnClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return getColumn(columnIndex).isEditable();
    }

    private Column<E, ?> getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }
}
