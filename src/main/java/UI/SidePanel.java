package UI;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {

    public SidePanel(Color panelColor, Color frameColor, int frameWidth, int frameHeight) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Used BoxLayout for vertical alignment
        setOpaque(true);
        setBackground(panelColor);
        setPreferredSize(new Dimension(frameWidth, frameHeight));  // Set panel to match frame height
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(frameColor, 5),  // Add a line border
                BorderFactory.createEmptyBorder(10, 10, 10, 10)    // Add padding on the left (10px)
        ));
    }
}