import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {

    private ArrayList<Edge> edgeList;

    private static final int NUM_ITE = 1000;
    private static final int POP_SIZE = 100; //Must keep even
    private static final float MUTATION_CHANCE = 0.01F;
    private static final float COMBINE_CHANCE = 0.3F;
    private static final int TOURNAMENT_SIZE = 4; //Must keep even
    private static final int NUM_GEN = 2500;

    //edge, k
    private int vertices, k;
    private ArrayList<Solution> population;

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

        //initialize the best and local global with empty solutions
        best_global = new Solution(vertices);
        best_local = new Solution(vertices);

        System.out.println("Initial: ");
        int i = 0;
        for (; i < runs; i++) {
            //Initializes the parents
            ArrayList<Solution> parents = new ArrayList<>();
            for (int j = 0; j < TOURNAMENT_SIZE; j++) {
                parents.add(new Solution(vertices));
            }
            create_Starting_Population();

            for (int j = 0; j < NUM_GEN; j++) {
                //Generates the parents to use for genetic modifications
                if (Math.random() < 0.5)
                    func.tournament(population, parents);
                else
                    func.bigger_tournament(population, parents, TOURNAMENT_SIZE);

                //Genetic operator, each with its own chance
                func.genetic_operators(parents, population, COMBINE_CHANCE, MUTATION_CHANCE);

                //Check if the solutions are valid
                //They MUST have k number of 1s
                //They MUST NOT have cost = 0
                func.repair(population, k, edgeList);
                best_local.set_solution(get_best(population));
            }

            //Applies hill climbing to the solution, trying to find a smaller cost solution
            //The neighbor checks the cost
            //The hill climbing discards any solution that has cost == 0
//           func.hill_climbing(NUM_ITE, population, edgeList);

            System.out.println("Rep: " + (i+1));
            //Gets the best solution of the population
            best_local = get_best(population);
            System.out.println(best_local);
            mbf += best_local.getCost();
            //Saves the best cost of the whole run
            if (i == 0 || best_cost < best_local.getCost())
                best_global.set_solution(best_local);
            //For better readability
            System.out.println();
        }

        System.out.println("MBF: " + mbf/i);
        System.out.println("Best solution found: ");
        System.out.println(best_global);
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
        population = new ArrayList<>();
        //When starting each solution, it already calculates the cost and makes sure none are invalid solutions;
        for (int j = 0; j < POP_SIZE; j++) {
            population.add(new Solution(k, vertices, edgeList));
        }
    }
    public Solution get_best(ArrayList<Solution> population) {
        Solution best_solution = population.get(0);
        population.forEach(solution -> {
            if(solution.getCost() < best_solution.getCost())
                best_solution.set_solution(solution);
        });
        return best_solution;
    }
}
