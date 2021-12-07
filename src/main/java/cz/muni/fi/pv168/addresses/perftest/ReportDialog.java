package cz.muni.fi.pv168.addresses.perftest;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportDialog {

    private static final Logger logger = Logger.getLogger(ReportDialog.class.getName());

    public static void show(PerformanceTestReport report) {
        try {
            EventQueue.invokeAndWait(() -> {
                JTextPane textPane = new JTextPane();
                textPane.setContentType("text/html");
                textPane.setEditable(false);
                textPane.setText(report.toHtmlString());
                JOptionPane.showMessageDialog(null,
                        new JScrollPane(textPane),
                        "Performance test was finished",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            logger.log(Level.SEVERE, "Error when showing ReportDialog", e.getCause());
        }
    }

}
