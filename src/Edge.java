import java.util.Arrays;

public class Edge {
    int start;
    int[] end, cost;

    public Edge(int start, int[] end_list, int[] cost_list) {
        this.start = start;
        this.end = end_list;
        this.cost = cost_list;
    }
    public int getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }

    public int[] getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", end=" + Arrays.toString(end) +
                ", cost=" + Arrays.toString(cost) +
                '}';
    }
}