import java.util.ArrayList;

public class func {

    private func() {
    }

    public static int calculate_cost(int[] solution, ArrayList<Edge> edgeList, int vertices) {
        int total = 0;

        for (int i = 0; i < vertices; i++) {
            if (solution[i] == 1) {
                for (int j =  i + 1; j < vertices; j++) {
                    if (solution[j] == 1) {
                        for (Edge edge : edgeList) {
                            if (edge.getStart() == (i+1) && edge.getEnd() == (j+1) ||
                            edge.getEnd() == (i+1) && edge.getStart() == (j+1)) {
                                total += edge.getCost();
                            }
                        }
                    }
                }
            }
        }

        return total;
    }

}
