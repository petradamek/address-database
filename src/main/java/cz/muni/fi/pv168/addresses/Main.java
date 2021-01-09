package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.inmemory.InMemoryAddressFinderFactory;
import cz.muni.fi.pv168.addresses.inmemory.IndexedAddressGroup;
import cz.muni.fi.pv168.addresses.inmemory.SimpleAddressGroup;
import cz.muni.fi.pv168.addresses.loader.mvcr.MVCRDataLoader;
import cz.muni.fi.pv168.addresses.simple.SimpleAddressFinderFactory;
import cz.muni.fi.pv168.addresses.time.StopWatch;

import java.io.IOException;
import java.util.Arrays;
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

    private static Address address(
            String street, String orientationNo,
            String municipality, Integer houseNo) {

        return AddressFactory.newInstance().
                setMunicipality(municipality).
                setOrientationNo(orientationNo).
                setStreet(street).
                setHouseNo(houseNo).
                newAddress();
    }

    private static List<Address> getInputAddresses() {
        return Arrays.asList(
                address("Botanická", "68a", "Brno", null),
                address("Botanická", "68a", null, null),
                address(null, null, "Chvalovice", 33),
                address(null, null, "Dolní Lhota", 1),
                address("Jindřišská", "1", null, null),
                address(null, null, "Lhota", 1)
        );
    }

    public static void main(String[] args) throws IOException {

        AddressFinder addressFinder = createAddressFinder();

        var stopWatch = StopWatch.start();
        final List<Address> inputAddresses = getInputAddresses();
        for (Address inputAddress : inputAddresses) {
            System.err.println("Input: " + inputAddress);
            Collection<Address> out = addressFinder.findAddress(inputAddress);
            System.err.println("Output: " + out);
        }
        double totalTime = stopWatch.getDurationInMilliseconds();
        double oneRecordAvgTime = totalTime / inputAddresses.size();
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

        var inMemoryAddressFinderFactory = new InMemoryAddressFinderFactory(dataLoader, addressGroupFactory);

        var addressFinderFactory = simpleAddressFinderFactory;
        //var addressFinderFactory = inMemoryAddressFinderFactory;

        return addressFinderFactory.newAddressFinder();
    }
}
