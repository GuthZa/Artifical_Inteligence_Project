import java.util.ArrayList;
import java.util.Arrays;

public class func {

    private func() {
    }

    public static int calculate_cost(int[] solution, ArrayList<Edge> edgeList, int vertices) {
        int total = 0;

        //TODO
        //uma solucao invalida implica que existe pelo menos um ponto sem ligacoes
        //esse ponto vai ter custo 0
        //para deixar de ter custo 0 deve ser realocado
        //-> trocar val 0 e outro ponto a 1

        //TODO FAZER TESTES E OBTER VALORES COM O CODIGO NO MOMENTO
        //TODO CRIAR ESTRATEGIAS PARA PENALIZAR OU REPARAR
        //TODO LATER:
        //IMPLEMENTAR ESTRATEGIAS
        //2 OPERADORES DE RECOMBINACAO E 2 OPE DE MUTACAO
        //2 METODOS DE SELECAO

        //METODO HIBRIDO
        //2 ABORDAGENS HIBRIDAS USANDO OS 2 ALGORITMOS




        /*NOTAS:
            - Como saber que um dos pontos nao tem vertices na solucao?
             - ex: [110011] - o ponto 6 so tem ligacao com o 4, mas o 4 nao e ponto 1 na solucao
             - logo isto gera uma solucao invalida

         */

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
        //If the copy is equal to the original, then the solution is valid
        //Otherwise, one of the points did not have connections to other points in the solution
        if (!Arrays.equals(copy, solution)) {
            //Invalid Solution
            return 0;
        }

        return total;
    }

}
