package addressdatabase;

import addressdatabase.loader.DataLoader;

import java.io.IOException;
import java.util.Objects;

/**
 * Factory for {@link AddressFinder}.
 */
public abstract class AddressFinderFactory {

    private final DataLoader dataLoader;

    public AddressFinderFactory(DataLoader dataLoader) {
        this.dataLoader = Objects.requireNonNull(dataLoader, "dataLoader is null");
    }

    protected DataLoader getDataLoader() {
        return dataLoader;
    }

    public abstract AddressFinder newAddressFinder() throws IOException;
}
