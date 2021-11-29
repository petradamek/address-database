package cz.muni.fi.pv168.addresses.ui.loading;

import cz.muni.fi.pv168.addresses.loader.DataLoader;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.ui.model.ReadOnlyEntityTableModel;

import java.io.IOException;

public class SynchronousLoadingStrategy extends AbstractLoadingStrategy {

    public SynchronousLoadingStrategy(DataLoader dataLoader) {
        super(dataLoader);
    }

    @Override
    public void loadAllData(ReadOnlyEntityTableModel<Address> addressTableModel) {
        try {
            addressTableModel.setRows(dataLoader.loadData());
        } catch (IOException ex) {
            handleError(ex);
        }
    }
}
