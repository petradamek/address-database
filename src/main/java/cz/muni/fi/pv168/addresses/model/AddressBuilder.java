package cz.muni.fi.pv168.addresses.model;

/**
 * Builder for {@link Address}.
 *
 * @author Petr Adámek
 */
public class AddressBuilder {

    private String street;
    private String orientationNo;
    private Integer houseNo;
    private Address.HouseNoType houseNoType;
    private String municipality;
    private String municipalDistrict;
    private String district;
    private String postCode;
    private String post;

    AddressBuilder() {
    }

    public AddressBuilder district(String district) {
        this.district = district;
        return this;
    }

    public AddressBuilder houseNo(Integer houseNo, Address.HouseNoType type) {
        this.houseNo = houseNo;
        this.houseNoType = type;
        return this;
    }

    public AddressBuilder municipalDistrict(String municipalDistrict) {
        this.municipalDistrict = municipalDistrict;
        return this;
    }

    public AddressBuilder municipality(String municipality) {
        this.municipality = municipality;
        return this;
    }

    public AddressBuilder orientationNo(String orientationNo) {
        this.orientationNo = orientationNo;
        return this;
    }

    public AddressBuilder post(String post) {
        this.post = post;
        return this;
    }

    public AddressBuilder postCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public AddressBuilder street(String street) {
        this.street = street;
        return this;
    }

    public Address build() {
        return new SimpleAddress(
                street, orientationNo, houseNo, houseNoType, municipality,
                municipalDistrict, district, postCode, post);
    }
}
