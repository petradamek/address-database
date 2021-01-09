package addressdatabase;

import addressdatabase.loader.DataLoader;

import java.io.IOException;
import java.util.Objects;

/**
 * @author petr
 */
public abstract class AddressServiceFactory {

    private final DataLoader dataLoader;

    public AddressServiceFactory(DataLoader dataLoader) {
        this.dataLoader = Objects.requireNonNull(dataLoader, "dataLoader is null");
    }

    protected DataLoader getDataLoader() {
        return dataLoader;
    }

    public abstract AddressService newAddressService() throws IOException;
}
