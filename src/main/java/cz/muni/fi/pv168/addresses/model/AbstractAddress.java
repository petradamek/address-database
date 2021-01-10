package cz.muni.fi.pv168.addresses.model;

/**
 * AbstractAddress simplifies creation of classes implementing Address.
 *
 * @author Petr Adámek
 */
public abstract class AbstractAddress implements Address {

    private static void writeSeparator(StringBuilder builder, String separator) {
        if (!lastCharIsSeparator(builder)) {
            builder.append(separator);
        }
    }

    private static boolean writeIfNotNull(StringBuilder builder, Object what) {
        return writeIfNotNull(builder, what, "");
    }

    private static boolean writeIfNotNull(StringBuilder builder, Object what, String prefix) {
        if (what == null) {
            return false;
        } else {
            builder.append(prefix);
            builder.append(what);
            return true;
        }
    }

    private static boolean lastCharIsSeparator(StringBuilder builder) {
        if (builder.length() <= 0) {
            return false;
        }
        char lastChar = builder.charAt(builder.length() - 1);
        return lastChar == ' ' || lastChar == ',';
    }

    protected void validate() {
        // TODO: specify rules for address
        if (getMunicipalDistrict() == null &&
                getMunicipality() == null &&
                getStreet() == null &&
                getPost() == null &&
                getPostCode() == null) {
            throw new IllegalArgumentException("At least municiality, street, "
                    + "post or postCode must be filled to create address");
        }
    }

    private void writeNumber(StringBuilder builder) {
        if (getHouseNo() != null) {
            if (getHouseNoType() == HouseNoType.REGISTRATION_NO) {
                builder.append("ev.č. ");
            } else if (getOrientationNo() == null) {
                builder.append("č.p. ");
            }
            builder.append(getHouseNo());
        }
        if (getOrientationNo() != null) {
            if (getHouseNo() != null) {
                builder.append("/");
            }
            builder.append(getOrientationNo());
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        boolean municipalDistrictWritten = false;
        boolean municipalityWritten = false;
        boolean streetWritten = writeIfNotNull(result, getStreet());

        if (!streetWritten) {
            municipalDistrictWritten = writeIfNotNull(result, getMunicipalDistrict());
            if (!municipalDistrictWritten) {
                municipalityWritten = writeIfNotNull(result, getMunicipality());
            }
        }
        writeSeparator(result, " ");
        writeNumber(result);
        writeSeparator(result, ", ");
        writeIfNotNull(result, getPostCode());
        if (!municipalityWritten) {
            writeSeparator(result, " ");
            writeIfNotNull(result, getMunicipality());
        }
        writeSeparator(result, ", ");
        if (!municipalDistrictWritten) {
            writeSeparator(result, " ");
            writeIfNotNull(result, getMunicipalDistrict());
        }
        writeSeparator(result, ", ");
        writeIfNotNull(result, getPost(), "pošta ");
        writeSeparator(result, ", ");
        writeIfNotNull(result, getDistrict(), "okres ");
        while (lastCharIsSeparator(result)) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }
}
