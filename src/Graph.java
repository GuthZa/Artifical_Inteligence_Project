import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Graph {

    ArrayList<Edge> edgeList;
    int[][] matrix;

    //p, edge, k
    int vertices, edges, k;
    int[] solution;

    public Graph(File file) throws IOException {
        fillData(file);
    }

    public int hill_climbing() {
        int cost = 0, neighborCost;
//
//        //calculate the cost of the Initial Solution
//        cost = calculate_Cost(solution);
//        for (int i = 0; i < 10; i++) { //TODO CHANGE HARDCODED NUMBER TO THE NUMBER ON INTERACTION
//            //Create the neighbor
//            int[] new_Solution = create_Neighbors();
//            //Calculates the cost of the new neighbor
//            neighborCost = calculate_Cost(new_Solution);
//            //If the neighbor cost is lower than the initial cost, swap them (Minimization problem)
//            if (neighborCost < cost) {
//                this.solution = new_Solution;
//                cost = neighborCost;
//            }
//        }
        return cost;
    }
    private int[] create_Neighbors() {
        int[] neighbor_Solution = new int[k];
        Random random = new Random();
        //copy everything from the solution into the neighbor_solution
//        if (vertices >= 0) System.arraycopy(solution, 0, neighbor_Solution, 0, solution_size);
//        int p1, p2;
//        // Find positions with value 0
//        do {
//            p1 = random.nextInt(vertices);
//        } while (neighbor_Solution[p1] != 0);
//
//        do {
//            p2 = random.nextInt(vertices);
//        } while (neighbor_Solution[p2] != 1);
//
//        //Switch
//        neighbor_Solution[p1] = 1;
//        neighbor_Solution[p2] = 0;

        return neighbor_Solution;
    }

    public void fillData(File file) throws IOException {
        Scanner scanner = new Scanner(file);

        //check if the file is correct
        if (!scanner.next().equals("k")) {
            System.out.println("Wrong file type");
            return;
        }

        //number of 1s in the solution
        this.k = Integer.parseInt(scanner.next());

        //just another check to see if were on the right track
        if (!scanner.next().equals("p")) return;
        if (!scanner.next().equals("edge")) return;

        this.vertices = Integer.parseInt(scanner.next()); //p
        this.edges = Integer.parseInt(scanner.next()); //edge

        this.solution = new int[vertices];

        this.matrix = new int[vertices][vertices];

        int start, end, cost;

        while (scanner.hasNextLine()) {
            if(!scanner.next().equals("e")) return;

            start = Integer.parseInt(scanner.next());
            end = Integer.parseInt(scanner.next());
            cost = Integer.parseInt(scanner.next());

            matrix[start - 1][end - 1] = cost;
            matrix[end - 1][start - 1] = cost;
        }
    }

    public void create_Start_Solution() {
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            //generates a random number between the highest edge and the lowest edge
            solution[i] = random.nextInt(vertices - 1);
        }
        //TODO CREATE SOLUTION WITH RANDOM NUMBER BETWEEN MIN_EDGE & MAX_EDGE
//        for (int i = 0, x; i < solution_size / 2; i++) {
//            do {
//                x = random.nextInt(vertices);
//            } while (solution[x] != 0);
//            solution[x] = 1;
//        }
    }

    public int[] getSolution() {
        return solution;
    }

    public int getVertices() {
        return vertices;
    }


    public void printSolution(int[] solution) {
        System.out.print("0s: ");
        for (int i = 0; i < k; i++) {
            if (solution[i]==0)
                System.out.print(i + " ");
        }
        System.out.print("\n1s: ");
        for (int i = 0; i < k; i++) {
            if (solution[i]==1)
                System.out.print(i + " ");
        }
        System.out.println();
    }

    public void printMatrix() {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
