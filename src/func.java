import java.util.ArrayList;
import java.util.Arrays;

public class func {

    private func() {
    }

    public static int calculate_cost(Solution solution, ArrayList<Edge> edgeList) {
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
        int cost = 0;
        for (Edge edge : edgeList) {
            if (solution.getSolution()[edge.getStart() - 1] == 1 && solution.getSolution()[edge.getEnd() - 1] == 1) {
                //adds the existing cost to the cost of the connection
                cost += edge.getCost();
                /*
                Make the positions that have valid connections 1
                The idea is that by the end of all edges, you would have a
                copy equal to the original solution
                This would mean that all points in the original had connections and therefore costs
                By analysing which points in the copy are 0 and 1 in the original,
                 you can get those that don't have connections
                Making the solution invalid
                */
                solution.getCopy()[edge.getStart() - 1] = 1;
                solution.getCopy()[edge.getEnd() - 1] = 1;
            }
        }
        //If the copy is equal to the original, then the solution is valid
        //Otherwise, one of the points did not have connections to other points in the solution
        return Arrays.equals(solution.getSolution(), solution.getCopy()) ? cost : 0;
    }

    public static void repair(ArrayList<Solution> population, ArrayList<Edge> edgeList , int vertices) {
        population.stream().
                filter(solution -> !Arrays.equals(solution.getSolution(), solution.getCopy())).
                forEach(solution -> solution.repair(vertices, edgeList));
    }
}
