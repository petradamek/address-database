package cz.muni.fi.pv168.addresses.ui.loading;

import cz.muni.fi.pv168.addresses.loader.DataLoader;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.ui.LoadingStateHandler;
import cz.muni.fi.pv168.addresses.ui.model.ReadOnlyEntityTableModel;

import javax.swing.SwingWorker;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AsynchronousLoadingStrategy extends AbstractLoadingStrategy {

    public AsynchronousLoadingStrategy(DataLoader dataLoader, LoadingStateHandler loadingStateHandler) {
        super(dataLoader, loadingStateHandler);
    }

    @Override
    public void loadAllData(ReadOnlyEntityTableModel<Address> addressTableModel) {

        // Does this even work?
        addressTableModel.setRows(List.of());
        // Does this even work?
        loadingStateHandler.setLoading(true);

        SwingWorker<List<Address>, Void> swingWorker = new SwingWorker<>() {

            @Override
            protected List<Address> doInBackground() throws IOException {
                // This method is executed in background thread
                return dataLoader.loadData();
            }

            @Override
            protected void done() {
                // this method is executed in EDT when the doInBackground() method is finished,
                // either successfully or with exception
                try {
                    var addresses = get();
                    addressTableModel.setRows(addresses);
                } catch (InterruptedException e) {
                    // InterruptedException is thrown by get() method when this thread was interrupted while waiting
                    // until the doInBackground() is finished. It should never happen in this case, because done()
                    // method is called after the doInBackground() is finished
                    throw new AssertionError(e);
                } catch (ExecutionException e) {
                    // this means that some exception has been thrown in doInBackground()
                    // such exception is set as a cause of the ExecutionException
                    handleError(e.getCause());
                } finally {
                    // Does this even work?
                    loadingStateHandler.setLoading(false);
                }
            }
        };

        swingWorker.execute();
    }
}
