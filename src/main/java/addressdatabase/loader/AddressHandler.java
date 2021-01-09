package addressdatabase.loader;

import addressdatabase.Address;
import addressdatabase.AddressBase;

/**
 * @author petr
 */
public interface AddressHandler {

    void processAddress(
            AddressBase addressBase,
            String orientationNo,
            Integer houseNo,
            Address.HouseNoType houseNoType);
}
