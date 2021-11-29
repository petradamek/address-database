package cz.muni.fi.pv168.addresses.ui.loading;

import cz.muni.fi.pv168.addresses.loader.DataLoader;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractLoadingStrategy implements LoadingStrategy {

    protected final DataLoader dataLoader;

    protected AbstractLoadingStrategy(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    protected void handleError(IOException ex) {
        throw new UncheckedIOException(ex);
    }

}
