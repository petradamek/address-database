package cz.muni.fi.pv168.addresses.finder.indexed;

import cz.muni.fi.pv168.addresses.model.AbstractAddress;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.Address.HouseNoType;
import cz.muni.fi.pv168.addresses.model.AddressBase;
import cz.muni.fi.pv168.addresses.finder.AddressMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Petr Adámek
 */
public class SimpleAddressGroup implements AddressGroup {

    private final AddressBase addressBase;
    private final List<Address> addresses = new ArrayList<>();

    SimpleAddressGroup(AddressBase addressBase) {
        this.addressBase = addressBase;
    }

    @Override
    public AddressBase getAddressBase() {
        return addressBase;
    }

    @Override
    public void addAddress(String orientationNo, Integer houseNo, HouseNoType houseNoType) {
        Address address = new AddressImpl(orientationNo, houseNo, houseNoType);
        addresses.add(address);
    }

    @Override
    public Collection<Address> findAddress(String orientationNo, Integer houseNo, HouseNoType houseNoType) {
        List<Address> result = new ArrayList<>();
        for (Address searchedAddress : addresses) {
            if (AddressMatcher.matchNumbers(orientationNo, houseNo, houseNoType, searchedAddress)) {
                result.add(searchedAddress);
            }
        }
        return result;
    }

    public static class Factory implements AddressGroupFactory {

        @Override
        public AddressGroup newAddressGroup(AddressBase addressBase) {
            return new SimpleAddressGroup(addressBase);
        }

    }

    private class AddressImpl extends AbstractAddress {

        private final String orientationNo;
        private final Integer houseNo;
        private final HouseNoType houseNoType;

        public AddressImpl(String orientationNo, Integer houseNo, HouseNoType houseNoType) {
            this.orientationNo = orientationNo;
            this.houseNo = houseNo;
            this.houseNoType = houseNoType;
        }

        @Override
        public String getStreet() {
            return addressBase.getStreet();
        }

        @Override
        public Integer getHouseNo() {
            return houseNo;
        }

        @Override
        public HouseNoType getHouseNoType() {
            return houseNoType;
        }

        @Override
        public String getOrientationNo() {
            return orientationNo;
        }


        @Override
        public String getMunicipality() {
            return addressBase.getMunicipality();
        }

        @Override
        public String getMunicipalDistrict() {
            return addressBase.getMunicipalDistrict();
        }

        @Override
        public String getPostCode() {
            return addressBase.getPostCode();
        }

        @Override
        public String getPost() {
            return addressBase.getPost();
        }

        @Override
        public String getDistrict() {
            return addressBase.getDistrict();
        }
    }
}
