package cz.muni.fi.pv168.addresses.finder.simple;

import cz.muni.fi.pv168.addresses.finder.AddressFinderAbstractTest;
import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

class SimpleAddressFinderWithParallelStreamSearchStrategyTest extends AddressFinderAbstractTest {

    @Override
    protected AddressFinderFactory newAddressFinderFactory(DataLoader dataLoader) {
        return new SimpleAddressFinderFactory(dataLoader, new ParallelStreamSearchStrategy());
    }
}