package cz.muni.fi.pv168.addresses.loader;

import java.io.IOException;

/**
 * This is common interface for data loading service.
 *
 * @author Petr Adámek
 */
public interface DataLoader {

    void loadData(AddressHandler addressHandler) throws IOException;
}
