package cz.muni.fi.pv168.addresses.finder.simple;

import cz.muni.fi.pv168.addresses.finder.AddressMatcher;
import cz.muni.fi.pv168.addresses.model.Address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ForEachSearchStrategy implements SearchStrategy {

    @Override
    public Collection<Address> findAddress(List<Address> addresses, Address address) {
        List<Address> result = new ArrayList<>();
        for (Address searchedAddress : addresses) {
            if (AddressMatcher.match(address, searchedAddress)) {
                result.add(searchedAddress);
            }
        }
        return result;
    }
}
