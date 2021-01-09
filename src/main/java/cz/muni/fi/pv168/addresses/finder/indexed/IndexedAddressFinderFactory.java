package cz.muni.fi.pv168.addresses.finder.indexed;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

import java.io.IOException;
import java.util.Objects;

/**
 * Factory for {@link IndexedAddressFinder}.
 */
public final class IndexedAddressFinderFactory extends AddressFinderFactory {

    private final AddressGroupFactory addressGroupFactory;

    public IndexedAddressFinderFactory(DataLoader dataLoader, AddressGroupFactory addressGroupFactory) {
        super(dataLoader);
        this.addressGroupFactory = Objects.requireNonNull(addressGroupFactory, "addressGroupFactory is null");
    }

    @Override
    public AddressFinder newAddressFinder() throws IOException {
        IndexedAddressFinder service = new IndexedAddressFinder();
        getDataLoader().loadData(service.newAddressHandler(addressGroupFactory));
        return service;
    }
}
