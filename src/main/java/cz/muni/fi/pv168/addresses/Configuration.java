package cz.muni.fi.pv168.addresses;

import cz.muni.fi.pv168.addresses.finder.AddressFinderFactory;
import cz.muni.fi.pv168.addresses.finder.indexed.IndexedAddressFinderFactory;
import cz.muni.fi.pv168.addresses.finder.indexed.IndexedAddressGroup;
import cz.muni.fi.pv168.addresses.finder.indexed.SimpleAddressGroup;
import cz.muni.fi.pv168.addresses.finder.simple.ForEachSearchStrategy;
import cz.muni.fi.pv168.addresses.finder.simple.ParallelStreamSearchStrategy;
import cz.muni.fi.pv168.addresses.finder.simple.SimpleAddressFinderFactory;
import cz.muni.fi.pv168.addresses.finder.simple.StreamSearchStrategy;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

import java.util.function.Function;

enum Configuration {

    SIMPLE_FINDER_WITH_FOR_EACH_STRATEGY(dataLoader -> {
        var searchStrategy = new ForEachSearchStrategy();
        return new SimpleAddressFinderFactory(dataLoader, searchStrategy);
    }),

    SIMPLE_FINDER_WITH_STREAM_STRATEGY(dataLoader -> {
        var searchStrategy = new StreamSearchStrategy();
        return new SimpleAddressFinderFactory(dataLoader, searchStrategy);
    }),

    SIMPLE_FINDER_WITH_PARALLEL_STREAM_STRATEGY(dataLoader -> {
        var searchStrategy = new ParallelStreamSearchStrategy();
        return new SimpleAddressFinderFactory(dataLoader, searchStrategy);
    }),

    INDEXED_FINDER_WITH_INDEXED_GROUP(dataLoader -> {
        var addressGroupFactory = new IndexedAddressGroup.Factory();
        return new IndexedAddressFinderFactory(dataLoader, addressGroupFactory);
    }),

    INDEXED_FINDER_WITH_SIMPLE_GROUP(dataLoader -> {
        var addressGroupFactory = new SimpleAddressGroup.Factory();
        return new IndexedAddressFinderFactory(dataLoader, addressGroupFactory);
    });

    private final Function<DataLoader, AddressFinderFactory> factoryFunction;

    Configuration(Function<DataLoader, AddressFinderFactory> factoryFunction) {
        this.factoryFunction = factoryFunction;
    }

    AddressFinderFactory createAddressFinderFactory(DataLoader dataLoader) {
        return factoryFunction.apply(dataLoader);
    }
}
