package UI;

import javax.swing.*;

public class ErrorUtil {

    // Execution Errors
    public static void noImageError() {
        JOptionPane.showMessageDialog(
                null,
                "Failed to load splash screen image! Please check the file path.",
                "Execution Error",
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

    // Input Errors
    public static void simsons_1_3rd_Illegal_Interval () {
        JOptionPane.showMessageDialog(null,
                "Number of subintervals must be even in Simpson's ⅓ rule!",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void simsons_3_8th_Illegal_Interval () {
        JOptionPane.showMessageDialog(null,
                "Number of subintervals must multiple of 3 in Simpson's ⅜ rule!",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void weddles_Illegal_Interval () {
        JOptionPane.showMessageDialog(null,
                "Number of subintervals must multiple of 6 in Weddle's rule!",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void invalidInput () {
        JOptionPane.showMessageDialog(null,
                "Invalid Input Detected!",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void invalidNumberOfRows () {
        JOptionPane.showMessageDialog(null,
                "Please enter a valid positive number for the number of rows.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
    }
}
