package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.loader.mvcr.MvcrDataLoader;
import cz.muni.fi.pv168.addresses.model.Address;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.DESCRIPTIVE_NO;

final class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static final int ITERATIONS_COUNT = 10;

    public static void main(String[] args) throws IOException {

        Configuration configuration = ConfigurationSelector.selectConfiguration();
        if (configuration == null) {
            logger.warning("No configuration selected, exiting...");
            return;
        }
        logger.info(String.format("Selected configuration %s: %s", configuration.name(), configuration));

        var dataLoader = new MvcrDataLoader(Paths.get("adresy.zip"));
        AddressFinderFactory finderFactory = configuration.createAddressFinderFactory(dataLoader);

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

        var performanceTest = new PerformanceTest(addresses, finderFactory);
        performanceTest.run(ITERATIONS_COUNT);
        logger.info(String.format("Selected configuration was %s: %s", configuration.name(), configuration));
    }
}
