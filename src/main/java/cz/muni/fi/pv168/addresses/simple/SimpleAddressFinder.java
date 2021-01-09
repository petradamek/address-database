package cz.muni.fi.pv168.addresses.simple;

import cz.muni.fi.pv168.addresses.Address;
import cz.muni.fi.pv168.addresses.AddressFinder;
import cz.muni.fi.pv168.addresses.AddressTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Very simple {@link AddressFinder} implementation, which stores addresses in {@link List}.
 *
 * There is no optimization for improving search performance. {@link #findAddress(Address)}
 * has to iterate over all the addresses every time.
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
