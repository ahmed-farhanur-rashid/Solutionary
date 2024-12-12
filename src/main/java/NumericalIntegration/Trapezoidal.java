package NumericalIntegration;

import net.objecthunter.exp4j.Expression;

import java.util.List;
import java.util.ArrayList;

public class Trapezoidal {

    public static double calculate (Expression expression, double a, double b, int n) {

        double h = (b - a) / n;

        //List<Double> xList = new ArrayList<>(n+1);
        List<Double> yList = new ArrayList<>(n+1);

        // Creating abstract table
        for (int i = 0; i <= n; i++) {
            double x = a + (h * i);

            //xList.add(x);    Is redundant now, but will be used in future to show steps

            yList.add(f(expression, x));
        }

        double firstTerm;
        double secondTerm = 0;

        // Summing terms

        //noinspection SequencedCollectionMethodCanBeUsed
        firstTerm = yList.get(0) + yList.get(n);

        for (int i = 1; i <= n - 1; i++) {
            secondTerm += yList.get(i);
        }

        return (h / 2) * (firstTerm + 2 * secondTerm);
    }

    private static double f(Expression expression, double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }
}
