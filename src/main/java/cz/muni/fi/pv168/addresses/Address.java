package cz.muni.fi.pv168.addresses;

/**
 * This interface represents address in Czech Republic.
 *
 * @author Petr Adámek
 */
public interface Address {

    static AddressBuilder builder() {
        return new AddressBuilder();
    }

    /**
     * Returns street part (<em>ulice</em>) of address.
     *
     * @return street part of address.
     */
    String getStreet();

    /**
     * Returns orientation number (<em>orientační číslo</em>). This number is
     * unique in the street.
     *
     * @return number in street
     */
    String getOrientationNo();

    /**
     * Returns house number (<em>popisné číslo</em> or <em>evidenční číslo</em>).
     * This number is unique in municipality part. There are two types of
     * house numbers in the Czech Republic - its descriptive number
     * (<em>popisné číslo</em>) assigned to common buildings, or registration
     * number (<em>evidenční číslo</em>) assigned to temporary or recreation
     * building.
     *
     * @return house number.
     */
    Integer getHouseNo();

    /**
     * Returns type of house number.
     *
     * @return type of house number.
     */
    HouseNoType getHouseNoType();

    /**
     * Returns municipality (<em>obec</em>).
     *
     * @return municipality
     */
    String getMunicipality();

    /**
     * Returns municipality district (<em>část obce</em>).
     *
     * @return municipality district
     */
    String getMunicipalDistrict();

    /**
     * Returns post code (<em>PSČ</em>).
     *
     * @return post code.
     */
    String getPostCode();

    /**
     * Returns post office name (<em>pošta</em>).
     *
     * @return post office name.
     */
    String getPost();

    /**
     * Returns district (<em>okres</em>).
     *
     * @return district.
     */
    String getDistrict();

    /**
     * HouseNoType distinguish if the house number is Descriptive Number or Registration Number
     */
    enum HouseNoType {

        /**
         * This type represent Descriptive House Number (<em>popisné číslo</em>).
         */
        DESCRIPTIVE_NO,

        /**
         * This type represents Registration House Number (<em>evidenční číslo</em>).
         */
        REGISTRATION_NO
    }
}
