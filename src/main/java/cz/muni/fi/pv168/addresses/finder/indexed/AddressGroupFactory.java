package cz.muni.fi.pv168.addresses.finder.indexed;

import cz.muni.fi.pv168.addresses.AddressBase;

/**
 * @author Petr Adámek
 */
interface AddressGroupFactory {

    AddressGroup newAddressGroup(AddressBase addressBase);
}
