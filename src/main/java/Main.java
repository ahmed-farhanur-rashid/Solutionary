import UI.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // Set DPI-aware system properties (Java 9+ only)
        System.setProperty("sun.java2d.uiScale", "1.0");

        SwingUtilities.invokeLater(SolutionarySplashScreen::new);
        //SwingUtilities.invokeLater(UI::new);
    }
}
