package Extra_Tools;

public class Draft {

    int m = 5;
    int n = 5;

    Draft (double [][] mat) {
        for (int i = 1; i < m; i++) {
            for (int j = i; j < n; j++) {
                double a = mat[i-1][i-1];
                double b = mat[i][i-1];
                for (int k = i-1; k < n; k++) {
                    mat[j][k] = a*mat[j][k] - b*mat[j-1][k];;
                }
            }
        }
    }
}
