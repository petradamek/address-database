package cz.muni.fi.pv168.addresses.finder.simple;

import cz.muni.fi.pv168.addresses.model.Address;

import java.util.Collection;
import java.util.List;

/**
 * Representation of the algorithm for searching a given list of addresses.
 */
interface SearchStrategy {

    Collection<Address> findAddress(List<Address> addresses, Address address);

}
