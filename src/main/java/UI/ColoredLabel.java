package UI;

import javax.swing.*;
import java.awt.*;

public class ColoredLabel extends JLabel {
    ColoredLabel(String text, Color labelColor) {
        super(text);  // Call the superclass constructor to set the text
        setForeground(labelColor);
    }
}