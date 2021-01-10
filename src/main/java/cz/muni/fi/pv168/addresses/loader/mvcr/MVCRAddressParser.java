package cz.muni.fi.pv168.addresses.loader.mvcr;

import cz.muni.fi.pv168.addresses.loader.AddressHandler;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.AddressBase;
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

final class MVCRAddressParser {

    private static final Logger logger = Logger.getLogger(MVCRAddressParser.class.getName());

    void parseDatabase(InputStream addressesXml, AddressHandler addressHandler) throws IOException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(addressesXml, new Handler(addressHandler));
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException("Error when initializing xml parser", ex);
        } catch (SAXException ex) {
            throw new IOException("Error when parsing addresses file", ex);
        }
    }

    private static class Handler extends DefaultHandler {

        private final AddressHandler addressHandler;
        private final AddressBaseBuilder addressBaseBuilder = new AddressBaseBuilder();
        private StopWatch stopWatch;
        private long count;

        private Handler(AddressHandler addressHandler) {
            this.addressHandler = addressHandler;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "adresy":
                    logger.log(Level.INFO, "Loading data, version is {0}", attributes.getValue("stav_k"));
                    stopWatch = StopWatch.start();
                    break;
                case "oblast":
                    addressBaseBuilder.district(attributes.getValue("okres"));
                    break;
                case "obec":
                    addressBaseBuilder.municipality(attributes.getValue("nazev"));
                    addressBaseBuilder.municipalityCode(parseCode(attributes));
                    break;
                case "cast":
                    addressBaseBuilder.municipalDistrict(attributes.getValue("nazev"));
                    addressBaseBuilder.municipalDistrictCode(parseCode(attributes));
                    break;
                case "ulice":
                    addressBaseBuilder.street(attributes.getValue("nazev"));
                    addressBaseBuilder.streetCode(parseCode(attributes));
                    break;
                case "a":
                    AddressBase addressBase = addressBaseBuilder.build();
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

        @Override
        public void endElement(String uri, String localName, String qName) {
            switch (qName) {
                case "adresy":
                    logger.log(Level.INFO, String.format("Loading data done, loaded %,d addresses, took %,.3f ms",
                            count, stopWatch.getDurationInMilliseconds()));
                    break;
                case "oblast":
                    addressBaseBuilder.district(null);
                    break;
                case "obec":
                    addressBaseBuilder.municipality(null);
                    addressBaseBuilder.municipalityCode(null);
                    break;
                case "cast":
                    addressBaseBuilder.municipalDistrict(null);
                    addressBaseBuilder.municipalDistrictCode(null);
                    break;
                case "ulice":
                    addressBaseBuilder.street(null);
                    addressBaseBuilder.streetCode(null);
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
    }
}
