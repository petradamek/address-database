package cz.muni.fi.pv168.addresses.ui.loading;

import cz.muni.fi.pv168.addresses.loader.DataLoader;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.ui.model.ReadOnlyEntityTableModel;

public interface LoadingStrategy {

    void loadAllData(ReadOnlyEntityTableModel<Address> addressTableModel);

}
