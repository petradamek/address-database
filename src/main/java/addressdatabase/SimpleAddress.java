package addressdatabase;

/**
 * This class represents address in Czech Republic
 *
 * @author Petr Adámek
 */
public class SimpleAddress extends AbstractAddress {

    private String street;
    private String orientationNo;
    private Integer houseNo;
    private HouseNoType houseNoType;
    private String municipality;
    private String municipalDistrict;
    private String district;
    private String postCode;
    private String post;

    public SimpleAddress(
            String street,
            String orientationNo,
            Integer houseNo,
            HouseNoType houseNoType,
            String municipality,
            String municipalDistrict,
            String district,
            String postCode,
            String post) {

        this.street = street;
        this.orientationNo = orientationNo;
        this.houseNo = houseNo;
        this.houseNoType = houseNoType;
        this.municipality = municipality;
        this.municipalDistrict = municipalDistrict;
        this.district = district;
        this.postCode = postCode;
        this.post = post;
        validate();
    }

    public SimpleAddress(
            AddressBase addressBase,
            String orientationNo,
            Integer houseNo,
            Address.HouseNoType houseNoType) {
        this(
                addressBase.getStreet(),
                orientationNo,
                houseNo,
                houseNoType,
                addressBase.getMunicipality(),
                addressBase.getMunicipalDistrict(),
                addressBase.getDistrict(),
                addressBase.getPostCode(),
                addressBase.getPost());
    }

    @Override
    public String getDistrict() {
        return district;
    }

    @Override
    public Integer getHouseNo() {
        return houseNo;
    }

    @Override
    public String getMunicipalDistrict() {
        return municipalDistrict;
    }

    public String getMunicipality() {
        return municipality;
    }

    @Override
    public String getPostCode() {
        return postCode;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getOrientationNo() {
        return orientationNo;
    }

    @Override
    public HouseNoType getHouseNoType() {
        return houseNoType;
    }

    @Override
    public String getPost() {
        return post;
    }
}
