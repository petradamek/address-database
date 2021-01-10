package cz.muni.fi.pv168.addresses.finder;

import cz.muni.fi.pv168.addresses.model.Address;
import org.junit.Test;

import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.DESCRIPTIVE_NO;
import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.REGISTRATION_NO;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressMatcherTest {

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
        assertTrue(AddressMatcher.match(emptyAddress, emptyAddress));

        source = Address.builder()
                .municipality("Chvalovice")
                .build();

        targetChvalovice = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .post("Znojmo 2")
                .district("Znojmo")
                .postCode("669 02")
                .build();

        assertTrue(AddressMatcher.match(source, targetChvalovice));

        source = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertTrue(AddressMatcher.match(source, targetChvalovice));

        source = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, REGISTRATION_NO)
                .build();

        assertFalse(AddressMatcher.match(source, targetChvalovice));

        source = Address.builder()
                .municipality("Chvalovice")
                .orientationNo("10")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertFalse(AddressMatcher.match(source, targetChvalovice));

        targetBotanicka = Address.builder()
                .street("Botanická")
                .houseNo(554, DESCRIPTIVE_NO)
                .orientationNo("68a")
                .municipality("Brno")
                .municipalDistrict("Ponava")
                .postCode("602 00")
                .district("Brno-město")
                .post("Brno 2")
                .build();

        source = Address.builder()
                .municipality("Brno")
                .orientationNo("68a")
                .build();

        assertTrue(AddressMatcher.match(source, targetBotanicka));

        source = Address.builder()
                .street("Botanická")
                .build();

        assertTrue(AddressMatcher.match(source, targetBotanicka));

        source = Address.builder()
                .street("BOTANICKÁ")
                .build();

        assertTrue(AddressMatcher.match(source, targetBotanicka));
    }
}
