package cz.muni.fi.pv168.addresses.inmemory;

import cz.muni.fi.pv168.addresses.AddressBase;

/**
 * @author Petr Adámek
 */
interface AddressGroupFactory {

    AddressGroup newAddressGroup(AddressBase addressBase);
}
