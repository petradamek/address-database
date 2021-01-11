package cz.muni.fi.pv168.addresses.finder.indexed;

import cz.muni.fi.pv168.addresses.model.AddressBase;

/**
 * Factory for {@link AddressGroup}.
 */
interface AddressGroupFactory {

    AddressGroup newAddressGroup(AddressBase addressBase);
}
