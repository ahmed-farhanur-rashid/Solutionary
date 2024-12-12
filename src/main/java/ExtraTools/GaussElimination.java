package ExtraTools;

public class GaussElimination {

    /**
     * Solves a system of linear equations using Gaussian Elimination.
     *
     * @param matrix The augmented matrix (coefficients + constants).
     * @return A solution array, or null if no unique solution exists.
     */
    public static double[] solve(double[][] matrix) {
        int n = matrix.length;

        // Forward elimination
        for (int k = 0; k < n; k++) {
            // Find the maximum element in the column for pivoting
            int max = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(matrix[i][k]) > Math.abs(matrix[max][k])) {
                    max = i;
                }
            }

            // Swap the rows
            double[] temp = matrix[k];
            matrix[k] = matrix[max];
            matrix[max] = temp;

            // Check for singular matrix
            if (Math.abs(matrix[k][k]) < 1e-10) {
                return null; // No unique solution
            }

            // Make all rows below this one 0 in the current column
            for (int i = k + 1; i < n; i++) {
                double factor = matrix[i][k] / matrix[k][k];
                for (int j = k; j <= n; j++) { // Include the last column (constants)
                    matrix[i][j] -= factor * matrix[k][j];
                }
            }
        }

        // Back substitution
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix[i][j] * solution[j];
            }
            solution[i] = (matrix[i][n] - sum) / matrix[i][i];
        }

        return solution;
    }
}

//----------------------------------------------------------------
//                     Driver Code Below
//----------------------------------------------------------------

//public static void main(String[] args) {
//    // Example usage
//    double[][] matrix = {
//            {0, 0, -1, 3},
//            {1, -3, 2, 2},
//            {-1, 2, -1, -1}
//    };
//
//    double[] solution = solve(matrix);
//    if (solution != null) {
//        System.out.println("Solution:");
//        for (double x : solution) {
//            System.out.printf("%.2f ", x);
//        }
//    } else {
//        System.out.println("No unique solution exists.");
//    }
//}