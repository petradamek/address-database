package addressdatabase.inmemory;

import addressdatabase.AddressService;
import addressdatabase.AddressServiceFactory;
import addressdatabase.loader.DataLoader;

import java.io.IOException;
import java.util.Objects;

/**
 * @author petr
 */
public final class InMemoryAddressServiceFactory extends AddressServiceFactory {

    private final AddressGroupFactory addressGroupFactory;

    public InMemoryAddressServiceFactory(DataLoader dataLoader, AddressGroupFactory addressGroupFactory) {
        super(dataLoader);
        this.addressGroupFactory = Objects.requireNonNull(addressGroupFactory, "addressGroupFactory is null");
    }

    @Override
    public AddressService newAddressService() throws IOException {
        InMemoryAddressService service = new InMemoryAddressService();
        getDataLoader().loadData(service.newAddressHandler(addressGroupFactory));
        return service;
    }
}
