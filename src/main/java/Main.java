import UI.*;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import com.formdev.flatlaf.FlatLaf;

public class Main {

    public static void main(String[] args) {

        System.setProperty("sun.java2d.uiScale", "1.0");

        //SwingUtilities.invokeLater(SplashScreen::new);
        SwingUtilities.invokeLater(UI::new);

        // Shows available fonts
        //System.out.println(Arrays.toString(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));

        //System.out.println(UIManager.getFont("Label.font").getName());
    }
}
