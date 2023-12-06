import java.util.ArrayList;

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

        for (Edge edge : edgeList) {
            if (solution[edge.getStart() - 1] == 1 && solution[edge.getEnd() - 1] == 1) {
                total += edge.getCost();
            }
        }

//        for (int i = 0; i < vertices; i++) {
//            if (solution[i] == 1) {
//                for (int j =  i + 1; j < vertices; j++) {
//                    if (solution[j] == 1) {
//                        for (Edge edge : edgeList) {
//                            if (edge.getStart() == (i+1) && edge.getEnd() == (j+1) ||
//                            edge.getEnd() == (i+1) && edge.getStart() == (j+1)) {
//                                total += edge.getCost();
//                            }
//                        }
//                    }
//                }
//            }
//        }

        return total;
    }

}
