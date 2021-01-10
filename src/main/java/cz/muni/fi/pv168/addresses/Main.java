package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.finder.indexed.IndexedAddressFinderFactory;
import cz.muni.fi.pv168.addresses.finder.indexed.IndexedAddressGroup;
import cz.muni.fi.pv168.addresses.finder.indexed.SimpleAddressGroup;
import cz.muni.fi.pv168.addresses.loader.mvcr.MVCRDataLoader;
import cz.muni.fi.pv168.addresses.finder.simple.SimpleAddressFinderFactory;
import cz.muni.fi.pv168.addresses.time.StopWatch;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aplikace ignoruje městské části a PSČ jednotlivých ulic.
 *
 * @author petr
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        AddressFinder addressFinder = createAddressFinder();

        var stopWatch = StopWatch.start();

        List<Address> addresses = List.of(
                Address.builder().street("Botanická").orientationNo("68a").municipality("Brno").build(),
                Address.builder().street("Botanická").orientationNo("68a").build(),
                Address.builder().municipality("Chvalovice").houseNo(33).build(),
                Address.builder().municipality("Dolní Lhota").houseNo(1).build(),
                Address.builder().street("Jindřišská").orientationNo("1").build(),
                Address.builder().municipality("Lhota").houseNo(1).build()
        );

        for (Address address : addresses) {
            System.err.println("Input: " + address);
            Collection<Address> foundAddresses = addressFinder.findAddress(address);
            System.err.println("Output: " + foundAddresses);
        }
        double totalTime = stopWatch.getDurationInMilliseconds();
        double oneRecordAvgTime = totalTime / addresses.size();
        logger.log(Level.INFO, String.format("Total time: %,.3f ms, average time for one record: %,.3f ms",
                totalTime, oneRecordAvgTime));
    }

    private static AddressFinder createAddressFinder() throws IOException {

        var dataLoader = new MVCRDataLoader("adresy.zip", "adresy.xml");

        var simpleAddressFinderFactory = new SimpleAddressFinderFactory(dataLoader);

        var simpleAddressGroupFactory = new SimpleAddressGroup.Factory();
        var indexedAddressGroupFactory = new IndexedAddressGroup.Factory();

        var addressGroupFactory = indexedAddressGroupFactory;
        //var addressGroupFactory = simpleAddressGroupFactory;

        var indexedAddressFinderFactory = new IndexedAddressFinderFactory(dataLoader, addressGroupFactory);

        var addressFinderFactory = simpleAddressFinderFactory;
        //var addressFinderFactory = indexedAddressFinderFactory;

        return addressFinderFactory.newAddressFinder();
    }
}
