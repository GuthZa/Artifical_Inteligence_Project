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
        */

        //Makes the copy all 0s again
        solution.resetCopy();

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

    public static void repair(ArrayList<Solution> population, int k, ArrayList<Edge> edgeList) {
        population.forEach(solution -> {
            solution.repair_solution(k);
            solution.repair_invalid_solution(edgeList);
        });
    }

    //Tournament size 2
    public static void tournament(ArrayList<Solution> population, ArrayList<Solution> parents) {

        int x1, x2;

        for (Solution parent : parents) {
            x1 = random.nextInt(population.size());
            do {
                x2 = random.nextInt(population.size());
            } while (x1 == x2);

            if (population.get(x1).getCost() < population.get(x2).getCost())
                parent.set_solution(population.get(x1));
            else
                parent.set_solution(population.get(x2));
        }
    }

    //Tournament variable size
    public static void bigger_tournament(ArrayList<Solution> population, ArrayList<Solution> parents , int tournament_size) {

        int new_x, counter = 0,  aux;
        int[] x = new int[tournament_size];
        int this_cost, best_cost;
        boolean to_discard;

        for (Solution parent : parents) {
            aux = random.nextInt(population.size());
            best_cost = population.get(aux).getCost();

            while (counter < tournament_size) {
                to_discard = false;
                new_x = random.nextInt(population.size());

                for (int j = 0; j <= counter; j++) {
                    if (x[j] == new_x) {
                        to_discard = true;
                        break;
                    }
                }
                if (!to_discard) {
                    x[counter] = new_x;
                    this_cost = population.get(new_x).getCost();

                    if (this_cost < best_cost) {
                        parent.set_solution(population.get(new_x));
                        best_cost = this_cost;
                    }
                    counter++;
                }
            }
        }
    }

    public static void genetic_operators(ArrayList<Solution> parents, ArrayList<Solution> offspring, float combine_chance, float mutation_chance) {

        //Combination algorithms
        one_point_split(parents, offspring, combine_chance);
        two_point_split(parents, offspring, combine_chance);

        //Mutation algorithms
        uniform_recombine(parents, offspring, mutation_chance);
        mutation(offspring, mutation_chance);

    }
    
    //One point separation
    public static void one_point_split(ArrayList<Solution> parents, ArrayList<Solution> offspring, float chance) {
        for (int i = 0; i < parents.size(); i+=2) {
            if (random.nextFloat() < chance) {
                int point = random.nextInt(offspring.get(0).getSolution().length);

                //From the beginning until the point
                offspring.get(i).recombine_solution(0, point, parents.get(i));
                offspring.get(i + 1).recombine_solution(0, point, parents.get(i + 1));

                //From the point until the end
                offspring.get(i).recombine_solution(point, parents.get(i).getSolution().length, offspring.get(i + 1));
                offspring.get(i + 1).recombine_solution(point, parents.get(i).getSolution().length, offspring.get(i));
            }
            else {
                offspring.get(i).set_solution(parents.get(i));
                offspring.get(i + 1).set_solution(parents.get(i + 1));
            }
        }
    }
    
    public static void two_point_split(ArrayList<Solution> parents, ArrayList<Solution> population, float chance) {
        for (int i = 0; i < parents.size(); i+=2) {
            if (random.nextFloat() < chance) {
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
                population.get(i).set_solution(parents.get(i));
                population.get(i + 1).set_solution(parents.get(i + 1));

                //From the beginning until the point
                population.get(i).recombine_solution(point_one, point_two, parents.get(i + 1));
                population.get(i + 1).recombine_solution(point_one, point_two, parents.get(i));

            }
            else {
                population.get(i).set_solution(parents.get(i));
                population.get(i + 1).set_solution(parents.get(i + 1));
            }
        }
    }
    
    public static void uniform_recombine(ArrayList<Solution> parents, ArrayList<Solution> population, float chance) {
        for (int i = 0; i < parents.size(); i+=2) {
            //Initialize the offspring
            population.get(i).set_solution(parents.get(i));
            population.get(i + 1).set_solution(parents.get(i + 1));

            for (int j = 0; j < population.get(i).getSolution().length; j++) {
                //There's a chance to swap the positions of the
                if(random.nextFloat() < chance) {
                    population.get(i).getSolution()[j] = parents.get(i + 1).getSolution()[j];
                    population.get(i + 1).getSolution()[j] = parents.get(i).getSolution()[j];
                }
            }
        }
    }

    public static void mutation(ArrayList<Solution> population, float mutation_chance) {
        population.forEach(solution -> {
            for (int i = 0; i < solution.getSolution().length; i++) {
                if (random.nextFloat() < mutation_chance) {
                    if (solution.getSolution()[i] == 0)
                        solution.getSolution()[i] = 1;
                    else
                        solution.getSolution()[i] = 0;
                }
            }
        });
    }

    public static void hill_climbing(int NUM_ITE, ArrayList<Solution> population, ArrayList<Edge> edgeList) {
        for (int i = 0; i < NUM_ITE; i++) {
            population.forEach(solution -> {
                //Create the neighbor
                Solution neighbor_Solution = new Solution(population.get(0).getSolution().length);
                neighbor_Solution.init_Neighbor(edgeList, solution);

                //If the neighbor cost is lower than the initial cost, swap them (Minimization problem)
                if (neighbor_Solution.getCost() != 0 && neighbor_Solution.getCost() < solution.getCost())
                    solution.set_solution(neighbor_Solution);

                //Generate a second neighbor
                //Create the neighbor
                neighbor_Solution.init_Neighbor(edgeList, solution);

                //If the neighbor cost is lower than the initial cost, swap them (Minimization problem)
                if (neighbor_Solution.getCost() != 0 && neighbor_Solution.getCost() < solution.getCost())
                    solution.set_solution(neighbor_Solution);
            });
        }
    }
}