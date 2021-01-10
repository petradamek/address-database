package cz.muni.fi.pv168.addresses.finder.simple;

import cz.muni.fi.pv168.addresses.finder.AddressMatcher;
import cz.muni.fi.pv168.addresses.model.Address;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StreamSearchStrategy implements SearchStrategy {

    @Override
    public Collection<Address> findAddress(List<Address> addresses, Address address) {
        return addresses.stream()
                .filter(searchedAddress -> AddressMatcher.match(address, searchedAddress))
                .collect(Collectors.toList());
    }
}
