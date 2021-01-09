package cz.muni.fi.pv168.addresses.finder.inmemory;

import cz.muni.fi.pv168.addresses.Address;
import cz.muni.fi.pv168.addresses.AddressBase;

import java.util.Collection;

/**
 * @author Petr Adámek
 */
interface AddressGroup {

    AddressBase getAddressBase();

    void addAddress(String orientationNo, Integer houseNo, Address.HouseNoType houseNoType);

    Collection<Address> findAddress(String orientationNo, Integer houseNo, Address.HouseNoType houseNoType);
}
