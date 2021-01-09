package addressdatabase;

import addressdatabase.inmemory.InMemoryAddressServiceFactory;
import addressdatabase.inmemory.IndexedAddressGroup;
import addressdatabase.inmemory.SimpleAddressGroup;
import addressdatabase.loader.mvcr.MVCRDataLoader;
import addressdatabase.simple.SimpleAddressServiceFactory;
import addressdatabase.time.StopWatch;

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

        AddressService addressService = createAddressService();

        var stopWatch = StopWatch.start();
        final List<Address> inputAddresses = getInputAddresses();
        for (Address inputAddress : inputAddresses) {
            System.err.println("Input: " + inputAddress);
            Collection<Address> out = addressService.findAddress(inputAddress);
            System.err.println("Output: " + out);
        }
        double totalTime = stopWatch.getDurationInMilliseconds();
        double oneRecordAvgTime = totalTime / inputAddresses.size();
        logger.log(Level.INFO, String.format("Total time: %,.3f ms, average time for one record: %,.3f ms",
                totalTime, oneRecordAvgTime));
    }

    private static AddressService createAddressService() throws IOException {

        var dataLoader = new MVCRDataLoader("adresy.zip", "adresy.xml");

        var simpleAddressServiceFactory = new SimpleAddressServiceFactory();
        simpleAddressServiceFactory.setDataLoader(dataLoader);

        var simpleAddressGroupFactory = new SimpleAddressGroup.Factory();
        var indexedAddressGroupFactory = new IndexedAddressGroup.Factory();

        var inMemoryAddressServiceFactory = new InMemoryAddressServiceFactory();
        inMemoryAddressServiceFactory.setDataLoader(dataLoader);

        inMemoryAddressServiceFactory.setAddressGroupFactory(indexedAddressGroupFactory);
        //inMemoryAddressServiceFactory.setAddressGroupFactory(simpleAddressGroupFactory);

        var serviceFactory = simpleAddressServiceFactory;
        //var serviceFactory = inMemoryAddressServiceFactory;

        return serviceFactory.newAddressService();
    }
}
