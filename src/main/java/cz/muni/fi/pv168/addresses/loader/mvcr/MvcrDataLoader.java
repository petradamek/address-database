package cz.muni.fi.pv168.addresses.loader.mvcr;

import cz.muni.fi.pv168.addresses.loader.AddressHandler;
import cz.muni.fi.pv168.addresses.loader.DataLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * {@link DataLoader} for loading addresses in format provided by the Czech Ministry of Interior.
 */
public final class MvcrDataLoader implements DataLoader {

    private static final String ADDRESSES_FILE_NAME = "adresy.xml";

    private final Path archivePath;
    private final MvcrAddressParser parser = new MvcrAddressParser();

    public MvcrDataLoader(Path archivePath) {
        this.archivePath = Objects.requireNonNull(archivePath, "archivePath is null");
    }

    @Override
    public void loadData(AddressHandler addressHandler) throws IOException {

        try (var zipInputStream = new ZipInputStream(Files.newInputStream(archivePath))) {
            for (; ; ) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                if (zipEntry == null) {
                    throw new IOException("Addresses file '" + ADDRESSES_FILE_NAME
                            + "' not found in archive " + archivePath);
                }
                if (zipEntry.getName().equals(ADDRESSES_FILE_NAME)) {
                    break;
                }
            }
            parser.parseDatabase(zipInputStream, addressHandler);
        }
    }
}
