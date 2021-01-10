package cz.muni.fi.pv168.addresses.model;

import org.junit.Test;

import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.DESCRIPTIVE_NO;
import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.REGISTRATION_NO;
import static org.junit.Assert.assertEquals;

/**
 * @author petr
 */
public class AbstractAddressTest {

    @Test
    public void testToString() {

        Address address;

        address = Address.builder()
                .municipality("Chvalovice")
                .build();

        assertEquals("Chvalovice", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertEquals("Chvalovice č.p. 10", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, REGISTRATION_NO)
                .build();

        assertEquals("Chvalovice ev.č. 10", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, REGISTRATION_NO)
                .postCode("602 00")
                .build();

        assertEquals("Chvalovice ev.č. 10, 602 00", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, REGISTRATION_NO)
                .build();

        assertEquals("Hatě ev.č. 10, Chvalovice", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertEquals("Hatě č.p. 10, Chvalovice", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .postCode("669 02")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertEquals("Hatě č.p. 10, 669 02 Chvalovice", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .post("Znojmo 2")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertEquals("Hatě č.p. 10, Chvalovice, pošta Znojmo 2", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .post("Znojmo 2")
                .district("Znojmo")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertEquals("Hatě č.p. 10, Chvalovice, pošta Znojmo 2, okres Znojmo", address.toString());

        address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .post("Znojmo 2")
                .district("Znojmo")
                .postCode("669 02")
                .build();

        assertEquals("Hatě č.p. 10, 669 02 Chvalovice, pošta Znojmo 2, okres Znojmo", address.toString());

        address = Address.builder()
                .street("Botanická")
                .houseNo(554, DESCRIPTIVE_NO)
                .orientationNo("68a")
                .municipality("Brno")
                .municipalDistrict("Ponava")
                .postCode("602 00")
                .district("Brno-město")
                .post("Brno 2")
                .build();

        assertEquals("Botanická 554/68a, 602 00 Brno, Ponava, pošta Brno 2, okres Brno-město", address.toString());
    }
}
