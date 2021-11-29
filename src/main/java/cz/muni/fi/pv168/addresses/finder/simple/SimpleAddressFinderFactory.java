package cz.muni.fi.pv168.addresses.finder.simple;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

import java.io.IOException;

/**
 * Factory for {@link SimpleAddressFinder}.
 */
public final class SimpleAddressFinderFactory extends AddressFinderFactory {

    private final SearchStrategy searchStrategy;

    public SimpleAddressFinderFactory(DataLoader dataLoader, SearchStrategy searchStrategy) {
        super(dataLoader);
        this.searchStrategy = searchStrategy;
    }

    @Override
    public AddressFinder newAddressFinder() throws IOException {
        return new SimpleAddressFinder(getDataLoader().loadData(), searchStrategy);
    }
}
