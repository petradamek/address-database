package addressdatabase.loader;

import java.io.IOException;

/**
 * This is common interface for data loading service.
 * 
 * @author Petr Adámek
 */
public interface DataLoader {
    
    public void loadData(AddressHandler addressHandler) throws IOException;
    
}
