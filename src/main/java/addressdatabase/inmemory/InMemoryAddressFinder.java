package addressdatabase.inmemory;

import addressdatabase.Address;
import addressdatabase.Address.HouseNoType;
import addressdatabase.AddressBase;
import addressdatabase.AddressFinder;
import addressdatabase.AddressTools;
import addressdatabase.loader.AddressHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Optimized {@link AddressFinder} implementation.
 *
 * Addresses are stored in multiple {@link Map}s to improve search performance.
 */
final class InMemoryAddressFinder implements AddressFinder {

    private final Map<String, List<AddressGroup>> addressGroupsByStreet = new HashMap<>();
    private final Map<String, List<AddressGroup>> addressGroupsByMunicipality = new HashMap<>();
    private final Map<String, List<AddressGroup>> addressGroupsByMunicipalDistrict = new HashMap<>();

    InMemoryAddressFinder() {
    }
//    private Map<String,List<AddressGroup>> addressGroupsByPost = 
//            new HashMap<String, List<AddressGroup>>();            
//    private Map<String,List<AddressGroup>> addressGroupsByPostCode = 
//            new HashMap<String, List<AddressGroup>>();            

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
        addToMap(addressGroupsByStreet, addressGroup, AddressBase::getStreet);
        addToMap(addressGroupsByMunicipality, addressGroup, AddressBase::getMunicipality);
        addToMap(addressGroupsByMunicipalDistrict, addressGroup, AddressBase::getMunicipalDistrict);
    }

    private void addToMap(Map<String, List<AddressGroup>> map, AddressGroup addressGroup, Function<AddressBase, String> keyExtractor) {
        String key = toUpperCase(keyExtractor.apply(addressGroup.getAddressBase()));
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(addressGroup);
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
        List<AddressGroup> result = new ArrayList<>();
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
        List<Address> result = new ArrayList<>();
        for (AddressGroup addressGroup : addressGroups) {
            result.addAll(addressGroup.findAddress(
                    address.getOrientationNo(),
                    address.getHouseNo(),
                    address.getHouseNoType()));
        }
        return result;
    }

    private class AddressHandlerImpl implements AddressHandler {

        private final AddressGroupFactory addressGroupFactory;
        private final Map<AddressBase, AddressGroup> addressGroups = new HashMap<>();

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
