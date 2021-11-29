package cz.muni.fi.pv168.addresses.ui.renderer;

import cz.muni.fi.pv168.addresses.model.Address;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.util.Map;

public class HouseNoTypeRenderer implements TableCellRenderer {

    private final static Map<Address.HouseNoType, String> LABELS = Map.of(
            Address.HouseNoType.DESCRIPTIVE_NO, "č.p.",
            Address.HouseNoType.REGISTRATION_NO, "ev.č."
    );

    private final DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        tableCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null) {
            tableCellRenderer.setText(LABELS.getOrDefault(value, "?"));
        }
        return tableCellRenderer;
    }
}
