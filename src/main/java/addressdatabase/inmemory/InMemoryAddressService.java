package addressdatabase.inmemory;

import addressdatabase.Address;
import addressdatabase.Address.HouseNoType;
import addressdatabase.AddressBase;
import addressdatabase.AddressService;
import addressdatabase.AddressTools;
import addressdatabase.loader.AddressHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is standard implementation of AddressService that keeps data in memory
 *
 * @author petr
 */
public class InMemoryAddressService implements AddressService {

    private Map<String, List<AddressGroup>> addressGroupsByStreet = new HashMap<String, List<AddressGroup>>();
    private Map<String, List<AddressGroup>> addressGroupsByMunicipality = new HashMap<String, List<AddressGroup>>();
    private Map<String, List<AddressGroup>> addressGroupsByMunicipalDistrict = new HashMap<String, List<AddressGroup>>();

    InMemoryAddressService() {
    }
//    private Map<String,List<AddressGroup>> addressGroupsByPost = 
//            new HashMap<String, List<AddressGroup>>();            
//    private Map<String,List<AddressGroup>> addressGroupsByPostCode = 
//            new HashMap<String, List<AddressGroup>>();            

    private static <K> List<AddressGroup> getAddressGroup(Map<K, List<AddressGroup>> addressGroups, K key) {
        List<AddressGroup> result = addressGroups.get(key);
        if (result == null) {
            result = new ArrayList<AddressGroup>();
            addressGroups.put(key, result);
        }
        return result;
    }

    AddressHandler newAddressHandler(AddressGroupFactory addressGroupFactory) {
        return new AddressHandlerImpl(addressGroupFactory);
    }

    private String toUpperCase(String s) {
        if (s == null) {
            return null;
        } else {
            return s.toUpperCase();
        }
    }

    private void addAddressGroup(AddressGroup addressGroup) {
        getAddressGroup(addressGroupsByStreet,
                toUpperCase(addressGroup.getAddressBase().getStreet())).add(addressGroup);
        getAddressGroup(addressGroupsByMunicipality,
                toUpperCase(addressGroup.getAddressBase().getMunicipality())).add(addressGroup);
        getAddressGroup(addressGroupsByMunicipalDistrict,
                toUpperCase(addressGroup.getAddressBase().getMunicipalDistrict())).add(addressGroup);
    }

    private List<AddressGroup> findAddressGroup(Address address) {
        List<AddressGroup> prefilteredAddressGroup;
        if (address.getStreet() != null) {
            prefilteredAddressGroup = addressGroupsByStreet.get(toUpperCase(address.getStreet()));
        } else if (address.getMunicipality() != null) {
            prefilteredAddressGroup = addressGroupsByMunicipality.get(toUpperCase(address.getMunicipality()));
        } else if (address.getMunicipalDistrict() != null) {
            prefilteredAddressGroup = addressGroupsByMunicipalDistrict.get(toUpperCase(address.getMunicipalDistrict()));
        } else {
            throw new IllegalArgumentException("At least street or municipal or municipalDistrict must be specified");
        }
        List<AddressGroup> result = new ArrayList<AddressGroup>();
        for (AddressGroup searchedAddressGroup : prefilteredAddressGroup) {
            if (AddressTools.match(address, searchedAddressGroup.getAddressBase())) {
                result.add(searchedAddressGroup);
            }
        }
        return result;
    }

    @Override
    public Collection<Address> findAddress(Address address) {
        Collection<AddressGroup> addressGroups = findAddressGroup(address);
        List<Address> result = new ArrayList<Address>();
        for (AddressGroup addressGroup : addressGroups) {
            result.addAll(addressGroup.findAddress(
                    address.getOrientationNo(),
                    address.getHouseNo(),
                    address.getHouseNoType()));
        }
        return result;
    }

    private class AddressHandlerImpl implements AddressHandler {

        private AddressGroupFactory addressGroupFactory;
        private Map<AddressBase, AddressGroup> addressGroups = new HashMap<AddressBase, AddressGroup>();

        public AddressHandlerImpl(AddressGroupFactory addressGroupFactory) {
            this.addressGroupFactory = addressGroupFactory;
        }

        @Override
        public void processAddress(AddressBase addressBase, String orientationNo, Integer houseNo, HouseNoType houseNoType) {
            AddressGroup addressGroup = getAddressGroup(addressBase);
            addressGroup.addAddress(orientationNo, houseNo, houseNoType);
        }

        private AddressGroup getAddressGroup(AddressBase addressBase) {
            AddressGroup addressGroup = addressGroups.get(addressBase);
            if (addressGroup == null) {
                addressGroup = addressGroupFactory.newAddressGroup(addressBase);
                addressGroups.put(addressBase, addressGroup);
                addAddressGroup(addressGroup);
            }
            return addressGroup;
        }
    }
}
