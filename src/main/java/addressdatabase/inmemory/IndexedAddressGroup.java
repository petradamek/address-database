/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addressdatabase.inmemory;

import addressdatabase.Address;
import addressdatabase.Address.HouseNoType;
import addressdatabase.AddressBase;
import addressdatabase.AddressTools;
import addressdatabase.SimpleAddress;
import java.util.*;

/**
 *
 * @author Petr Adámek
 */
class IndexedAddressGroup implements AddressGroup {

    static class Factory implements AddressGroupFactory {

        @Override
        public AddressGroup newAddressGroup(AddressBase addressBase) {
            return new IndexedAddressGroup(addressBase);
        }
        
    }    
    
    private AddressBase addressBase;
    private Map<Integer, Set<Address>> addressByHouseNo = new HashMap<Integer, Set<Address>>();
    private Map<String, Set<Address>> addressByOrientationNo = new HashMap<String, Set<Address>>();
    private Set<Address> allAddresses = new HashSet<Address>();
    
    IndexedAddressGroup(AddressBase addressBase) {
        this.addressBase = addressBase;
    }

    public AddressBase getAddressBase() {
        return addressBase;
    }    
    
    private static <K> Set<Address> getAddressSet(
            Map<K, Set<Address>> addresses,
            K key) {
        Set<Address> result = addresses.get(key);
        if (result == null) {
            result = new HashSet<Address>();
            addresses.put(key,result);
        }
        return result;        
    }
    
    @Override
    public void addAddress(String orientationNo, Integer houseNo, HouseNoType houseNoType) {
        Address address = new SimpleAddress(addressBase, orientationNo, houseNo, houseNoType);
        getAddressSet(addressByHouseNo, houseNo).add(address);
        getAddressSet(addressByOrientationNo, orientationNo).add(address);
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
        Set<Address> result = new HashSet<Address>();
        for (Address searchedAddress : preFilteredAddresses) {
            if (AddressTools.matchNumbers(orientationNo, houseNo, houseNoType, searchedAddress)) {
                result.add(searchedAddress);
            }
        }
        return result;
    }
    
    
    
}
