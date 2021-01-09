package addressdatabase.loader.mvcr;

import addressdatabase.Address;
import addressdatabase.AddressBase;
import addressdatabase.AddressBaseFactory;
import addressdatabase.loader.AddressHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Petr Adámek
 */
public class MVCRAddressParser extends DefaultHandler {
    
    static final Logger logger = Logger.getLogger(MVCRAddressParser.class.getName());
    
    private AddressHandler addressHandler;

    public MVCRAddressParser(AddressHandler addressHandler) {
        this.addressHandler = addressHandler;
    }
            
    public static void parseDatabase(AddressHandler addressHandler, 
            InputStream addressesXml) throws IOException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(addressesXml, new MVCRAddressParser(addressHandler));
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException("Error when inicializing xml parser",ex);
        } catch (SAXException ex) {
            throw new IOException("Error when parsing addresses file", ex);
        }
        
    }
    
    private AddressBaseFactory addressBaseFactory = AddressBaseFactory.newInstance();
    private long startTime;
    private long count;
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("adresy")) {
            logger.log(Level.INFO, "Loading data done, loaded {0} addresses, took {1} s", 
                    new Object[] {count,(System.currentTimeMillis() - startTime)/1000d});
        } else if (qName.equals("oblast")) {
            addressBaseFactory.setDistrict(null);
        } else if (qName.equals("obec")) {
            addressBaseFactory.setMunicipality(null);
            addressBaseFactory.setMunicipalityCode(null);
        } else if (qName.equals("cast")) {
            addressBaseFactory.setMunicipalDistrict(null);
            addressBaseFactory.setMunicipalDistrictCode(null);
        } else if (qName.equals("ulice")) {
            addressBaseFactory.setStreet(null);
            addressBaseFactory.setStreetCode(null);
        }
    }    

    private int parseCode(Attributes attributes) throws SAXException {
        try {
            return Integer.parseInt(attributes.getValue("kod"));
        } catch(NumberFormatException ex) {
            throw new SAXException("Error when parsing code '" + 
                    attributes.getValue("kod") + "' for " + 
                    attributes.getValue("nazev"), ex);
        }
    }

    private Integer parseNumber(String number) throws SAXException {
        try {
            return Integer.parseInt(number);
        } catch(NumberFormatException ex) {
            throw new SAXException("Error when parsing house number '" + 
                    number + "'", ex);
        }
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("adresy")) {
            logger.log(Level.INFO, "Loading data, version is {0}", attributes.getValue("stav_k"));
            startTime = System.currentTimeMillis();
        } else if (qName.equals("oblast")) {
            addressBaseFactory.setDistrict(attributes.getValue("okres"));
        } else if (qName.equals("obec")) {
            addressBaseFactory.setMunicipality(attributes.getValue("nazev"));
            addressBaseFactory.setMunicipalityCode(parseCode(attributes));
        } else if (qName.equals("cast")) {
            addressBaseFactory.setMunicipalDistrict(attributes.getValue("nazev"));
            addressBaseFactory.setMunicipalDistrictCode(parseCode(attributes));
        } else if (qName.equals("ulice")) {
            addressBaseFactory.setStreet(attributes.getValue("nazev"));
            addressBaseFactory.setStreetCode(parseCode(attributes));
        } else if (qName.equals("a")) {
            AddressBase addressBase = addressBaseFactory.newAddressBase();
            String orientationNumber = attributes.getValue("o");
            String registrationNumber = attributes.getValue("e");
            String descritptiveNumber = attributes.getValue("p");
            Integer houseNumber;
            Address.HouseNoType houseNoType;
            if (registrationNumber == null && descritptiveNumber != null) {
                houseNumber = parseNumber(descritptiveNumber);
                houseNoType = Address.HouseNoType.DESCRIPTIVE_NO;
            } else if (registrationNumber != null && descritptiveNumber == null) {
                houseNumber = parseNumber(registrationNumber);
                houseNoType = Address.HouseNoType.REGISTRATION_NO;
            } else {                
                logger.log(Level.WARNING, 
                        "Wrong address for {0}; registrationNumber = {1}, "
                        + "descritptiveNumber = {2}, orientationNumber = {3}", 
                        new Object[]{addressBase, registrationNumber, 
                            descritptiveNumber, orientationNumber});
                houseNumber = null;
                houseNoType = null;                
            }            
            addressHandler.processAddress(addressBase, orientationNumber, houseNumber, houseNoType);
            count++;
        } else {
            throw new SAXException("Unknown element: " + qName);
        }
    }    
    
    
}
