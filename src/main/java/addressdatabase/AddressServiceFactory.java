package addressdatabase;

import addressdatabase.loader.DataLoader;

import java.io.IOException;

/**
 * @author petr
 */
public abstract class AddressServiceFactory {

    private DataLoader dataLoader;

    protected DataLoader getDataLoader() {
        return dataLoader;
    }

    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public abstract AddressService newAddressService() throws IOException;
}
