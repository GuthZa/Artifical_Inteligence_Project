import java.util.ArrayList;
import java.util.Arrays;

public class func {

    private func() {
    }

    public static int calculate_cost(int[] solution, ArrayList<Edge> edgeList, int vertices) {
        int total = 0, penalization = 1;
        int count_origin = 0, count_copy = 0;
        //Copy the number of 1s in the origin solution and copy
        //The difference will be used to penalize the solution

        //TODO CRIAR ESTRATEGIAS PARA PENALIZAR OU REPARAR
        //TODO LATER:
        //IMPLEMENTAR ESTRATEGIAS
        //2 OPERADORES DE RECOMBINACAO E 2 OPE DE MUTACAO
        //2 METODOS DE SELECAO

        //METODO HIBRIDO
        //2 ABORDAGENS HIBRIDAS USANDO OS 2 ALGORITMOS

        int[] copy = new int[vertices];

        for (Edge edge : edgeList) {
            if (solution[edge.getStart() - 1] == 1 && solution[edge.getEnd() - 1] == 1) {
                total += edge.getCost();
                //Make the positions that have valid connections 1
                //The idea is that by the end of all edges, you would have a
                //copy equal to the original solution
                //This would mean that all points in the original had connections and therefore costs
                //By analysing which points in the copy are 0 and 1 in the original,
                // you can get those that don't have connections
                //Making the solution invalid
                copy[edge.getStart() - 1] = 1;
                copy[edge.getEnd() - 1] = 1;
            }
        }

        for (int i = 0; i < vertices; i++) {
            if (solution[i] == 1) count_origin++;
            if (copy[i] == 1) count_copy++;
        }
        //If the copy is equal to the original, then the solution is valid
        //Otherwise, one of the points did not have connections to other points in the solution
        if (!Arrays.equals(copy, solution)) {
            //Invalid Solution
//            return 0;
            //The penalization for an invalid is total cost += 1
            penalization = 1 + (count_copy / count_origin);
        }



        //para reparar tinha de trocar pontos aleatoriamente ate a solucao ser valida
        return total * penalization;
    }

    private static void repair(int[] solution, ArrayList<Edge> edgeList, int vertices, int cost) {
        //Escoolher tantos pontos na solucao que estejam a 1 e na copia a 0
        //trocar na solucao para 0
        //edge.start == 1 && edge.end == 0 || edge.start == 0 && edge.end == 1

        //posso precorrer a array ou gerar valores aleatoreos com edgeList.get(random)
    }
}
