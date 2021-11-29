package cz.muni.fi.pv168.addresses.ui.action;

import cz.muni.fi.pv168.addresses.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class ReloadAction extends AbstractAction {

    private final Runnable reloadOperation;

    public ReloadAction(Runnable reloadOperation) {
        this.reloadOperation = reloadOperation;
        putValue(NAME, "Reload");
        putValue(SMALL_ICON, Icons.RELOAD_ICON);
        putValue(MNEMONIC_KEY, KeyEvent.VK_R);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        reloadOperation.run();
    }
}
