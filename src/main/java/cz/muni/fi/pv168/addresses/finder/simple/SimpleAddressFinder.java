package cz.muni.fi.pv168.addresses.finder.simple;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.model.Address;

import java.util.Collection;
import java.util.List;

/**
 * Very simple {@link AddressFinder} implementation, which stores addresses in {@link List}.
 *
 * Does not use optimized data structure for improving search performance. {@link #findAddress(Address)}
 * has to iterate over all the addresses every time. The concrete way of traversing the addresses
 * (e.g. single-threaded or multi-threaded) is defined by {@link SearchStrategy}.
 */
final class SimpleAddressFinder implements AddressFinder {

    private final List<Address> addresses;
    private final SearchStrategy searchStrategy;

    SimpleAddressFinder(List<Address> addresses, SearchStrategy searchStrategy) {
        this.addresses = addresses;
        this.searchStrategy = searchStrategy;
    }

    @Override
    public Collection<Address> findAddress(Address address) {
        return searchStrategy.findAddress(addresses, address);
    }
}
