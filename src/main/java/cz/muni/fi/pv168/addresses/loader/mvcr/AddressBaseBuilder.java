package cz.muni.fi.pv168.addresses.loader.mvcr;

import cz.muni.fi.pv168.addresses.model.AddressBase;

/**
 * This class is not thread safe!
 */
final class AddressBaseBuilder {

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

    AddressBaseBuilder() {
    }

    void district(String district) {
        invalidate();
        this.district = district;
    }

    void municipalDistrict(String municipalDistrict) {
        invalidate();
        this.municipalDistrict = municipalDistrict;
    }

    void municipalDistrictCode(Integer municipalDistrictCode) {
        invalidate();
        this.municipalDistrictCode = municipalDistrictCode;
    }

    void municipality(String municipality) {
        invalidate();
        this.municipality = municipality;
    }

    void municipalityCode(Integer municipalityCode) {
        invalidate();
        this.municipalityCode = municipalityCode;
    }

    void post(String post) {
        invalidate();
        this.post = post;
    }

    void postCode(String postCode) {
        invalidate();
        this.postCode = postCode;
    }

    void street(String street) {
        invalidate();
        this.street = street;
    }

    void streetCode(Integer streetCode) {
        invalidate();
        this.streetCode = streetCode;
    }

    AddressBase build() {
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

    private void invalidate() {
        addressBase = null;
    }
}
