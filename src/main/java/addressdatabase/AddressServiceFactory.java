/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addressdatabase;

import addressdatabase.loader.DataLoader;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author petr
 */
public abstract class AddressServiceFactory {
    
    private DataLoader dataLoader;

    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    protected DataLoader getDataLoader() {
        return dataLoader;
    }    
    
    public abstract AddressService newAddressService() throws IOException;
        
}
