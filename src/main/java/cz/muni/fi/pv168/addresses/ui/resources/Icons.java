package cz.muni.fi.pv168.addresses.ui.resources;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Icons {

    public static final Icon QUIT_ICON = createIcon("Crystal_Clear_action_exit.png");
    public static final Icon RELOAD_ICON = createIcon("Crystal_Clear_action_reload.png");

    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    private static ImageIcon createIcon(String name) {
        URL url = Icons.class.getResource(name);
        if (url == null) {
            throw new IllegalArgumentException("Icon resource not found on classpath: " + name);
        }
        return new ImageIcon(url);
    }
}
