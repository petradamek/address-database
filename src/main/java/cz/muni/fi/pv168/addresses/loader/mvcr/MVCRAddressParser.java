package cz.muni.fi.pv168.addresses.loader.mvcr;

import cz.muni.fi.pv168.addresses.Address;
import cz.muni.fi.pv168.addresses.AddressBase;
import cz.muni.fi.pv168.addresses.AddressBaseFactory;
import cz.muni.fi.pv168.addresses.loader.AddressHandler;
import cz.muni.fi.pv168.addresses.time.StopWatch;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Petr Adámek
 */
public class MVCRAddressParser extends DefaultHandler {

    static final Logger logger = Logger.getLogger(MVCRAddressParser.class.getName());

    private final AddressHandler addressHandler;
    private final AddressBaseFactory addressBaseFactory = AddressBaseFactory.newInstance();
    private StopWatch stopWatch;
    private long count;

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
            throw new RuntimeException("Error when inicializing xml parser", ex);
        } catch (SAXException ex) {
            throw new IOException("Error when parsing addresses file", ex);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "adresy":
                logger.log(Level.INFO, String.format("Loading data done, loaded %,d addresses, took %,.3f ms",
                        count, stopWatch.getDurationInMilliseconds()));
                break;
            case "oblast":
                addressBaseFactory.setDistrict(null);
                break;
            case "obec":
                addressBaseFactory.setMunicipality(null);
                addressBaseFactory.setMunicipalityCode(null);
                break;
            case "cast":
                addressBaseFactory.setMunicipalDistrict(null);
                addressBaseFactory.setMunicipalDistrictCode(null);
                break;
            case "ulice":
                addressBaseFactory.setStreet(null);
                addressBaseFactory.setStreetCode(null);
                break;
        }
    }

    private int parseCode(Attributes attributes) throws SAXException {
        try {
            return Integer.parseInt(attributes.getValue("kod"));
        } catch (NumberFormatException ex) {
            throw new SAXException("Error when parsing code '" +
                    attributes.getValue("kod") + "' for " +
                    attributes.getValue("nazev"), ex);
        }
    }

    private Integer parseNumber(String number) throws SAXException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException ex) {
            throw new SAXException("Error when parsing house number '" +
                    number + "'", ex);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "adresy":
                logger.log(Level.INFO, "Loading data, version is {0}", attributes.getValue("stav_k"));
                stopWatch = StopWatch.start();
                break;
            case "oblast":
                addressBaseFactory.setDistrict(attributes.getValue("okres"));
                break;
            case "obec":
                addressBaseFactory.setMunicipality(attributes.getValue("nazev"));
                addressBaseFactory.setMunicipalityCode(parseCode(attributes));
                break;
            case "cast":
                addressBaseFactory.setMunicipalDistrict(attributes.getValue("nazev"));
                addressBaseFactory.setMunicipalDistrictCode(parseCode(attributes));
                break;
            case "ulice":
                addressBaseFactory.setStreet(attributes.getValue("nazev"));
                addressBaseFactory.setStreetCode(parseCode(attributes));
                break;
            case "a":
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
                break;
            default:
                throw new SAXException("Unknown element: " + qName);
        }
    }
}
