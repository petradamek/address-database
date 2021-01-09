package addressdatabase.inmemory;

import addressdatabase.AddressFinder;
import addressdatabase.AddressFinderFactory;
import addressdatabase.loader.DataLoader;

import java.io.IOException;
import java.util.Objects;

/**
 * @author petr
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
