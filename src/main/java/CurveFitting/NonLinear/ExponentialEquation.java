package CurveFitting.NonLinear;

// y = a*e^(bx)

import ExtraTools.GaussJordan;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ExponentialEquation {

    public static double[] fit (List<Double> xList, List<Double> yList, int n, DefaultTableModel tableModel) {

        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXSquared = 0;

        for(int i = 0; i < n; i++) {

            double X = xList.get(i);
            double ySmall = yList.get(i); // Small y
            double Y = Math.log(ySmall); // Capital Y, Natural Log of i-th element as yList.get(i)
            double XY = X * Y;
            double xSquared = X * X;

            sumX += X;
            sumY += Y;
            sumXY += XY;
            sumXSquared += xSquared;

            tableModel.addRow(new Object[]{X, ySmall, Y, XY, xSquared});
        }
        tableModel.addRow(new Object[]{"", "", "", ""});
        tableModel.addRow(new Object[]{"ΣX", "", "ΣY", "ΣXY", "ΣX²"});
        tableModel.addRow(new Object[]{sumX, "" ,sumY, sumXY, sumXSquared});

        // Creating 2D matrix to find solution using Gauss-Jordan Method

        double[][] matrix = {
                {sumX, n, sumY},
                {sumXSquared, sumX, sumXY}
        };

        return GaussJordan.solve(matrix);
    }
}
