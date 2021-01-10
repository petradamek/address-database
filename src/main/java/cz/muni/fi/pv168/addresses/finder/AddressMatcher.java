package cz.muni.fi.pv168.addresses.finder;

import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.model.AddressBase;

public final class AddressMatcher {

    private AddressMatcher() {
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

    private static boolean matchField(String source, String target) {
        if (source == null) {
            return true;
        } else {
            return source.equalsIgnoreCase(target);
        }
    }

    private static boolean matchField(Integer source, Integer target) {
        if (source == null) {
            return true;
        } else {
            return source.equals(target);
        }
    }

    private static <E extends Enum<E>> boolean matchField(E source, E target) {
        if (source == null) {
            return true;
        } else {
            return source == target;
        }
    }
}
