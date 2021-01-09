package addressdatabase.inmemory;

import addressdatabase.Address;
import addressdatabase.AddressBase;
import java.util.Collection;

/**
 *
 * @author Petr Adámek
 */
interface AddressGroup {
    
    AddressBase getAddressBase();
    
    void addAddress(String orientationNo, 
            Integer houseNo, Address.HouseNoType houseNoType);
    
    Collection<Address> findAddress(String orientationNo, 
            Integer houseNo, Address.HouseNoType houseNoType);

}
