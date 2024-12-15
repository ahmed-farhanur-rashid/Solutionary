package Interpolation;

import java.util.List;

public class Lagrange {

    public static double solve (List<Double> xList, List<Double> yList, int n, double x) {


        double result = 0.0;

        for (int i = 0; i < n; i++) {

            double term = yList.get(i);

            for (int j = 0; j < n; j++) {

                if (i != j) {

                    term *= ((x - xList.get(j)) / (xList.get(i) - xList.get(j)));
                }
            }
            result += term;
        }

        return result;
    }
}
