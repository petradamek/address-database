package cz.muni.fi.pv168.addresses;

/**
 * Factory class that helps creation of Address instances.
 *
 * @author Petr Adámek
 */
public class AddressFactory {

    private String street;
    private String orientationNo;
    private Integer houseNo;
    private Address.HouseNoType houseNoType;
    private String municipality;
    private String municipalDistrict;
    private String district;
    private String postCode;
    private String post;

    private AddressFactory() {
    }

    public static AddressFactory newInstance() {
        return new AddressFactory();
    }

    public AddressFactory setDistrict(String district) {
        this.district = district;
        return this;
    }

    public AddressFactory setHouseNo(Integer houseNo) {
        this.houseNo = houseNo;
        if (houseNo == null) {
            houseNoType = null;
        } else if (houseNoType == null) {
            houseNoType = Address.HouseNoType.DESCRIPTIVE_NO;
        }
        return this;
    }

    public AddressFactory setHouseNoType(Address.HouseNoType houseNoType) {
        this.houseNoType = houseNoType;
        return this;
    }

    public AddressFactory setMunicipalDistrict(String municipalDistrict) {
        this.municipalDistrict = municipalDistrict;
        return this;
    }

    public AddressFactory setMunicipality(String municipality) {
        this.municipality = municipality;
        return this;
    }

    public AddressFactory setOrientationNo(String orientationNo) {
        this.orientationNo = orientationNo;
        return this;
    }

    public AddressFactory setPost(String post) {
        this.post = post;
        return this;
    }

    public AddressFactory setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public AddressFactory setStreet(String street) {
        this.street = street;
        return this;
    }

    public Address newAddress() {
        return new SimpleAddress(
                street, orientationNo, houseNo, houseNoType, municipality,
                municipalDistrict, district, postCode, post);
    }
}
