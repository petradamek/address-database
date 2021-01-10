package cz.muni.fi.pv168.addresses.loader;

import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.AddressBase;

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
