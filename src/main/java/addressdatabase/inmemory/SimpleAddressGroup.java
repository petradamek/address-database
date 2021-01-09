/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addressdatabase.inmemory;

import addressdatabase.AbstractAddress;
import addressdatabase.Address;
import addressdatabase.Address.HouseNoType;
import addressdatabase.AddressBase;
import addressdatabase.AddressTools;
import java.util.*;

/**
 *
 * @author Petr Adámek
 */
public class SimpleAddressGroup implements AddressGroup {

    public static class Factory implements AddressGroupFactory {

        @Override
        public AddressGroup newAddressGroup(AddressBase addressBase) {
            return new SimpleAddressGroup(addressBase);
        }
        
    }
    
    private AddressBase addressBase;
    private List<Address> addresses = new ArrayList<Address>();
    
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
        List<Address> result = new ArrayList<Address>();
        for (Address searchedAddress : addresses) {
            if (AddressTools.matchNumbers(orientationNo, houseNo, houseNoType, searchedAddress)) {
                result.add(searchedAddress);
            }
        }
        return result;
    }

    private class AddressImpl extends AbstractAddress {

        private String orientationNo;
        private Integer houseNo;    
        private HouseNoType houseNoType;

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
