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

    private static final String ADDRESSES_FILE_NAME = "adresy.xml";

    private final String archiveName;
    private final MVCRAddressParser parser = new MVCRAddressParser();

    public MVCRDataLoader(String archiveName) {
        this.archiveName = Objects.requireNonNull(archiveName, "archiveName is null");
    }

    @Override
    public void loadData(AddressHandler addressHandler) throws IOException {

        try (var zipInputStream = new ZipInputStream(new FileInputStream(archiveName))) {
            for (; ; ) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                if (zipEntry == null) {
                    throw new IOException("Addresses file '" + ADDRESSES_FILE_NAME
                            + "' not found in archive " + archiveName);
                }
                if (zipEntry.getName().equals(ADDRESSES_FILE_NAME)) {
                    break;
                }
            }
            parser.parseDatabase(zipInputStream, addressHandler);
        }
    }
}
