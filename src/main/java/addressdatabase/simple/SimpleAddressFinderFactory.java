package addressdatabase.simple;

import addressdatabase.Address;
import addressdatabase.Address.HouseNoType;
import addressdatabase.AddressBase;
import addressdatabase.AddressFinder;
import addressdatabase.AddressFinderFactory;
import addressdatabase.SimpleAddress;
import addressdatabase.loader.AddressHandler;
import addressdatabase.loader.DataLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author petr
 */
public final class SimpleAddressFinderFactory extends AddressFinderFactory {

    public SimpleAddressFinderFactory(DataLoader dataLoader) {
        super(dataLoader);
    }

    @Override
    public AddressFinder newAddressFinder() throws IOException {
        AddressHandlerImpl addressHandler = new AddressHandlerImpl();
        getDataLoader().loadData(addressHandler);
        return new SimpleAddressFinder(addressHandler.addresses);
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
