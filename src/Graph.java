import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Graph {

    ArrayList<Edge> edgeList;
    int vertices;
    int edges;
    int interactions;
    int[] solution;

    public Graph(File file) throws IOException {
        edgeList = new ArrayList<>();

        fillData(file);
        System.out.println(this);
    }

    public int trepa_colinas() {
        int cost, neighborCost;

        //calculate the cost of the Initial Solution
        cost = calculate_Cost(solution);
        for (int i = 0; i < interactions; i++) {
            //Create the neighbor
            int[] new_Solution = create_Neighbors();
            //Calculates the cost of the new neighbor
            neighborCost = calculate_Cost(new_Solution);
            //If the neighbor cost is lower than the initial cost, swap them (Minimization problem)
            if (neighborCost < cost) {
                this.solution = new_Solution;
                cost = neighborCost;
            }
        }
        return cost;
    }

    private int calculate_Cost(int[] sol) {

        //to redo
//        int total = 0;
//
//        for (int i = 0; i < vertices; i++) {
//            if (sol[i]==0)
//            {
//                for (int j = 0; j < vertices; j++) {
//                    if (sol[j]==1 && matrix[i][j]==1)
//                        total++;
//                }
//            }
//        }
//
        return 42;
    }

    private int[] create_Neighbors() {
        int[] neighbor_Solution = new int[vertices];
        Random random = new Random();
        for (int i = 0; i < vertices; i++) {
            neighbor_Solution[i] = solution[i];
        }
        int p1, p2;
        // Find positions with value 0
        do {
            p1 = random.nextInt(vertices);
        } while (neighbor_Solution[p1] != 0);

        do {
            p2 = random.nextInt(vertices);
        } while (neighbor_Solution[p2] != 1);

        //Switch
        neighbor_Solution[p1] = 1;
        neighbor_Solution[p2] = 0;

        return neighbor_Solution;
    }

    public void fillData(File file) throws IOException {
        Scanner scanner = new Scanner(file);

        if(!scanner.next().equals("k")) {
            System.out.println("Wrong file type");
            return;
        }
        if (scanner.hasNextInt()) {
            if(!scanner.next().equals("p") && !scanner.next().equals("edge"))
                return;

            this.interactions = scanner.nextInt();
            this.vertices = scanner.nextInt();

            this.solution = new int[vertices];

            int start, end, cost;

            for (int i = 0; i < vertices; i++) {
                if (!scanner.next().equals("e"))
                    return;
                for (int j = 0; j < vertices; j++) {
                    start = scanner.nextInt();
                    end = scanner.nextInt();
                    cost = scanner.nextInt();

                    edgeList.add(new Edge(start,end,cost));
                }
            }
        } else {
            System.out.println("Empty File!");
        }
    }

    public void create_Start_Solution() {
        Random random = new Random();
        for (int i = 0; i < vertices; i++) {
            solution[i] = 0;
        }
        for (int i = 0, x; i < vertices / 2; i++) {
            do {
                x = random.nextInt(vertices);
            } while (solution[x] != 0);
            solution[x] = 1;
        }
    }

    public int[] getSolution() {
        return solution;
    }

    public int getVertices() {
        return vertices;
    }

    private void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getInteractions() {
        return interactions;
    }

    private void setInteractions(int interactions) {
        this.interactions = interactions;
    }

    public void printSolution(int[] solution) {
        System.out.print("0s: ");
        for (int i = 0; i < vertices; i++) {
            if (solution[i]==0)
                System.out.print(i + " ");
        }
        System.out.print("\n1s: ");
        for (int i = 0; i < vertices; i++) {
            if (solution[i]==1)
                System.out.print(i + " ");
        }
        System.out.println();
    }

}
