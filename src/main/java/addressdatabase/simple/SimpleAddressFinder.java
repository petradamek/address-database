package addressdatabase.simple;

import addressdatabase.Address;
import addressdatabase.AddressFinder;
import addressdatabase.AddressTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is very simple implementation of AddressService
 *
 * @author petr
 */
final class SimpleAddressFinder implements AddressFinder {

    private final List<Address> addresses;

    SimpleAddressFinder(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public Collection<Address> findAddress(Address address) {
        List<Address> result = new ArrayList<>();
        for (Address searchedAddress : addresses) {
            if (AddressTools.match(address, searchedAddress)) {
                result.add(searchedAddress);
            }
        }
        return result;
    }
}
