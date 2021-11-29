package cz.muni.fi.pv168.addresses.loader;

import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.SimpleAddress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This is common interface for data loading service.
 *
 * @author Petr Adámek
 */
public interface DataLoader {

    void loadData(AddressHandler addressHandler) throws IOException;

    default void loadData(Consumer<Address> addressConsumer) throws IOException {
        loadData((addressBase, orientationNo, houseNo, houseNoType) -> addressConsumer.accept(
                new SimpleAddress(addressBase, orientationNo, houseNo, houseNoType)));
    }

    default List<Address> loadData() throws IOException {
        List<Address> addresses = new ArrayList<>();
        loadData(addresses::add);
        return addresses;
    }
}
