package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.Address.HouseNoType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author petr
 */
public class AddressToolsTest {

    private Address emptyAddress() {
        Address result = mock(Address.class);
        when(result.getDistrict()).thenReturn(null);
        when(result.getHouseNo()).thenReturn(null);
        when(result.getHouseNoType()).thenReturn(null);
        when(result.getMunicipalDistrict()).thenReturn(null);
        when(result.getMunicipality()).thenReturn(null);
        when(result.getOrientationNo()).thenReturn(null);
        when(result.getPost()).thenReturn(null);
        when(result.getPostCode()).thenReturn(null);
        when(result.getStreet()).thenReturn(null);
        return result;
    }

    @Test
    public void testMatch() {

        Address source, targetChvalovice, targetBotanicka, emptyAddress;

        emptyAddress = emptyAddress();
        assertTrue(AddressTools.match(emptyAddress, emptyAddress));

        source = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                newAddress();

        targetChvalovice = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setMunicipalDistrict("Hatě").
                setHouseNo(10).
                setPost("Znojmo 2").
                setDistrict("Znojmo").
                setPostCode("669 02").
                newAddress();

        assertTrue(AddressTools.match(source, targetChvalovice));

        source = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setHouseNo(10).
                newAddress();

        assertTrue(AddressTools.match(source, targetChvalovice));

        source = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setHouseNo(10).
                setHouseNoType(HouseNoType.REGISTRATION_NO).
                newAddress();

        assertFalse(AddressTools.match(source, targetChvalovice));

        source = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setOrientationNo("10").
                setHouseNo(10).
                newAddress();

        assertFalse(AddressTools.match(source, targetChvalovice));

        targetBotanicka = AddressFactory.newInstance().
                setStreet("Botanická").
                setHouseNo(554).
                setOrientationNo("68a").
                setMunicipality("Brno").
                setMunicipalDistrict("Ponava").
                setPostCode("602 00").
                setDistrict("Brno-město").
                setPost("Brno 2").
                newAddress();

        source = AddressFactory.newInstance().
                setMunicipality("Brno").
                setOrientationNo("68a").
                newAddress();

        assertTrue(AddressTools.match(source, targetBotanicka));

        source = AddressFactory.newInstance().
                setStreet("Botanická").
                newAddress();

        assertTrue(AddressTools.match(source, targetBotanicka));

        source = AddressFactory.newInstance().
                setStreet("BOTANICKÁ").
                newAddress();

        assertTrue(AddressTools.match(source, targetBotanicka));
    }

    @Test
    public void testIsEmpty() {

        Address address;

        address = emptyAddress();
        assertTrue(AddressTools.isEmpty(address));

//        address = AddressFactory.newInstance().setDistrict("X").newAddress();
//        assertFalse(AddressTools.isEmpty(address));        

        address = emptyAddress();
        when(address.getHouseNo()).thenReturn(1);
        assertFalse(AddressTools.isEmpty(address));

        address = emptyAddress();
        when(address.getHouseNoType()).thenReturn(HouseNoType.DESCRIPTIVE_NO);
        assertFalse(AddressTools.isEmpty(address));

        address = emptyAddress();
        when(address.getOrientationNo()).thenReturn("1a");
        assertFalse(AddressTools.isEmpty(address));

        address = AddressFactory.newInstance().setMunicipalDistrict("X").newAddress();
        assertFalse(AddressTools.isEmpty(address));

        address = AddressFactory.newInstance().setMunicipality("X").newAddress();
        assertFalse(AddressTools.isEmpty(address));

        address = AddressFactory.newInstance().setStreet("X").newAddress();
        assertFalse(AddressTools.isEmpty(address));

        address = AddressFactory.newInstance().setPost("X").newAddress();
        assertFalse(AddressTools.isEmpty(address));

        address = AddressFactory.newInstance().setPostCode("111 00").newAddress();
        assertFalse(AddressTools.isEmpty(address));
    }
}
