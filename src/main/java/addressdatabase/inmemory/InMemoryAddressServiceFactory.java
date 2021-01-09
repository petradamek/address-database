package addressdatabase.inmemory;

import addressdatabase.AddressService;
import addressdatabase.AddressServiceFactory;
import java.io.IOException;

/**
 *
 * @author petr
 */
public class InMemoryAddressServiceFactory extends AddressServiceFactory {

    private AddressGroupFactory addressGroupFactory;

    public void setAddressGroupFactory(AddressGroupFactory addressGroupFactory) {
        this.addressGroupFactory = addressGroupFactory;
    }    
    
    @Override
    public AddressService newAddressService() throws IOException {
        InMemoryAddressService service = new InMemoryAddressService();
        getDataLoader().loadData(service.newAddressHandler(addressGroupFactory));
        return service;
    }    
    
}
