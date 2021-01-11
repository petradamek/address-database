package cz.muni.fi.pv168.addresses.finder.indexed;

import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.AddressBase;

import java.util.Collection;

/**
 * Stores group of addresses with the same {@link AddressBase}.
 *
 * Different implementations could use different data structures for storing data to improve performance.
 */
interface AddressGroup {

    AddressBase getAddressBase();

    void addAddress(String orientationNo, Integer houseNo, Address.HouseNoType houseNoType);

    Collection<Address> findAddress(String orientationNo, Integer houseNo, Address.HouseNoType houseNoType);
}
