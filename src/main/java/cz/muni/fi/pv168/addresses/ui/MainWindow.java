package cz.muni.fi.pv168.addresses.ui;

import cz.muni.fi.pv168.addresses.loader.DataLoader;
import cz.muni.fi.pv168.addresses.loader.DataLoaderFactory;
import cz.muni.fi.pv168.addresses.model.Address;
import cz.muni.fi.pv168.addresses.ui.action.QuitAction;
import cz.muni.fi.pv168.addresses.ui.action.ReloadAction;
import cz.muni.fi.pv168.addresses.ui.model.AddressesTableModel;
import cz.muni.fi.pv168.addresses.ui.model.ReadOnlyEntityTableModel;
import cz.muni.fi.pv168.addresses.ui.renderer.HouseNoTypeRenderer;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.UncheckedIOException;

public class MainWindow {

    private final JFrame frame;

    private final Action quitAction = new QuitAction();
    private final Action reloadAction = new ReloadAction(this::reloadAddresses);

    private final ReadOnlyEntityTableModel<Address> addressesTableModel = new AddressesTableModel();
    private final DataLoader dataLoader;

    public MainWindow(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
        this.frame = createFrame();
        var addressTable = new JTable(addressesTableModel);
        addressTable.setDefaultRenderer(Address.HouseNoType.class, new HouseNoTypeRenderer());
        frame.add(new JScrollPane(addressTable), BorderLayout.CENTER);
        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
    }

    public static void main(String... args) {
        EventQueue.invokeLater(() -> new MainWindow(DataLoaderFactory.newDataLoader()).show());
    }

    public void show() {
        frame.setVisible(true);
        reloadAddresses();
    }

    private void reloadAddresses() {
        try {
            addressesTableModel.setRows(dataLoader.loadData());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private JFrame createFrame() {
        var frame = new JFrame("Address Database");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var editMenu = new JMenu("File");
        editMenu.setMnemonic('e');
        editMenu.add(reloadAction);
        editMenu.addSeparator();
        editMenu.add(quitAction);
        menuBar.add(editMenu);
        return menuBar;
    }

    private JToolBar createToolbar() {
        var toolbar = new JToolBar();
        toolbar.add(quitAction);
        toolbar.addSeparator();
        toolbar.add(reloadAction);
        return toolbar;
    }
}
