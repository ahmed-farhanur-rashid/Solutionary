package UI;

import javax.swing.*;

public class ErrorUtils {

    public static void noImageError() {
        JOptionPane.showMessageDialog(
                null,
                "Failed to load splash screen image. Please check the file path.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void interruptionError() {
        JOptionPane.showMessageDialog(
                null,
                "Failed to load splash screen image. Please check the file path.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
