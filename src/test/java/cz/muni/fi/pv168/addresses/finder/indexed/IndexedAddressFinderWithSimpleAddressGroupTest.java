package cz.muni.fi.pv168.addresses.finder.indexed;

import cz.muni.fi.pv168.addresses.finder.AddressFinderAbstractTest;
import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

import static org.junit.jupiter.api.Assertions.*;

class IndexedAddressFinderWithSimpleAddressGroupTest extends AddressFinderAbstractTest {

    @Override
    protected AddressFinderFactory newAddressFinderFactory(DataLoader dataLoader) {
        return new IndexedAddressFinderFactory(dataLoader, new SimpleAddressGroup.Factory());
    }
}