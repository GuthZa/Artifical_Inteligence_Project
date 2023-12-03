public class Edge {
    int start, end, cost;

    public Edge(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getCost() {
        return cost;
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