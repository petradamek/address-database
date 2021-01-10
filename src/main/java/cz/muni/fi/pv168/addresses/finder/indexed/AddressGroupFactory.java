package cz.muni.fi.pv168.addresses.finder.indexed;

import cz.muni.fi.pv168.addresses.model.AddressBase;

/**
 * @author Petr Adámek
 */
interface AddressGroupFactory {

    AddressGroup newAddressGroup(AddressBase addressBase);
}
