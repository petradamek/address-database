package addressdatabase.simple;

import addressdatabase.Address;
import addressdatabase.Address.HouseNoType;
import addressdatabase.AddressBase;
import addressdatabase.AddressService;
import addressdatabase.AddressServiceFactory;
import addressdatabase.SimpleAddress;
import addressdatabase.loader.AddressHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author petr
 */
public class SimpleAddressServiceFactory extends AddressServiceFactory {

    @Override
    public AddressService newAddressService() throws IOException {
        AddressHandlerImpl addressHandler = new AddressHandlerImpl();
        getDataLoader().loadData(addressHandler);
        return new SimpleAddressService(addressHandler.addresses);
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
