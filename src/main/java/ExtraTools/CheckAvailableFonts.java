package ExtraTools;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CheckAvailableFonts {

    public static void main(String[] args) {

        System.out.println(Arrays.toString(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        System.out.println(UIManager.getFont("Label.font").getName());
    }
}