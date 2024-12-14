package CurveFitting.NonLinear;

//  y = ax^2 + bx + c

import ExtraTools.GaussJordan;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class QuadraticEquation {

    public static double[] fit (List<Double> xList, List<Double> yList, int n, DefaultTableModel tableModel) {

        double sumX = 0;
        double sumY = 0;

        double sumXY = 0;
        double sumXSquaredY = 0;

        double sumXSquared = 0;
        double sumXCubed = 0;
        double sumXQuartic = 0;

        for(int i = 0; i < n; i++) {

            double X = xList.get(i);
            double Y = yList.get(i); // Capital Y, Natural Log of i-th element as yList.get(i)

            double XY = X * Y;
            double xSquaredY = X * XY;

            double xSquared = X * X;

            double xCubed = X * xSquared;
            double xQuartic = X * xCubed;

            sumX += X;
            sumY += Y;

            sumXY += XY;
            sumXSquaredY += xSquaredY;

            sumXSquared += xSquared;
            sumXCubed += xCubed;
            sumXQuartic += xQuartic;

            tableModel.addRow(new Object[]{X, Y, XY, xSquaredY, xSquared, xCubed, xQuartic});
        }
        tableModel.addRow(new Object[]{"", "", "", "", "", "", ""});
        tableModel.addRow(new Object[]{"ΣX", "ΣY", "ΣXY", "ΣX²Y", "ΣX²", "ΣX³", "ΣX⁴"});
        tableModel.addRow(new Object[]{sumX ,sumY, sumXY, sumXSquaredY, sumXSquared, sumXCubed, sumXQuartic});

        // Creating 3x3 2D matrix to find solution using Gauss-Jordan Method

        double[][] matrix = {
                {sumXSquared, sumX, n, sumY},
                {sumXCubed, sumXSquared, sumX, sumXY},
                {sumXQuartic, sumXCubed, sumXSquared, sumXSquaredY}
        };

        return GaussJordan.solve(matrix);
    }
}
