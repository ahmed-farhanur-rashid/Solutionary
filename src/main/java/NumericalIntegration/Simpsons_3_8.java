package NumericalIntegration;

import UI.ErrorUtil;
import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;
import java.util.List;

public class Simpsons_3_8 {

    public static double calculate (Expression expression, double a, double b, int n) {

        if (n % 3 == 0) {
            double h = (b - a) / n;

            //List<Double> xList = new ArrayList<>(n+1);
            List<Double> yList = new ArrayList<>(n + 1);

            // Creating abstract table
            for (int i = 0; i <= n; i++) {
                double x = a + (h * i);

                //xList.add(x);    Is redundant now, but will be used in future to show steps

                yList.add(f(expression, x));
            }

            double firstTerm;
            double secondTerm = 0;
            double thirdTerm = 0;

            // Summing terms

            //noinspection SequencedCollectionMethodCanBeUsed
            firstTerm = yList.get(0) + yList.get(n);

            for (int i = 1; i <= n - 1; i++) {

                if (i % 3 == 0 && i <= n - 3) {
                    thirdTerm += yList.get(i);
                } else {
                    secondTerm += yList.get(i);
                }
            }

            return (3*h / 8) * (firstTerm + 3 * secondTerm + 2 * thirdTerm);
        } else {
            ErrorUtil.simsons_3_8th_Illegal_Interval();
            return Double.NaN;
        }
    }

    private static double f(Expression expression, double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }
}
