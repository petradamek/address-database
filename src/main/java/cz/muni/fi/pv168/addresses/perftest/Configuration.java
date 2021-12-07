package cz.muni.fi.pv168.addresses.perftest;

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

    SIMPLE_FINDER_WITH_FOR_EACH_STRATEGY(
            "#1 Sequential (list-based) finder using for-each loop",
            dataLoader -> {
                var searchStrategy = new ForEachSearchStrategy();
                return new SimpleAddressFinderFactory(dataLoader, searchStrategy);
            }),

    SIMPLE_FINDER_WITH_STREAM_STRATEGY(
            "#2 Sequential (list-based) finder using streams (single thread)",
            dataLoader -> {
                var searchStrategy = new StreamSearchStrategy();
                return new SimpleAddressFinderFactory(dataLoader, searchStrategy);
            }),

    SIMPLE_FINDER_WITH_PARALLEL_STREAM_STRATEGY(
            "#3 Sequential (list-based) finder using parallel streams (multiple threads)",
            dataLoader -> {
                var searchStrategy = new ParallelStreamSearchStrategy();
                return new SimpleAddressFinderFactory(dataLoader, searchStrategy);
            }),

    INDEXED_FINDER_WITH_INDEXED_GROUP(
            "#4 Indexed (map-based) finder using indexed (map-based) AddressGroup",
            dataLoader -> {
                var addressGroupFactory = new IndexedAddressGroup.Factory();
                return new IndexedAddressFinderFactory(dataLoader, addressGroupFactory);
            }),

    INDEXED_FINDER_WITH_SIMPLE_GROUP(
            "#5 Indexed (map-based) finder using sequential (list-based) AddressGroup",
            dataLoader -> {
                var addressGroupFactory = new SimpleAddressGroup.Factory();
                return new IndexedAddressFinderFactory(dataLoader, addressGroupFactory);
            });

    private final Function<DataLoader, AddressFinderFactory> factoryFunction;
    private final String description;

    Configuration(String description, Function<DataLoader, AddressFinderFactory> factoryFunction) {
        this.factoryFunction = factoryFunction;
        this.description = description;
    }

    AddressFinderFactory createAddressFinderFactory(DataLoader dataLoader) {
        return factoryFunction.apply(dataLoader);
    }

    @Override
    public String toString() {
        return description;
    }
}
