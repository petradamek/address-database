package cz.muni.fi.pv168.addresses.finder.indexed;

import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.Address.HouseNoType;
import cz.muni.fi.pv168.addresses.model.AddressBase;
import cz.muni.fi.pv168.addresses.AddressTools;
import cz.muni.fi.pv168.addresses.model.SimpleAddress;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Petr Adámek
 */
public class IndexedAddressGroup implements AddressGroup {

    private final AddressBase addressBase;
    private final Map<Integer, Set<Address>> addressByHouseNo = new HashMap<>();
    private final Map<String, Set<Address>> addressByOrientationNo = new HashMap<>();
    private final Set<Address> allAddresses = new HashSet<>();

    IndexedAddressGroup(AddressBase addressBase) {
        this.addressBase = addressBase;
    }

    public AddressBase getAddressBase() {
        return addressBase;
    }

    @Override
    public void addAddress(String orientationNo, Integer houseNo, HouseNoType houseNoType) {
        Address address = new SimpleAddress(addressBase, orientationNo, houseNo, houseNoType);
        addressByHouseNo.computeIfAbsent(houseNo, k1 -> new HashSet<>()).add(address);
        addressByOrientationNo.computeIfAbsent(orientationNo, k -> new HashSet<>()).add(address);
        allAddresses.add(address);
    }

    @Override
    public Collection<Address> findAddress(String orientationNo, Integer houseNo, HouseNoType houseNoType) {
        Collection<Address> preFilteredAddresses;
        if (houseNo != null) {
            preFilteredAddresses = addressByHouseNo.get(houseNo);
        } else if (orientationNo != null) {
            preFilteredAddresses = addressByOrientationNo.get(orientationNo);
        } else {
            preFilteredAddresses = allAddresses;
        }
        if (preFilteredAddresses == null) {
            return Collections.emptySet();
        }
        Set<Address> result = new HashSet<>();
        for (Address searchedAddress : preFilteredAddresses) {
            if (AddressTools.matchNumbers(orientationNo, houseNo, houseNoType, searchedAddress)) {
                result.add(searchedAddress);
            }
        }
        return result;
    }

    public static class Factory implements AddressGroupFactory {

        @Override
        public AddressGroup newAddressGroup(AddressBase addressBase) {
            return new IndexedAddressGroup(addressBase);
        }
    }
}
