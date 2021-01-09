package addressdatabase;

import java.util.Collection;

/**
 * This service allows to find given address.
 *
 * @author Petr Adámek
 */
public interface AddressFinder {

    Collection<Address> findAddress(Address address);
}
