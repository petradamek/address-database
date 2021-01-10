package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.time.StopWatch;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

final class PerformanceTest {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private final Collection<Address> addresses;
    private final AddressFinder addressFinder;

    PerformanceTest(List<Address> addresses, AddressFinder addressFinder) {
        this.addresses = addresses;
        this.addressFinder = addressFinder;
    }

    void run() {
        var stopWatch = StopWatch.start();
        System.err.println();
        findAllAddresses(this::printResult);
        double totalTime = stopWatch.getDurationInMilliseconds();
        double oneRecordAvgTime = totalTime / addresses.size();

        logger.log(Level.INFO, String.format("Total time: %,.3f ms, average time for one record: %,.3f ms",
                totalTime, oneRecordAvgTime));
    }

    private void findAllAddresses(BiConsumer<Address, Collection<Address>> resultConsumer) {
        for (Address address : addresses) {
            Collection<Address> foundAddresses = addressFinder.findAddress(address);
            resultConsumer.accept(address, foundAddresses);
        }
    }

    private void printResult(Address address, Collection<Address> foundAddresses) {
        System.err.printf("Found %d addresses for '%s' %n", foundAddresses.size(), address);
        int n = 0;
        for (var foundAddress : foundAddresses) {
            System.err.printf("   %3d: %s%n", n++, foundAddress);
        }
        System.err.println();
    }
}
