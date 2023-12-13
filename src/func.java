import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class func {

    private func() {
    }

    public static int calculate_cost(int[] solution, ArrayList<Edge> edgeList, int vertices) {
        int total;
        int[] copy = new int[vertices];
        /*
        Copy the number of 1s in the origin solution and copy
        The difference will be used to penalize the solution

        TODO CRIAR ESTRATEGIAS PARA PENALIZAR OU REPARAR
        TODO LATER:
        IMPLEMENTAR ESTRATEGIAS
        2 OPERADORES DE RECOMBINACAO E 2 OPE DE MUTACAO
        2 METODOS DE SELECAO

        METODO HIBRIDO
        2 ABORDAGENS HIBRIDAS USANDO OS 2 ALGORITMOS
        */

        do {
            total = 0;
            for (Edge edge : edgeList) {
                if (solution[edge.getStart() - 1] == 1 && solution[edge.getEnd() - 1] == 1) {
                    total += edge.getCost();
                /*
                Make the positions that have valid connections 1
                The idea is that by the end of all edges, you would have a
                copy equal to the original solution
                This would mean that all points in the original had connections and therefore costs
                By analysing which points in the copy are 0 and 1 in the original,
                 you can get those that don't have connections
                Making the solution invalid
                */
                    copy[edge.getStart() - 1] = 1;
                    copy[edge.getEnd() - 1] = 1;
                }
            }
            //If the copy is equal to the original, then the solution is valid
            //Otherwise, one of the points did not have connections to other points in the solution
            if (Arrays.equals(solution, copy)) {
                return total;
            } else { //invalid solution, repair it
//                return 0;
                repair(solution, copy, edgeList, vertices);
            }
        } while (true);
    }

    public static void repair(int[] solution, int[] copy, ArrayList<Edge> edgeList, int vertices) {
        Random random = new Random();
        int num_to_change;
        for (int i = 0; i < vertices; i++) {
            //Find the points where the copy was not evaluated
            if (solution[i] == 1 && copy[i] == 0) {
                //Get a random other point that is at 0
                do {
                    num_to_change = random.nextInt(vertices);
                } while (solution[num_to_change] != 0);
                //Swap them
                solution[i] = 0;
                solution[num_to_change] = 1;
            }
        }
    }
}
