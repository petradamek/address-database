package cz.muni.fi.pv168.addresses;

/**
 * @author petr
 */
public abstract class AddressTools {

    private static boolean matchField(Object source, Object target) {
        if (source == null) {
            return true;
        }
        if (source instanceof CharSequence) {
            return
                    target instanceof CharSequence &&
                            source.toString().equalsIgnoreCase(target.toString());
        } else {
            return source.equals(target);
        }
    }

    public static boolean match(Address source, Address target) {
        return
                matchField(source.getStreet(), target.getStreet()) &&
                        matchField(source.getOrientationNo(), target.getOrientationNo()) &&
                        matchField(source.getHouseNo(), target.getHouseNo()) &&
                        matchField(source.getHouseNoType(), target.getHouseNoType()) &&
                        matchField(source.getMunicipality(), target.getMunicipality()) &&
                        matchField(source.getMunicipalDistrict(), target.getMunicipalDistrict()) &&
                        matchField(source.getDistrict(), target.getDistrict()) &&
                        matchField(source.getPost(), target.getPost()) &&
                        matchField(source.getPostCode(), target.getPostCode());
    }

    public static boolean match(Address source, AddressBase target) {
        return
                matchField(source.getStreet(), target.getStreet()) &&
                        matchField(source.getMunicipality(), target.getMunicipality()) &&
                        matchField(source.getMunicipalDistrict(), target.getMunicipalDistrict()) &&
                        matchField(source.getDistrict(), target.getDistrict()) &&
                        matchField(source.getPost(), target.getPost()) &&
                        matchField(source.getPostCode(), target.getPostCode());
    }

    public static boolean matchNumbers(
            String sourceOrientationNo,
            Integer sourceHouseNo,
            Address.HouseNoType sourceHouseNoType,
            Address target) {

        return
                matchField(sourceOrientationNo, target.getOrientationNo()) &&
                        matchField(sourceHouseNo, target.getHouseNo()) &&
                        matchField(sourceHouseNoType, target.getHouseNoType());
    }

    public static boolean isEmpty(Address source) {
        return
                source.getStreet() == null &&
                        source.getOrientationNo() == null &&
                        source.getHouseNo() == null &&
                        source.getHouseNoType() == null &&
                        source.getMunicipality() == null &&
                        source.getMunicipalDistrict() == null &&
                        source.getDistrict() == null &&
                        source.getPost() == null &&
                        source.getPostCode() == null;
    }
}
