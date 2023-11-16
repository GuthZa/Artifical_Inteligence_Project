import java.io.*;
import java.util.Scanner;

public class main {

    private static final int DEFAULT_RUNS = 10;
    public static void main(String[] args) throws IOException {

        String file_name;
        int runs, custo, melhor_custo = 0;
        double mbf = 0.0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Runs: ");
        runs = scanner.nextInt();
        System.out.println("File Name: ");
        file_name = scanner.next();

        File file = new File(file_name);
        if (!file.exists()) {
            System.out.println("Error opening the file");
            return;
        }
        if (runs <=0) {
            System.out.println("Num of runs needs to be higher than 0");
            return;
        }

        Grafo grafo = new Grafo(file);
        if (grafo.getMatrix()==null) return;

        int i = 0;
        int[] best_solution = new int[grafo.getVertices()];
        for (; i < runs; i++) {
            System.out.println("Initial: ");
            grafo.create_Start_Solution();
            grafo.printSolution(grafo.getSolution());

            custo = grafo.trepa_colinas();
            System.out.println("\nRep: " + i);
            grafo.printSolution(grafo.getSolution());
            System.out.println("Final cost: " + custo);
            mbf += custo;
            if (i == 0 || melhor_custo > custo) {
                melhor_custo = custo;
                best_solution = grafo.getSolution();
            }
            //For better readability
            System.out.println();
        }

        System.out.println("\n\nMBF: " + mbf/i);
        System.out.println("Best solution found: ");
        grafo.printSolution(best_solution);
        System.out.println("Final cost: " + melhor_custo);
    }
}
