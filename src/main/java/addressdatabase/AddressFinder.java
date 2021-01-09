package addressdatabase;

import java.util.Collection;

/**
 * This component allows to find given address in official address database.
 */
public interface AddressFinder {

    /**
     * Finds all canonical addresses matching a given address specification.
     *
     * Address specification does not need to be complete, in that case
     * all the addresses matching the specification will be returned.
     *
     * @param address address specification (possibly incomplete)
     * @return all matching addresses or an empty collection if no such address was found
     */
    Collection<Address> findAddress(Address address);
}
