package cz.muni.fi.pv168.addresses.finder.simple;

import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.Address.HouseNoType;
import cz.muni.fi.pv168.addresses.model.AddressBase;
import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.model.SimpleAddress;
import cz.muni.fi.pv168.addresses.loader.AddressHandler;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory for {@link SimpleAddressFinder}.
 */
public final class SimpleAddressFinderFactory extends AddressFinderFactory {

    private final SearchStrategy searchStrategy;

    public SimpleAddressFinderFactory(DataLoader dataLoader, SearchStrategy searchStrategy) {
        super(dataLoader);
        this.searchStrategy = searchStrategy;
    }

    @Override
    public AddressFinder newAddressFinder() throws IOException {
        AddressHandlerImpl addressHandler = new AddressHandlerImpl();
        getDataLoader().loadData(addressHandler);
        return new SimpleAddressFinder(addressHandler.addresses, searchStrategy);
    }

    private static class AddressHandlerImpl implements AddressHandler {

        private final List<Address> addresses = new ArrayList<>();

        @Override
        public void processAddress(AddressBase addressBase, String orientationNo, Integer houseNo, HouseNoType houseNoType) {
            Address address = new SimpleAddress(addressBase, orientationNo, houseNo, houseNoType);
            addresses.add(address);
        }
    }
}
