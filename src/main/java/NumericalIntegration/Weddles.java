package NumericalIntegration;

import UI.ErrorUtil;
import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;
import java.util.List;

public class Weddles {

    public static double calculate (Expression expression, double a, double b, int n) {

        if (n % 6 == 0) {
            double h = (b - a) / n;

            //List<Double> xList = new ArrayList<>(n+1);
            List<Double> yList = new ArrayList<>(n + 1);

            // Creating abstract table
            for (int i = 0; i <= n; i++) {
                double x = a + (h * i);

                //xList.add(x);    Is redundant now, but will be used in future to show steps

                yList.add(f(expression, x));
            }

            double firstTerm = 0;
            double secondTerm = 0;
            double thirdTerm = 0;

            // Summing terms

            for (int i = 0; i <= n; i++) {
                if (i % 2 == 0) {
                    firstTerm += yList.get(i);
                }
                if (i % 2 == 1 && i <= n - 1) {
                    secondTerm += yList.get(i);
                }
                if (i % 3 == 0 && i <= n - 3) {
                    thirdTerm += yList.get(i);
                }
            }

            return (3 * h / 10) * (firstTerm + 5 * secondTerm + thirdTerm);
        } else {
            ErrorUtil.weddles_Illegal_Interval();
            return Double.NaN;
        }
    }

    private static double f(Expression expression, double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }
}
