import java.util.ArrayList;
import java.util.Arrays;

public class Edge {
    int start;
    ArrayList<Integer> end, cost;

    public Edge(int start, int end, int cost) {
        this.end = new ArrayList<>();
        this.cost = new ArrayList<>();
        this.start = start;
        this.end.add(end);
        this.cost.add(cost);
    }
    public int getStart() {
        return start;
    }

    public ArrayList<Integer> getEnd() {
        return end;
    }

    public ArrayList<Integer> getCost() {
        return cost;
    }

    public void add_Edge(int end, int cost) {
        this.end.add(end);
        this.cost.add(cost);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", end=" + end +
                ", cost=" + cost +
                '}';
    }
}