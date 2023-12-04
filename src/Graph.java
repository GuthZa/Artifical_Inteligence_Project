import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Graph {

    private ArrayList<Edge> edgeList;

    //p, edge, k
    private int vertices, edges, k;
    private int[] solution;

    public Graph(File file) throws IOException {
        fillData(file);
    }

    public int hill_climbing() {
        int cost, neighborCost;

        //calculate the cost of the Initial Solution
        cost = func.calculate_cost(solution, edgeList, vertices);
        for (int i = 0; i < 100; i++) { //TODO CHANGE HARDCODED NUMBER TO THE NUMBER ON INTERACTION
            //Create the neighbor
            int[] new_Solution = create_Neighbors();
            //Calculates the cost of the new neighbor
            neighborCost = func.calculate_cost(new_Solution, edgeList, vertices);
            //If the neighbor cost is lower than the initial cost, swap them (Minimization problem)
            if (neighborCost != 0 && neighborCost < cost) {
                this.solution = new_Solution;
                cost = neighborCost;
            }

            //Generate a second neighbor
            //Calculates the cost of the new neighbor
            neighborCost = func.calculate_cost(new_Solution, edgeList, vertices);
            //If the neighbor cost is lower than the initial cost, swap them (Minimization problem)
            if (neighborCost != 0 && neighborCost < cost) {
                this.solution = new_Solution;
                cost = neighborCost;
            }
        }
        return cost;
    }
    private int[] create_Neighbors() {
        int[] neighbor_Solution = new int[vertices];
        Random random = new Random();
        //copy everything from the solution into the neighbor_solution
        if (vertices >= 0) System.arraycopy(solution, 0, neighbor_Solution, 0, vertices);
        int p1, p2;
        // Find positions with value 0
        do {
            p1 = random.nextInt(vertices - 1);
        } while (neighbor_Solution[p1] != 0);
        //find positions with value 1
        do {
            p2 = random.nextInt(vertices - 1);
        } while (neighbor_Solution[p2] != 1);

        //Switch
        neighbor_Solution[p1] = 1;
        neighbor_Solution[p2] = 0;

        return neighbor_Solution;
    }

    public void fillData(File file) throws IOException {
        Scanner scanner = new Scanner(file);

        //check if the file is correct
        if (!scanner.next().equals("k")) {
            System.out.println("Wrong file type");
            return;
        }

        edgeList = new ArrayList<>();

        //number of 1s in the solution
        this.k = Integer.parseInt(scanner.next());

        //just another check to see if were on the right track
        if (!scanner.next().equals("p")) return;
        if (!scanner.next().equals("edge")) return;

        this.vertices = Integer.parseInt(scanner.next()); //p
        this.edges = Integer.parseInt(scanner.next()); //edge

        this.solution = new int[vertices];

        int start, end, cost;

        while (scanner.hasNextLine()) {
            if(!scanner.next().equals("e")) return;

            start = Integer.parseInt(scanner.next());
            end = Integer.parseInt(scanner.next());
            cost = Integer.parseInt(scanner.next());

            edgeList.add(new Edge(start, end, cost));
        }
    }

    public void create_Start_Solution() {
        Random random = new Random();
        int position;
        for (int i = 0; i < vertices; i++) {
            solution[i] = 0;
        }
        for (int i = 0; i < k; i++) {
            do {
                position = random.nextInt(vertices - 1);
            } while (solution[position]!=0);
            solution[position] = 1;
        }
    }

    public int[] getSolution() {
        return solution;
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    public int getK() {
        return k;
    }

    public void printSolution(int[] solution) {
        System.out.print("0s: ");
        for (int i = 0; i < vertices; i++) {
            if (solution[i]==0)
                System.out.print((i+1) + " ");
        }
        System.out.print("\n1s: ");
        for (int i = 0; i < vertices; i++) {
            if (solution[i]==1)
                System.out.print((i+1) + " ");
        }
        System.out.println();
    }
}
