/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addressdatabase;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * Aplikace ignoruje městské části a PSČ jednotlivých ulic.
 * 
 * @author petr
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class.getName());
    
    private static Address address(
            String street, String orientationNo,
            String municipality, Integer houseNo) {

        return AddressFactory.newInstance().
                setMunicipality(municipality).
                setOrientationNo(orientationNo).
                setStreet(street).                
                setHouseNo(houseNo).
                newAddress();                
    }            
    
    private static List<Address> getInputAddresses() {
        return Arrays.asList(
                address("Botanická", "68a", "Brno", null),
                address("Botanická", "68a", null, null),
                address(null,null,"Chvalovice",33),
                address(null,null,"Dolní Lhota",1),
                address("Jindřišská", "1", null, null),
                address(null,null,"Lhota",1)
        );
    }
    
    public static void main(String[] args) throws IOException {
        
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        
        AddressService addressService = context.getBean(AddressService.class);

        long start = System.currentTimeMillis();
        final List<Address> inputAddresses = getInputAddresses();
        for (Address inputAddress : inputAddresses) {
            System.err.println("Input: " + inputAddress);
            Collection<Address> out = addressService.findAddress(inputAddress);
            System.err.println("Output: " + out);
        }
        long end = System.currentTimeMillis();
        double totalTime = (end - start) / 1000.0;
        double oneRecordAvgTime = totalTime / inputAddresses.size();
        logger.log(Level.INFO, "Total time: {0} s, average time for one record: {1} s",
                new Object[]{totalTime, oneRecordAvgTime});
        
        
    }
}
