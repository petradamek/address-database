package cz.muni.fi.pv168.addresses.finder;

import cz.muni.fi.pv168.addresses.loader.AddressHandler;
import cz.muni.fi.pv168.addresses.loader.DataLoader;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.AddressBase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.DESCRIPTIVE_NO;
import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.REGISTRATION_NO;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AddressFinderAbstractTest {

    private static final Address CHVALOVICE_1_CHVALOVICE_ZNOJMO = Address.builder()
            .municipality("Chvalovice")
            .houseNo(1, DESCRIPTIVE_NO)
            .municipalDistrict("Chvalovice")
            .district("Znojmo")
            .post("Znojmo 2")
            .postCode("669 02")
            .build();

    private static final Address CHVALOVICE_33_CHVALOVICE_ZNOJMO = Address.builder()
            .municipality("Chvalovice")
            .houseNo(33, DESCRIPTIVE_NO)
            .municipalDistrict("Chvalovice")
            .district("Znojmo")
            .post("Znojmo 2")
            .postCode("669 02")
            .build();

    private static final Address CHVALOVICE_RN_33_CHVALOVICE_ZNOJMO = Address.builder()
            .municipality("Chvalovice")
            .houseNo(33, REGISTRATION_NO)
            .municipalDistrict("Chvalovice")
            .district("Znojmo")
            .post("Znojmo 2")
            .postCode("669 02")
            .build();

    private static final Address HATE_1_CHVALOVICE_ZNOJMO = Address.builder()
            .municipality("Chvalovice")
            .houseNo(1, DESCRIPTIVE_NO)
            .municipalDistrict("Hatě")
            .district("Znojmo")
            .post("Znojmo 2")
            .postCode("669 02")
            .build();

    private static final Address HATE_33_CHVALOVICE_ZNOJMO = Address.builder()
            .municipality("Chvalovice")
            .houseNo(33, DESCRIPTIVE_NO)
            .municipalDistrict("Hatě")
            .district("Znojmo")
            .post("Znojmo 2")
            .postCode("669 02")
            .build();

    private static final Address HATE_RN_33_CHVALOVICE_ZNOJMO = Address.builder()
            .municipality("Chvalovice")
            .houseNo(33, REGISTRATION_NO)
            .municipalDistrict("Hatě")
            .district("Znojmo")
            .post("Znojmo 2")
            .postCode("669 02")
            .build();

    private static final Address CHVALOVICE_33_CHVALOVICE_PRACHATICE = Address.builder()
            .municipality("Chvalovice")
            .houseNo(33, DESCRIPTIVE_NO)
            .municipalDistrict("Chvalovice")
            .district("Prachatice")
            .post("Netolice")
            .postCode("384 11")
            .build();

    private static final Address CHVALOVICE_RN_33_CHVALOVICE_PRACHATICE = Address.builder()
            .municipality("Chvalovice")
            .houseNo(33, REGISTRATION_NO)
            .municipalDistrict("Chvalovice")
            .district("Prachatice")
            .post("Netolice")
            .postCode("384 11")
            .build();

    private static final Address HATE_1_BECVARY_KOLIN = Address.builder()
            .municipality("Bečváry")
            .houseNo(1, DESCRIPTIVE_NO)
            .municipalDistrict("Hatě")
            .district("Kolín")
            .post("Zásmuky")
            .postCode("281 44")
            .build();

    private static final Address HATE_33_BECVARY_KOLIN = Address.builder()
            .municipality("Bečváry")
            .houseNo(33, DESCRIPTIVE_NO)
            .municipalDistrict("Hatě")
            .district("Kolín")
            .post("Zásmuky")
            .postCode("281 44")
            .build();

    private static final Address HATE_RN_33_BECVARY_KOLIN = Address.builder()
            .municipality("Bečváry")
            .houseNo(33, REGISTRATION_NO)
            .municipalDistrict("Hatě")
            .district("Kolín")
            .post("Zásmuky")
            .postCode("281 44")
            .build();

    private static final Address BOTANICKA_68A_BRNO = Address.builder()
            .municipality("Brno")
            .street("BOTANICKÁ")
            .orientationNo("68A")
            .houseNo(554, REGISTRATION_NO)
            .municipalDistrict("Ponava")
            .district("Brno-město")
            .post("Brno 2")
            .postCode("602 00")
            .build();

    private static final List<Address> ALL_ADDRESSES = List.of(
            CHVALOVICE_1_CHVALOVICE_ZNOJMO,
            CHVALOVICE_33_CHVALOVICE_ZNOJMO,
            CHVALOVICE_RN_33_CHVALOVICE_ZNOJMO,
            HATE_1_CHVALOVICE_ZNOJMO,
            HATE_33_CHVALOVICE_ZNOJMO,
            HATE_RN_33_CHVALOVICE_ZNOJMO,
            CHVALOVICE_33_CHVALOVICE_PRACHATICE,
            CHVALOVICE_RN_33_CHVALOVICE_PRACHATICE,
            HATE_1_BECVARY_KOLIN,
            HATE_33_BECVARY_KOLIN,
            HATE_RN_33_BECVARY_KOLIN,
            BOTANICKA_68A_BRNO
    );

    protected abstract AddressFinderFactory newAddressFinderFactory(DataLoader dataLoader);

    @Test
    void findByMunicipality() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipality("chvalovicE")
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        CHVALOVICE_1_CHVALOVICE_ZNOJMO,
                        CHVALOVICE_33_CHVALOVICE_ZNOJMO,
                        CHVALOVICE_RN_33_CHVALOVICE_ZNOJMO,
                        HATE_1_CHVALOVICE_ZNOJMO,
                        HATE_33_CHVALOVICE_ZNOJMO,
                        HATE_RN_33_CHVALOVICE_ZNOJMO,
                        CHVALOVICE_33_CHVALOVICE_PRACHATICE,
                        CHVALOVICE_RN_33_CHVALOVICE_PRACHATICE);
    }

    @Test
    void findByMunicipalityAndDescriptiveNo() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipality("chvalovicE")
                .houseNo(33, DESCRIPTIVE_NO)
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        CHVALOVICE_33_CHVALOVICE_ZNOJMO,
                        HATE_33_CHVALOVICE_ZNOJMO,
                        CHVALOVICE_33_CHVALOVICE_PRACHATICE);
    }

    @Test
    void findByMunicipalityAndRegistrationNo() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipality("chvalovicE")
                .houseNo(33, REGISTRATION_NO)
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        CHVALOVICE_RN_33_CHVALOVICE_ZNOJMO,
                        HATE_RN_33_CHVALOVICE_ZNOJMO,
                        CHVALOVICE_RN_33_CHVALOVICE_PRACHATICE);
    }

    @Test
    void findByMunicipalityAndDescriptiveOrRegistrationNo() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipality("chvalovicE")
                .houseNo(33, null)
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        CHVALOVICE_33_CHVALOVICE_ZNOJMO,
                        CHVALOVICE_RN_33_CHVALOVICE_ZNOJMO,
                        HATE_33_CHVALOVICE_ZNOJMO,
                        HATE_RN_33_CHVALOVICE_ZNOJMO,
                        CHVALOVICE_33_CHVALOVICE_PRACHATICE,
                        CHVALOVICE_RN_33_CHVALOVICE_PRACHATICE);
    }

    @Test
    void findByMunicipalDistrict() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipalDistrict("hatĚ")
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        HATE_1_CHVALOVICE_ZNOJMO,
                        HATE_33_CHVALOVICE_ZNOJMO,
                        HATE_RN_33_CHVALOVICE_ZNOJMO,
                        HATE_1_BECVARY_KOLIN,
                        HATE_33_BECVARY_KOLIN,
                        HATE_RN_33_BECVARY_KOLIN);
    }

    @Test
    void findByMunicipalDistrictAndDescriptiveNo() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipalDistrict("hatĚ")
                .houseNo(33, DESCRIPTIVE_NO)
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        HATE_33_CHVALOVICE_ZNOJMO,
                        HATE_33_BECVARY_KOLIN);
    }

    @Test
    void findByMunicipalDistrictAndRegistrationNo() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipalDistrict("hatĚ")
                .houseNo(33, REGISTRATION_NO)
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        HATE_RN_33_CHVALOVICE_ZNOJMO,
                        HATE_RN_33_BECVARY_KOLIN);
    }

    @Test
    void findByMunicipalDistrictAndDescriptiveOrRegistrationNo() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipalDistrict("hatĚ")
                .houseNo(33, null)
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        HATE_33_CHVALOVICE_ZNOJMO,
                        HATE_RN_33_CHVALOVICE_ZNOJMO,
                        HATE_33_BECVARY_KOLIN,
                        HATE_RN_33_BECVARY_KOLIN);
    }

    @Test
    void findByMunicipalityAndMunicipalDistrict() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .municipality("chvalovicE")
                .municipalDistrict("hatĚ")
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        HATE_1_CHVALOVICE_ZNOJMO,
                        HATE_33_CHVALOVICE_ZNOJMO,
                        HATE_RN_33_CHVALOVICE_ZNOJMO);
    }

    @Test
    void findByStreet() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .street("botanická")
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        BOTANICKA_68A_BRNO);
    }

    @Test
    void findByStreetAndOrientationNumber() {
        var finder = newAddressFinder(ALL_ADDRESSES);
        var address = Address.builder()
                .street("botanická")
                .orientationNo("68a")
                .build();

        assertThat(finder.findAddress(address))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        BOTANICKA_68A_BRNO);
    }

    private AddressFinder newAddressFinder(List<Address> addresses) {
        var factory = newAddressFinderFactory(handler -> loadData(addresses, handler));
        try {
            return factory.newAddressFinder();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private void loadData(List<Address> addresses, AddressHandler handler) {
        addresses.forEach(
                address -> handler.processAddress(
                        AddressBase.ofAddress(address),
                        address.getOrientationNo(),
                        address.getHouseNo(), address.getHouseNoType()));
    }


}
