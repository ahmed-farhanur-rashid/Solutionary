package UI;

import javax.swing.*;

public class ErrorUtils {

    public static void noImageError() {
        JOptionPane.showMessageDialog(
                null,
                "Failed to load splash screen image! Please check the file path.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void interruptionError() {
        JOptionPane.showMessageDialog(
                null,
                "Thread interrupted!",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void noIntervalError() {
        JOptionPane.showMessageDialog(
                null,
                "No interval found in range [-5K, 5K]!\nPlease input manually!",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void iterationLimitError () {
        JOptionPane.showMessageDialog(null,
                "Max iteration limit exceeded!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void divisionByZeroError () {
        JOptionPane.showMessageDialog(null,
                "Division by zero occurred!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
