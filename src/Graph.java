import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {

    private ArrayList<Edge> edgeList;

    private static final int NUM_ITE = 10;
    private static final int POP_SIZE = 10; //Must keep pairs, bc of parents
    private static final float REPAIR_CHANCE = 0.2F;
    private static final float COMBINE_CHANCE = 0.5F;

    //p, edge, k
    private int vertices, k;
    private final ArrayList<Solution> population = new ArrayList<>();

    public Graph() {
        run();
    }

    public void run() {
        String file_name;
        int runs = 10, best_cost = 0;
        double mbf = 0.0;
        Solution best_global, best_local;

        Scanner scanner = new Scanner(System.in);
        System.out.println("File Name: ");
        file_name = scanner.next();

        File file = new File(file_name);
        if (!file.exists()) {
            System.out.println("Error opening the file");
            return;
        }

        try {
            fillData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //initialize the best global with a random solution
        best_global = new Solution(k, vertices, edgeList);

        ArrayList<Solution> parents;

        int i = 0;
        for (; i < runs; i++) {
            System.out.println("Initial: ");
            create_Starting_Population();

            //Generates the parents to use for genetic modifications
            parents = func.tournament(population);

            //Genetic operator, each with its own chance
            func.genetic_operators(parents, population, COMBINE_CHANCE, REPAIR_CHANCE);

            //Applies hill climbing to the solution, trying to find a smaller cost solution
            hill_climbing(population);

            //Check if the solutions are valid
            //They must have k 1s
            func.check_valid_solution(population, k);
            //Checks if they are valid solutions
            //They must not have cost = 0
            func.repair(population, k, edgeList);

            System.out.println("Rep: " + (i+1));
            //Gets the best solution of the population
            best_local = get_best(population);
            System.out.println(best_local);
            mbf += best_local.getCost();
            //Saves the best cost of the whole run
            if (i == 0 || best_cost < best_local.getCost())
                best_global.swap_solution(best_local);
            //For better readability
            System.out.println();
        }

        System.out.println("MBF: " + mbf/i);
        System.out.println("Best solution found: ");
        System.out.println(best_global);
    }

    private void hill_climbing(ArrayList<Solution> population) {
        for (int i = 0; i < NUM_ITE; i++) {
            population.forEach(solution -> {
                //Create the neighbor
                Solution neighbor_Solution = new Solution(vertices, edgeList, solution.getSolution());

                //If the neighbor cost is lower than the initial cost, swap them (Minimization problem)
                if (neighbor_Solution.getCost() != 0 && neighbor_Solution.getCost() < solution.getCost())
                    solution.swap_solution(neighbor_Solution);

                //Generate a second neighbor
                //Create the neighbor
                neighbor_Solution = new Solution(vertices, edgeList, solution.getSolution());

                //If the neighbor cost is lower than the initial cost, swap them (Minimization problem)
                if (neighbor_Solution.getCost() != 0 && neighbor_Solution.getCost() < solution.getCost())
                    solution.swap_solution(neighbor_Solution);
            });
        }
    }
    private void fillData(File file) throws IOException {
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
        //no use for this value, just saves the number of points in the graph
        scanner.next(); //edge

        int start, end, cost;

        while (scanner.hasNextLine()) {
            if(!scanner.next().equals("e")) return;

            start = Integer.parseInt(scanner.next());
            end = Integer.parseInt(scanner.next());
            cost = Integer.parseInt(scanner.next());

            edgeList.add(new Edge(start, end, cost));
        }
    }
    private void create_Starting_Population() {
        //When starting each solution, it already calculates the cost and makes sure none are invalid solutions;
        for (int j = 0; j < POP_SIZE; j++) {
            population.add(new Solution(k, vertices, edgeList));
        }
    }
    public Solution get_best(ArrayList<Solution> population) {
        Solution best_solution = population.get(0);
        population.forEach(solution -> {
            if(solution.getCost() < best_solution.getCost())
                best_solution.swap_solution(solution);
        });
        return best_solution;
    }
}
