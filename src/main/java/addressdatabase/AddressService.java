package addressdatabase;

import java.util.Collection;

/**
 * This service allows to find given address.
 *
 * @author Petr Adámek
 */
public interface AddressService {

    Collection<Address> findAddress(Address address);
}
