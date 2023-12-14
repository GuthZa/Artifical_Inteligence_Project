import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class func {

    static Random random = new Random();
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

    public static void repair(ArrayList<Solution> population, ArrayList<Edge> edgeList , int vertices, float repair_chance) {
        //Repair only invalid solutions
        population.stream().
                filter(solution -> !Arrays.equals(solution.getSolution(), solution.getCopy())).
                forEach(solution -> {
                    if (random.nextFloat() < repair_chance)
                        solution.repair(vertices, edgeList);
                });
    }

    public static ArrayList<Solution> tournament(ArrayList<Solution> population) {

        ArrayList<Solution> parents = new ArrayList<>();

        //gets the best 2 solutions to be parents
        Solution best = population.get(0), second = population.get(1);
        //Iterate the population
        population.forEach(solution -> {
            //get the best solution
            if (solution.getCost() < best.getCost()) {
                second.swap_solution(best);
                best.swap_solution(solution);
            }
            //gets the second-best solution
            else if (solution.getCost() < second.getCost() && solution != best)
                second.swap_solution(solution);
        });

        parents.add(best);
        parents.add(second);

        return parents;
    }

    public static ArrayList<Solution> genetic_operators(ArrayList<Solution> parents, ArrayList<Solution> population, float combine_chance, float mutation_chance) {

        crossover(parents, population, combine_chance);



        if (Math.random() < 0.5)
            recombine(parents, population, combine_chance);
        else
            uniform_recombine(parents, population, combine_chance);

        //TODO depois dos genetic operators, as solucoes podem ter mais ou menos pontos do que os que devia

        return population;
    }
    
    public static void crossover(ArrayList<Solution> parents, ArrayList<Solution> population, float combine_chance) {
        for (int i = 0; i < population.size(); i+=2) {
            if (random.nextFloat() < combine_chance) {
                int point = random.nextInt(population.get(0).getSolution().length - 1);

                //From the beginning until the point
                population.get(i).recombine_solution(0, point, parents.get(i));
                population.get(i + 1).recombine_solution(0, point, parents.get(i + 1));

                //From the point until the end
                population.get(i).recombine_solution(point, parents.get(i).getSolution().length, population.get(i + 1));
                population.get(i + 1).recombine_solution(point, parents.get(i).getSolution().length, population.get(i));
            }
            else {
                population.get(i).swap_solution(parents.get(i));
                population.get(i + 1).swap_solution(parents.get(i + 1));
            }
        }
    }

    public static void recombine(ArrayList<Solution> parents, ArrayList<Solution> population, float combine_chance) {
        for (int i = 0; i < population.size(); i+=2) {
            if (random.nextFloat() < combine_chance) {
                int point_two, point_one;
                //Initialize the points
                point_one = random.nextInt(population.get(0).getSolution().length);
                do {
                    point_two = random.nextInt(population.get(0).getSolution().length);
                } while (point_two == point_one);

                //Guarantees the point one is smaller than the point twp
                if (point_one > point_two) {
                    int dummy = point_one;
                    point_one = point_two;
                    point_two = dummy;
                }

                //Initialize the offspring
                population.get(i).swap_solution(parents.get(i));
                population.get(i + 1).swap_solution(parents.get(i + 1));

                //From the beginning until the point
                population.get(i).recombine_solution(point_one, point_two, parents.get(i + 1));
                population.get(i + 1).recombine_solution(point_one, point_two, parents.get(i));

            }
            else {
                population.get(i).swap_solution(parents.get(i));
                population.get(i + 1).swap_solution(parents.get(i + 1));
            }
        }
    }
    
    public static void uniform_recombine(ArrayList<Solution> parents, ArrayList<Solution> population, float combine_chance) {
        for (int i = 0; i < population.size(); i+=2) {
            //Initialize the offspring
            population.get(i).swap_solution(parents.get(i));
            population.get(i + 1).swap_solution(parents.get(i + 1));

            for (int j = 0; j < population.get(i).getSolution().length; j++) {
                //There's a chance to swap the positions of the
                if(random.nextFloat() < combine_chance) {
                    population.get(i).getSolution()[j] = parents.get(i + 1).getSolution()[j];
                    population.get(i + 1).getSolution()[j] = parents.get(i).getSolution()[j];
                }
            }
        }
    }

    public static void mutation(ArrayList<Solution> parents, ArrayList<Solution> population, float mutation_chance) {

    }
}
