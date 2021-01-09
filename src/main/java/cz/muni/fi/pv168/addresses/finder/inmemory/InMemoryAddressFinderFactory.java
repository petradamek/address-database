package cz.muni.fi.pv168.addresses.finder.inmemory;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

import java.io.IOException;
import java.util.Objects;

/**
 * Factory for {@link InMemoryAddressFinder}.
 */
public final class InMemoryAddressFinderFactory extends AddressFinderFactory {

    private final AddressGroupFactory addressGroupFactory;

    public InMemoryAddressFinderFactory(DataLoader dataLoader, AddressGroupFactory addressGroupFactory) {
        super(dataLoader);
        this.addressGroupFactory = Objects.requireNonNull(addressGroupFactory, "addressGroupFactory is null");
    }

    @Override
    public AddressFinder newAddressFinder() throws IOException {
        InMemoryAddressFinder service = new InMemoryAddressFinder();
        getDataLoader().loadData(service.newAddressHandler(addressGroupFactory));
        return service;
    }
}
