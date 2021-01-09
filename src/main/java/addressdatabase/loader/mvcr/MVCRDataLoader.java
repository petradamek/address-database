package addressdatabase.loader.mvcr;

import addressdatabase.loader.AddressHandler;
import addressdatabase.loader.DataLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Petr Adámek
 */
public class MVCRDataLoader implements DataLoader {

    private static final Logger logger = Logger.getLogger(MVCRDataLoader.class.getName());
    private String archiveName;
    private String addressesFileName;

    public void setAddressesFileName(String addressesFileName) {
        this.addressesFileName = addressesFileName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
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
            MVCRAddressParser.parseDatabase(addressHandler, zipInputStream);
        }
    }
}
