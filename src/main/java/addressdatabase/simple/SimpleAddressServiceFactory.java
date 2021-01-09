package addressdatabase.simple;

import addressdatabase.*;
import addressdatabase.Address.HouseNoType;
import addressdatabase.loader.AddressHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author petr
 */
public class SimpleAddressServiceFactory extends AddressServiceFactory {

    private static class AddressHandlerImpl implements AddressHandler {

        private List<Address> addresses = new ArrayList<Address>();
        
        @Override
        public void processAddress(AddressBase addressBase, String orientationNo, Integer houseNo, HouseNoType houseNoType) {
            Address address = new SimpleAddress(addressBase, orientationNo, houseNo, houseNoType);
            addresses.add(address);
        }
        
    }
    
    @Override
    public AddressService newAddressService() throws IOException {
        AddressHandlerImpl addressHandler = new AddressHandlerImpl();
        getDataLoader().loadData(addressHandler);
        return new SimpleAddressService(addressHandler.addresses);
    }
    
    
    
}
