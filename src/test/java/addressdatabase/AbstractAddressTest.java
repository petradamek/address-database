package addressdatabase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author petr
 */
public class AbstractAddressTest {

    @Test
    public void testToString() {

        Address address;

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                newAddress();

        assertEquals("Chvalovice", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setHouseNo(10).
                newAddress();

        assertEquals("Chvalovice č.p. 10", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setHouseNo(10).
                setHouseNoType(Address.HouseNoType.REGISTRATION_NO).
                newAddress();

        assertEquals("Chvalovice ev.č. 10", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setHouseNo(10).
                setHouseNoType(Address.HouseNoType.REGISTRATION_NO).
                setPostCode("602 00").
                newAddress();

        assertEquals("Chvalovice ev.č. 10, 602 00", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setMunicipalDistrict("Hatě").
                setHouseNo(10).
                setHouseNoType(Address.HouseNoType.REGISTRATION_NO).
                newAddress();

        assertEquals("Hatě ev.č. 10, Chvalovice", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setMunicipalDistrict("Hatě").
                setHouseNo(10).
                newAddress();

        assertEquals("Hatě č.p. 10, Chvalovice", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setMunicipalDistrict("Hatě").
                setPostCode("669 02").
                setHouseNo(10).
                newAddress();

        assertEquals("Hatě č.p. 10, 669 02 Chvalovice", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setMunicipalDistrict("Hatě").
                setPost("Znojmo 2").
                setHouseNo(10).
                newAddress();

        assertEquals("Hatě č.p. 10, Chvalovice, pošta Znojmo 2", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setMunicipalDistrict("Hatě").
                setPost("Znojmo 2").
                setDistrict("Znojmo").
                setHouseNo(10).
                newAddress();

        assertEquals("Hatě č.p. 10, Chvalovice, pošta Znojmo 2, okres Znojmo", address.toString());

        address = AddressFactory.newInstance().
                setMunicipality("Chvalovice").
                setMunicipalDistrict("Hatě").
                setHouseNo(10).
                setPost("Znojmo 2").
                setDistrict("Znojmo").
                setPostCode("669 02").
                newAddress();

        assertEquals("Hatě č.p. 10, 669 02 Chvalovice, pošta Znojmo 2, okres Znojmo", address.toString());

        address = AddressFactory.newInstance().
                setStreet("Botanická").
                setHouseNo(554).
                setOrientationNo("68a").
                setMunicipality("Brno").
                setMunicipalDistrict("Ponava").
                setPostCode("602 00").
                setDistrict("Brno-město").
                setPost("Brno 2").
                newAddress();

        assertEquals("Botanická 554/68a, 602 00 Brno, Ponava, pošta Brno 2, okres Brno-město", address.toString());
    }
}
