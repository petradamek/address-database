package addressdatabase.inmemory;

import addressdatabase.AddressBase;

/**
 * @author Petr Adámek
 */
interface AddressGroupFactory {

    AddressGroup newAddressGroup(AddressBase addressBase);
}
