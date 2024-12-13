package NumericalDifferentiation;

import net.objecthunter.exp4j.Expression;

import javax.swing.*;
import java.awt.*;

public class RungeKutta {

    /**
     * Runge-Kutta 1st Order (RK1)
     */
    public static void Runge_Kutta_1st_Order (Expression expression, double h, double x, double y, double xn, JPanel panel) {

        int n = (int) ((xn - x) / h);

        panel.removeAll();   // Clear previous steps to start fresh
        panel.revalidate();
        panel.repaint();

        for (int i = 0; i < n; i++) {

            double y_next = y + h * f(expression, x, y);

            JLabel stepLabel = new JLabel("<html><b>"
                    + "Iteration - " + (i+1) + ":"
                    + "K<sub>n+" + (i + 1) + "</sub> = " + y_next
                    + "</b></html>");
            stepLabel.setFont (new Font("Ariel", Font.BOLD, 16));
            panel.add(stepLabel);
            x += h;
            y = y_next;
        }

        panel.revalidate();
        panel.repaint();
    }

    /**
     * Runge-Kutta 2nd Order (RK2)
     */
    public static void Runge_Kutta_2nd_Order (Expression expression, double h, double x, double y, double xn, JPanel panel) {

        int n = (int) ((xn - x) / h);

        panel.removeAll();   // Clear previous steps to start fresh
        panel.revalidate();  // Recalculate layout
        panel.repaint();     // Redraw panel

        for (int i = 0; i < n; i++) {

            double k1 = h * f(expression, x, y);
            double k2 = h * f(expression, x + h/2, y + k1/2);
            double y_next = y + 0.5 * (k1 + k2);

            JLabel stepLabel = new JLabel("<html><b>"
                    + "Iteration - " + (i+1) + ":  <br>"
                    + "K<sub>n+1</sub> = " + k1 + "<br>"
                    + "K<sub>n+2</sub> = " + k2 + "<br>"
                    + "Y<sub>n+" + (i + 1) + "</sub> = " + y_next
                    + "<br><br></b></html>");
            stepLabel.setFont (new Font("Ariel", Font.BOLD, 16));
            panel.add(stepLabel);
            x += h;
            y = y_next;
        }

        panel.revalidate();
        panel.repaint();
    }

    /**
     * Runge-Kutta 3rd Order (RK3)
     */
    public static void Runge_Kutta_3rd_Order (Expression expression, double h, double x, double y, double xn, JPanel panel) {

        int n = (int) ((xn - x) / h);

        panel.removeAll();   // Clear previous steps to start fresh
        panel.revalidate();  // Recalculate layout
        panel.repaint();     // Redraw panel

        for (int i = 0; i < n; i++) {

            double k1 = h * f(expression, x, y);
            double k2 = h * f(expression, x + h/2, y + k1/2);
            double k3 = h * f(expression, x + h, y - k1 + 2*k2);
            double y_next = y + ((1.0 / 6.0) * (k1 + 4*k2 + k3));

            JLabel stepLabel = new JLabel("<html><b>"
                    + "Iteration - " + (i+1) + ":  <br>"
                    + "K<sub>n+1</sub> = " + k1 + "<br>"
                    + "K<sub>n+2</sub> = " + k2 + "<br>"
                    + "K<sub>n+3</sub> = " + k3 + "<br>"
                    + "Y<sub>n+" + (i + 1) + "</sub> = " + y_next
                    + "<br><br></b></html>");
            stepLabel.setFont (new Font("Ariel", Font.BOLD, 16));
            panel.add(stepLabel);
            x += h;
            y = y_next;
        }

        panel.revalidate();
        panel.repaint();
    }

    /**
     * Runge-Kutta 4th Order (RK4)
     */
    public static void Runge_Kutta_4th_Order (Expression expression, double h, double x, double y, double xn, JPanel panel) {

        int n = (int) ((xn - x) / h);

        panel.removeAll();   // Clear previous steps to start fresh
        panel.revalidate();  // Recalculate layout
        panel.repaint();     // Redraw panel

        for (int i = 0; i < n; i++) {

            double k1 = h * f(expression, x, y);
            double k2 = h * f(expression, x + h/2, y + k1/2);
            double k3 = h * f(expression, x + h/2, y + k2/2);
            double k4 = h * f(expression, x + h, y + k3);
            double y_next = y + ((1.0 / 6.0) * (k1 + 2*k2 + 2*k3 + k4));

            JLabel stepLabel = new JLabel("<html><b>"
                    + "Iteration - " + (i+1) + ":  <br>"
                    + "K<sub>n+1</sub> = " + k1 + "<br>"
                    + "K<sub>n+2</sub> = " + k2 + "<br>"
                    + "K<sub>n+3</sub> = " + k3 + "<br>"
                    + "K<sub>n+4</sub> = " + k4 + "<br>"
                    + "Y<sub>n+" + (i + 1) + "</sub> = " + y_next
                    + "<br><br></b></html>");
            stepLabel.setFont (new Font("Ariel", Font.BOLD, 16));
            panel.add(stepLabel);
            x += h;
            y = y_next;
        }

        panel.revalidate();
        panel.repaint();
    }

    private static double f(Expression expression, double x, double y) {
        expression.setVariable("x", x);
        expression.setVariable("y", y);
        return expression.evaluate();
    }
}
