package CurveFitting.Linear;

import ExtraTools.GaussJordan;

import javax.swing.table.DefaultTableModel;
import java.util.List;

// y = ax +  b

public class LinearEquation {

    public static double[] fit (List<Double> xList, List<Double> yList, int n, DefaultTableModel tableModel) {

        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXSquared = 0;

        for(int i = 0; i < n; i++) {

            double X = xList.get(i);
            double Y = yList.get(i);
            double XY = X * Y;
            double xSquared = X * X;

            sumX += X;
            sumY += Y;
            sumXY += XY;
            sumXSquared += xSquared;

            tableModel.addRow(new Object[]{X, Y, XY, xSquared});
        }
        tableModel.addRow(new Object[]{"", "", "", ""});
        tableModel.addRow(new Object[]{"Σx", "Σy", "Σxy", "Σx²"});
        tableModel.addRow(new Object[]{sumX, sumY, sumXY, sumXSquared});

        // Creating 2D matrix to find solution using Gauss-Jordan Method

        double[][] matrix = {
                {sumX, n, sumY},
                {sumXSquared, sumX, sumXY}
        };

        return GaussJordan.solve(matrix);
    }
}
