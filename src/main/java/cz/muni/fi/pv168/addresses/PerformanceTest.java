package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.finder.AddressFinder;
import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.time.StopWatch;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

final class PerformanceTest {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private final Collection<Address> addresses;
    private final AddressFinderFactory addressFinderFactory;
    private final String configurationDescription;

    PerformanceTest(List<Address> addresses, AddressFinderFactory addressFinderFactory, String configurationDescription) {
        this.addresses = addresses;
        this.addressFinderFactory = addressFinderFactory;
        this.configurationDescription = configurationDescription;
    }

    PerformanceTestReport run(int iterationsCount) throws IOException {

        var loadingStopWatch = StopWatch.start();
        AddressFinder addressFinder = createAddressFinder();
        double loadingTime = loadingStopWatch.getDurationInMilliseconds();

        logger.info("Starting dry run (without measuring time, with printing results)");
        dryRun(addressFinder);

        logger.info(String.format("Starting measurement for %d input addresses in %d iterations",
                addresses.size(), iterationsCount));
        var stopWatch = StopWatch.start();
        measurement(addressFinder, iterationsCount);
        double totalTime = stopWatch.getDurationInMilliseconds();
        double oneRecordAvgTime = totalTime / addresses.size() / iterationsCount;

        var runtime = Runtime.getRuntime();
        long heapSizeInMiB = runtime.totalMemory() / (1024 * 1024);

        PerformanceTestReport report = new PerformanceTestReport(
                configurationDescription, loadingTime, totalTime, oneRecordAvgTime, heapSizeInMiB, runtime.availableProcessors());
        logger.log(Level.INFO, "Performance test finished\n{0}", report);
        return report;
    }

    private AddressFinder createAddressFinder() throws IOException {
        return addressFinderFactory.newAddressFinder();
    }

    private void dryRun(AddressFinder addressFinder) {
        System.err.println();
        findAllAddresses(addressFinder, this::printResult);
    }

    private void measurement(AddressFinder addressFinder, int iterationsCount) {
        for (int i = 0; i < iterationsCount; i++) {
            findAllAddresses(addressFinder, this::doNotPrintResult);
        }
    }

    private void findAllAddresses(AddressFinder addressFinder, BiConsumer<Address, Collection<Address>> resultConsumer) {
        for (Address address : addresses) {
            Collection<Address> foundAddresses = addressFinder.findAddress(address);
            resultConsumer.accept(address, foundAddresses);
        }
    }

    private void doNotPrintResult(Address address, Collection<Address> foundAddresses) {
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
