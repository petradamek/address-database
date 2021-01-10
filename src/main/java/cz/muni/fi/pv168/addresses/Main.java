package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.finder.indexed.IndexedAddressFinderFactory;
import cz.muni.fi.pv168.addresses.finder.indexed.IndexedAddressGroup;
import cz.muni.fi.pv168.addresses.finder.indexed.SimpleAddressGroup;
import cz.muni.fi.pv168.addresses.loader.DataLoader;
import cz.muni.fi.pv168.addresses.loader.mvcr.MvcrDataLoader;
import cz.muni.fi.pv168.addresses.finder.simple.SimpleAddressFinderFactory;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.time.StopWatch;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cz.muni.fi.pv168.addresses.model.Address.HouseNoType.DESCRIPTIVE_NO;

/**
 * Aplikace ignoruje městské části a PSČ jednotlivých ulic.
 *
 * @author petr
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        var archivePath = Paths.get("adresy.zip");
        var dataLoader = new MvcrDataLoader(archivePath);

        AddressFinder addressFinder = simpleAddressFinder(dataLoader);
        //AddressFinder addressFinder = indexedAddressFinderWithIndexedAddressGroups(dataLoader);
        //AddressFinder addressFinder = indexedAddressFinderWithSimpleAddressGroups(dataLoader);

        var stopWatch = StopWatch.start();

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

        System.err.println();
        for (Address address : addresses) {
            Collection<Address> foundAddresses = addressFinder.findAddress(address);
            System.err.printf("Found %d addresses for '%s' %n", foundAddresses.size(), address);
            int n = 0;
            for (var foundAddress : foundAddresses) {
                System.err.printf("   %3d: %s%n", n++, foundAddress);
            }
            System.err.println();
        }
        double totalTime = stopWatch.getDurationInMilliseconds();
        double oneRecordAvgTime = totalTime / addresses.size();
        logger.log(Level.INFO, String.format("Total time: %,.3f ms, average time for one record: %,.3f ms",
                totalTime, oneRecordAvgTime));
    }

    private static AddressFinder simpleAddressFinder(DataLoader dataLoader) throws IOException {
        var factory = new SimpleAddressFinderFactory(dataLoader);
        return factory.newAddressFinder();
    }

    private static AddressFinder indexedAddressFinderWithIndexedAddressGroups(DataLoader dataLoader) throws IOException {
        var addressGroupFactory = new IndexedAddressGroup.Factory();
        var factory = new IndexedAddressFinderFactory(dataLoader, addressGroupFactory);
        return factory.newAddressFinder();
    }

    private static AddressFinder indexedAddressFinderWithSimpleAddressGroups(DataLoader dataLoader) throws IOException {
        var addressGroupFactory = new SimpleAddressGroup.Factory();
        var factory = new IndexedAddressFinderFactory(dataLoader, addressGroupFactory);
        return factory.newAddressFinder();
    }
}
