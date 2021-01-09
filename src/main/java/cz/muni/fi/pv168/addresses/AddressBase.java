package cz.muni.fi.pv168.addresses;

/**
 * @author petr
 */
public class AddressBase {

    private final String street;
    private final int streetCode;
    private final String municipality;
    private final int municipalityCode;
    private final String municipalDistrict;
    private final int municipalDistrictCode;
    private final String district;
    private final String postCode;
    private final String post;

    public AddressBase(
            String street, Integer streetCode,
            String municipality, Integer municipalityCode,
            String municipalDistrict, Integer municipalDistrictCode,
            String district,
            String postCode, String post) {

        this.street = street;
        this.streetCode = integerToInt(streetCode);
        this.municipality = municipality;
        this.municipalityCode = integerToInt(municipalityCode);
        this.municipalDistrict = municipalDistrict;
        this.municipalDistrictCode = integerToInt(municipalDistrictCode);
        this.district = district;
        this.postCode = postCode;
        this.post = post;
    }

    private static int integerToInt(Integer i) {
        return i == null ? -1 : i.intValue();
    }

    private static Integer intToInteger(int i) {
        return i == -1 ? null : Integer.valueOf(i);
    }

    public String getDistrict() {
        return district;
    }

    public String getMunicipalDistrict() {
        return municipalDistrict;
    }

    public int getMunicipalDistrictCode() {
        return municipalDistrictCode;
    }

    public String getMunicipality() {
        return municipality;
    }

    public int getMunicipalityCode() {
        return municipalityCode;
    }

    public String getPost() {
        return post;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetCode() {
        return streetCode;
    }

    @Override
    public String toString() {
        return "AddressBase{" +
                "street=" + street +
                ", streetCode=" + streetCode +
                ", municipality=" + municipality +
                ", municipalityCode=" + municipalityCode +
                ", municipalDistrict=" + municipalDistrict +
                ", municipalDistrictCode=" + municipalDistrictCode +
                ", district=" + district +
                ", postCode=" + postCode +
                ", post=" + post + '}';
    }
}
