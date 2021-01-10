package cz.muni.fi.pv168.addresses.loader.mvcr;

import cz.muni.fi.pv168.addresses.loader.AddressHandler;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Petr Adámek
 */
public final class MVCRDataLoader implements DataLoader {

    private final String archiveName;
    private final String addressesFileName;
    private final MVCRAddressParser parser = new MVCRAddressParser();

    public MVCRDataLoader(String archiveName, String addressesFileName) {
        this.archiveName = Objects.requireNonNull(archiveName, "archiveName is null");
        this.addressesFileName = Objects.requireNonNull(addressesFileName, "addressesFileName is null");
    }

    @Override
    public void loadData(AddressHandler addressHandler) throws IOException {

        try (var zipInputStream = new ZipInputStream(new FileInputStream(archiveName))) {
            for (; ; ) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                if (zipEntry == null) {
                    throw new IOException("Addresses file '" + addressesFileName
                            + "' not found in archive " + archiveName);
                }
                if (zipEntry.getName().equals(addressesFileName)) {
                    break;
                }
            }
            parser.parseDatabase(zipInputStream, addressHandler);
        }
    }
}
