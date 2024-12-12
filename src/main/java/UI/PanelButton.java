package UI;

import javax.swing.*;
import java.awt.*;

public class PanelButton extends JButton {

    public PanelButton(String text, String tooltip, Color backgroundColor) {

        super(text);  // Call the JButton constructor with the text

        // Set button properties
        setFocusable(false);  // Disable focus ability
        setBackground(backgroundColor);  // Set background color
        setOpaque(true);  // Ensure the background color shows
        setHorizontalAlignment(SwingConstants.LEFT);  // Align text to the left
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));  // Set max button height limit
        setToolTipText(tooltip);  // Set tooltip for the button

        //setFont(new Font("Calibri", Font.BOLD, 24));  // Set modern font
    }
}
