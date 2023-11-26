import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Graph {

    ArrayList<Edge> edgeList;
    int vertices, edges, solution_size;
    int[] solution;

    public Graph(File file) throws IOException {
        edgeList = new ArrayList<>();

        fillData(file);
    }

    public int hill_climbing() {
        int cost, neighborCost;

        //calculate the cost of the Initial Solution
        cost = calculate_Cost(solution);
        for (int i = 0; i < 10; i++) { //TODO CHANGE HARDCODED NUMBER TO THE NUMBER ON INTERACTION
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
        int[] neighbor_Solution = new int[solution_size];
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

        //Receive K, such that exists a smaller sample of size k to find the minimum cost
        this.solution_size = Integer.parseInt(scanner.next());
        //just another check to see if were on the right track
        if (!scanner.next().equals("p"))
            return;
        if (!scanner.next().equals("edge"))
            return;

        this.vertices = Integer.parseInt(scanner.next());
        this.edges = Integer.parseInt(scanner.next());

        this.solution = new int[solution_size];

        ArrayList<Integer> end = new ArrayList<>(), cost = new ArrayList<>();
        int start = 0, last_number = 0;
        boolean last_cycle = false;
        String line = null;
        for (int i = 0; i <= edges; i++) {
            //to read the "e" and the last lines
            if (!scanner.hasNextLine() || ( !(line = scanner.next()).isEmpty() && !line.equals("e") ))
                last_cycle = true;

            if (last_cycle || ((start = Integer.parseInt(scanner.next())) != last_number && last_number != 0)) {
                //Should have the same size, since it's the number of connection of the point
                int[] end_list = new int[end.size()];
                int[] cost_list = new int[cost.size()];
                //fill the array with the cost and point values
                for (int k = 0; k < end.size(); k++) {
                    end_list[k] = end.get(k);
                }
                for (int k = 0; k < cost.size(); k++) {
                    cost_list[k] = cost.get(k);
                }
                edgeList.add(new Edge(last_number, end_list, cost_list));
                end = new ArrayList<>();
                cost = new ArrayList<>();
            }
            if (!last_cycle) {
                end.add(Integer.parseInt(scanner.next()));
                cost.add(Integer.parseInt(scanner.next()));
                last_number = start;
            }
        }

        edgeList.forEach(System.out::println);
    }

    public void create_Start_Solution() {
        Random random = new Random();
        for (int i = 0; i < solution_size; i++) {
            solution[i] = random.nextInt(10);
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
        for (int i = 0; i < solution_size; i++) {
            if (solution[i]==0)
                System.out.print(i + " ");
        }
        System.out.print("\n1s: ");
        for (int i = 0; i < solution_size; i++) {
            if (solution[i]==1)
                System.out.print(i + " ");
        }
        System.out.println();
    }

}
