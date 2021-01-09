package addressdatabase.simple;

import addressdatabase.Address;
import addressdatabase.AddressService;
import addressdatabase.AddressTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is very simple implementation of AddressService
 *
 * @author petr
 */
public class SimpleAddressService implements AddressService {

    private List<Address> addresses;

    SimpleAddressService(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public Collection<Address> findAddress(Address address) {
        List<Address> result = new ArrayList<Address>();
        for (Address searchedAddress : addresses) {
            if (AddressTools.match(address, searchedAddress)) {
                result.add(searchedAddress);
            }
        }
        return result;
    }
}
