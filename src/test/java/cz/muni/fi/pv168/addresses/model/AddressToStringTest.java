package cz.muni.fi.pv168.addresses.model;

import org.junit.jupiter.api.Test;

import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.DESCRIPTIVE_NO;
import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.REGISTRATION_NO;
import static org.assertj.core.api.Assertions.assertThat;

final class AddressToStringTest {

    @Test
    void municipality() {

        var address = Address.builder()
                .municipality("Chvalovice")
                .build();

        assertThat(address.toString())
                .isEqualTo("Chvalovice");
    }

    @Test
    void municipalityAndDescriptiveNo() {

        var address = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertThat(address.toString())
                .isEqualTo("Chvalovice č.p. 10");
    }

    @Test
    void municipalityAndRegistrationNo() {
        var address = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, REGISTRATION_NO)
                .build();

        assertThat(address.toString())
                .isEqualTo("Chvalovice ev.č. 10");
    }

    @Test
    void municipalityAndRegistrationNoAndZip() {
        var address = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, REGISTRATION_NO)
                .postCode("602 00")
                .build();

        assertThat(address.toString())
                .isEqualTo("Chvalovice ev.č. 10, 602 00");
    }

    @Test
    void municipalityAndMunicipalityDistrictAndRegistrationNo() {
        var address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, REGISTRATION_NO)
                .build();

        assertThat(address.toString())
                .isEqualTo("Hatě ev.č. 10, Chvalovice");
    }

    @Test
    void municipalityAndMunicipalityDistrictAndDescriptiveNo() {
        var address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertThat(address.toString())
                .isEqualTo("Hatě č.p. 10, Chvalovice");

    }

    @Test
    void municipalityAndMunicipalityDistrictAndDescriptiveNoAndZip() {
        var address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .postCode("669 02")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertThat(address.toString())
                .isEqualTo("Hatě č.p. 10, 669 02 Chvalovice");

    }

    @Test
    void municipalityAndMunicipalityDistrictAndDescriptiveNoAndPost() {
        var address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .post("Znojmo 2")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertThat(address.toString())
                .isEqualTo("Hatě č.p. 10, Chvalovice, pošta Znojmo 2");

    }

    @Test
    void municipalityAndMunicipalityDistrictAndDescriptiveNoAndPostAndPostAndDistrict() {
        var address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .post("Znojmo 2")
                .district("Znojmo")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        assertThat(address.toString())
                .isEqualTo("Hatě č.p. 10, Chvalovice, pošta Znojmo 2, okres Znojmo");

    }

    @Test
    void fullAddressWithoutStreet() {
        var address = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .post("Znojmo 2")
                .district("Znojmo")
                .postCode("669 02")
                .build();

        assertThat(address.toString())
                .isEqualTo("Hatě č.p. 10, 669 02 Chvalovice, pošta Znojmo 2, okres Znojmo");
    }

    @Test
    void fullAddressWithBothNumbers() {

        var address = Address.builder()
                .street("Botanická")
                .houseNo(554, DESCRIPTIVE_NO)
                .orientationNo("68a")
                .municipality("Brno")
                .municipalDistrict("Ponava")
                .postCode("602 00")
                .district("Brno-město")
                .post("Brno 2")
                .build();

        assertThat(address.toString())
                .isEqualTo("Botanická 554/68a, 602 00 Brno, Ponava, pošta Brno 2, okres Brno-město");
    }
}
