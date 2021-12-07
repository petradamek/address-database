package cz.muni.fi.pv168.addresses.perftest;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

public class ConfigurationSelector {

    private volatile Configuration configuration;

    static Configuration selectConfiguration() {
        var selector = new ConfigurationSelector();
        selector.waitForUserInput();
        return selector.configuration;
    }

    private void waitForUserInput() {
        try {
            EventQueue.invokeAndWait(this::showDialog);
        } catch (InterruptedException ex) {
            throw new AssertionError(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Error when selecting configuration", ex.getCause());
        }
    }

    private void showDialog() {
        JComboBox<Configuration> comboBox = new JComboBox<>(Configuration.values());
        int result = JOptionPane.showConfirmDialog(null, comboBox,
                "Select AddressFinder implementation", JOptionPane.DEFAULT_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            configuration = (Configuration) comboBox.getSelectedItem();
        }
    }
}
