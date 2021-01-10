package cz.muni.fi.pv168.addresses.finder;

import cz.muni.fi.pv168.addresses.model.Address;
import org.junit.jupiter.api.Test;

import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.DESCRIPTIVE_NO;
import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.REGISTRATION_NO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class AddressMatcherTest {

    @Test
    void emptyToEmpty() {
        assertThat(AddressMatcher.match(emptyAddress(), emptyAddress()))
                .isTrue();
    }

    @Test
    void municipality() {
        var source = Address.builder()
                .municipality("Chvalovice")
                .build();

        var target = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .post("Znojmo 2")
                .district("Znojmo")
                .postCode("669 02")
                .build();

        assertThat(AddressMatcher.match(source, target))
                .isTrue();
    }

    @Test
    void municipalityAndDescriptiveNo() {
        var source = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        var target = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .post("Znojmo 2")
                .district("Znojmo")
                .postCode("669 02")
                .build();

        assertThat(AddressMatcher.match(source, target))
                .isTrue();
    }

    @Test
    void differentHousNoType() {
        var source = Address.builder()
                .municipality("Chvalovice")
                .houseNo(10, REGISTRATION_NO)
                .build();

        var target = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .post("Znojmo 2")
                .district("Znojmo")
                .postCode("669 02")
                .build();

        assertThat(AddressMatcher.match(source, target))
                .isFalse();
    }

    @Test
    void noOrientationNumberInTarget() {
        var source = Address.builder()
                .municipality("Chvalovice")
                .orientationNo("10")
                .houseNo(10, DESCRIPTIVE_NO)
                .build();

        var target = Address.builder()
                .municipality("Chvalovice")
                .municipalDistrict("Hatě")
                .houseNo(10, DESCRIPTIVE_NO)
                .post("Znojmo 2")
                .district("Znojmo")
                .postCode("669 02")
                .build();

        assertThat(AddressMatcher.match(source, target))
                .isFalse();
    }

    @Test
    void municipalityAndOrientationNo() {
        var source = Address.builder()
                .municipality("Brno")
                .orientationNo("68a")
                .build();

        var target = Address.builder()
                .street("Botanická")
                .houseNo(554, DESCRIPTIVE_NO)
                .orientationNo("68a")
                .municipality("Brno")
                .municipalDistrict("Ponava")
                .postCode("602 00")
                .district("Brno-město")
                .post("Brno 2")
                .build();

        assertThat(AddressMatcher.match(source, target))
                .isTrue();
    }

    @Test
    void street() {
        var source = Address.builder()
                .street("Botanická")
                .build();

        var target = Address.builder()
                .street("Botanická")
                .houseNo(554, DESCRIPTIVE_NO)
                .orientationNo("68a")
                .municipality("Brno")
                .municipalDistrict("Ponava")
                .postCode("602 00")
                .district("Brno-město")
                .post("Brno 2")
                .build();

        assertThat(AddressMatcher.match(source, target))
                .isTrue();
    }

    @Test
    void streetCaseInsensitive() {
        var source = Address.builder()
                .street("BOTANICKÁ")
                .build();

        var target = Address.builder()
                .street("Botanická")
                .houseNo(554, DESCRIPTIVE_NO)
                .orientationNo("68a")
                .municipality("Brno")
                .municipalDistrict("Ponava")
                .postCode("602 00")
                .district("Brno-město")
                .post("Brno 2")
                .build();

        assertThat(AddressMatcher.match(source, target))
                .isTrue();
    }

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
}
