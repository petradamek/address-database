package cz.muni.fi.pv168.addresses.ui.loading;

import cz.muni.fi.pv168.addresses.loader.DataLoader;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.ui.LoadingStateHandler;
import cz.muni.fi.pv168.addresses.ui.model.ReadOnlyEntityTableModel;

import java.io.IOException;
import java.util.List;

public class SynchronousLoadingStrategy extends AbstractLoadingStrategy {

    public SynchronousLoadingStrategy(DataLoader dataLoader, LoadingStateHandler loadingStateHandler) {
        super(dataLoader, loadingStateHandler);
    }

    @Override
    public void loadAllData(ReadOnlyEntityTableModel<Address> addressTableModel) {
        // Does this even work?
        addressTableModel.setRows(List.of());
        // Does this even work?
        loadingStateHandler.setLoading(true);
        try {
            addressTableModel.setRows(dataLoader.loadData());
        } catch (IOException ex) {
            handleError(ex);
        }
        // Does this even work?
        loadingStateHandler.setLoading(false);
    }
}
