package cz.muni.fi.pv168.addresses.ui.loading;

import cz.muni.fi.pv168.addresses.loader.DataLoader;
import cz.muni.fi.pv168.addresses.ui.LoadingStateHandler;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractLoadingStrategy implements LoadingStrategy {

    private static final Logger logger = Logger.getLogger(AbstractLoadingStrategy.class.getName());

    protected final DataLoader dataLoader;
    protected final LoadingStateHandler loadingStateHandler;

    protected AbstractLoadingStrategy(DataLoader dataLoader, LoadingStateHandler loadingStateHandler) {
        this.dataLoader = dataLoader;
        this.loadingStateHandler = loadingStateHandler;
    }

    protected void handleError(IOException ex) {
        logger.log(Level.SEVERE, "Error when loading data", ex);
        JOptionPane.showMessageDialog(null, ex.toString(), "Error when loading data",
                JOptionPane.ERROR_MESSAGE);
    }

}
