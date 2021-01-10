package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.loader.mvcr.MvcrDataLoader;
import cz.muni.fi.pv168.addresses.model.Address;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.DESCRIPTIVE_NO;

/**
 * Aplikace ignoruje městské části a PSČ jednotlivých ulic.
 *
 * @author petr
 */
final class Main {

    private static final int ITERATIONS_COUNT = 10;

    public static void main(String[] args) throws IOException {

        Configuration configuration = Configuration.SIMPLE_FINDER;

        var archivePath = Paths.get("adresy.zip");
        var dataLoader = new MvcrDataLoader(archivePath);

        AddressFinder addressFinder = configuration
                .createAddressFinderFactory(dataLoader)
                .newAddressFinder();

        List<Address> addresses = List.of(
                Address.builder().street("Botanická").orientationNo("68a").municipality("Brno").build(),
                Address.builder().street("Botanická").orientationNo("68a").build(),
                Address.builder().street("Irkutská").orientationNo("2").build(),
                Address.builder().municipality("Chvalovice").houseNo(33, DESCRIPTIVE_NO).build(),
                Address.builder().municipality("Chvalovice").houseNo(33, null).build(),
                Address.builder().municipality("Dolní Lhota").houseNo(1, DESCRIPTIVE_NO).build(),
                Address.builder().street("Jindřišská").orientationNo("1").build(),
                Address.builder().municipality("Lhota").houseNo(1, DESCRIPTIVE_NO).build()
        );

        var performanceTest = new PerformanceTest(addresses, addressFinder);
        performanceTest.run(ITERATIONS_COUNT);
    }
}
