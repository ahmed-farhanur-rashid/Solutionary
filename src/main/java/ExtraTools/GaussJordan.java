package ExtraTools;

public class GaussJordan {

    /**
     * Solves a system of linear equations using Gauss-Jordan Elimination.
     *
     * @param matrix The augmented matrix (coefficients + constants).
     * @return A solution array, or null if no unique solution exists.
     */
    public static double[] solve(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Perform Gauss-Jordan Elimination
        for (int i = 0; i < rows; i++) {
            // Find the pivot element
            int pivotRow = i;
            for (int k = i + 1; k < rows; k++) {
                if (Math.abs(matrix[k][i]) > Math.abs(matrix[pivotRow][i])) {
                    pivotRow = k;
                }
            }

            // Swap rows to place pivot at the diagonal
            double[] temp = matrix[i];
            matrix[i] = matrix[pivotRow];
            matrix[pivotRow] = temp;

            // Check for singularity
            if (Math.abs(matrix[i][i]) < 1e-10) {
                return null; // No unique solution
            }

            // Normalize the pivot row
            double pivot = matrix[i][i];
            for (int j = 0; j < cols; j++) {
                matrix[i][j] /= pivot;
            }

            // Eliminate all other entries in the current column
            for (int k = 0; k < rows; k++) {
                if (k != i) {
                    double factor = matrix[k][i];
                    for (int j = 0; j < cols; j++) {
                        matrix[k][j] -= factor * matrix[i][j];
                    }
                }
            }
        }

        // Extract solutions from the last column
        double[] solution = new double[rows];
        for (int i = 0; i < rows; i++) {
            solution[i] = matrix[i][cols - 1];
        }

        return solution;
    }
}
