/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addressdatabase.inmemory;

import addressdatabase.AddressBase;

/**
 *
 * @author Petr Adámek
 */
interface AddressGroupFactory {
    
    AddressGroup newAddressGroup(AddressBase addressBase);
    
}
