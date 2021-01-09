package cz.muni.fi.pv168.addresses;

/**
 * This class is not thread safe!
 *
 * @author petr
 */
public class AddressBaseFactory {

    private String street;
    private Integer streetCode;
    private String municipality;
    private Integer municipalityCode = -1;
    private String municipalDistrict;
    private Integer municipalDistrictCode = -1;
    private String district;
    private String postCode;
    private String post;

    private AddressBase addressBase;

    private AddressBaseFactory() {
    }

    public static AddressBaseFactory newInstance() {
        return new AddressBaseFactory();
    }

    private void invalidate() {
        addressBase = null;
    }

    public void setDistrict(String district) {
        invalidate();
        this.district = district;
    }

    public void setMunicipalDistrict(String municipalDistrict) {
        invalidate();
        this.municipalDistrict = municipalDistrict;
    }

    public void setMunicipalDistrictCode(Integer municipalDistrictCode) {
        invalidate();
        this.municipalDistrictCode = municipalDistrictCode;
    }

    public void setMunicipality(String municipality) {
        invalidate();
        this.municipality = municipality;
    }

    public void setMunicipalityCode(Integer municipalityCode) {
        invalidate();
        this.municipalityCode = municipalityCode;
    }

    public void setPost(String post) {
        invalidate();
        this.post = post;
    }

    public void setPostCode(String postCode) {
        invalidate();
        this.postCode = postCode;
    }

    public void setStreet(String street) {
        invalidate();
        this.street = street;
    }

    public void setStreetCode(Integer streetCode) {
        invalidate();
        this.streetCode = streetCode;
    }

    public AddressBase newAddressBase() {
        if (addressBase == null) {
            addressBase = new AddressBase(
                    street, streetCode,
                    municipality, municipalityCode,
                    municipalDistrict, municipalDistrictCode,
                    district,
                    postCode, post);
        }
        return addressBase;
    }
}
