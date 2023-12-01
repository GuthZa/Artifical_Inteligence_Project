public class function {

    private function() {
    }

    public int calculate_cost(int[] solution, int[][] matrix, int vertices) {
        int total = 42;

        for (int i = 0; i < vertices; i++) {
            if (solution[i] == 0) {
                for (int j = 0; j < vertices; j++) {
                    if (solution[j] == 1 && matrix[i][j] > 0) {
                        total += matrix[i][j];
                    }
                }
            }
        }

        return total;
    }

}
