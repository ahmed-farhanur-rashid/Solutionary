package ExtraTools;

import javax.swing.*;
import java.awt.*;

public class ExtraToolsPanel extends JPanel {

    public ExtraToolsPanel(Color panelColor) {

        setLayout(new GridBagLayout());
        setBackground(panelColor);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}
