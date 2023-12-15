import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Solution {
    private int[] solution;
    private int[] copy; //array that saves the points that were analysed for cost
    private int cost;
    public Solution(int k, int vertices, ArrayList<Edge> edgeList) {
        solution = new int[vertices];
        copy = new int[vertices];
        init_Solution(k, vertices, edgeList);
    }

    public Solution(int vertices, ArrayList<Edge> edgeList, int[] solution) {
        copy = new int[vertices];
        init_Neighbor(vertices, edgeList, solution);
    }

    public void init_Solution(int k, int vertices, ArrayList<Edge> edgeList) {
        Random random = new Random();
        int position;
        do {
            for (int i = 0; i < vertices; i++) {
                //initialize the arrays
                solution[i] = 0;
                copy[i] = 0;
            }
            for (int i = 0; i < k; i++) {
                //Adds points up to the number predefined in the file as k
                do {
                    position = random.nextInt(vertices);
                } while (solution[position] != 0);
                solution[position] = 1;
            }
            //this creates new solutions until they are valid
            this.cost = func.calculate_cost(this, edgeList);
        } while (cost == 0);
    }

    public void init_Neighbor(int vertices, ArrayList<Edge> edgeList, int[] solution) {
        int[] neighbor_Solution = new int[vertices];
        Random random = new Random();
        //copy everything from the solution into the neighbor_solution
        System.arraycopy(solution, 0, neighbor_Solution, 0, vertices);
        int p1, p2;
        // Find positions with value 0
        do {
            p1 = random.nextInt(vertices - 1);
        } while (neighbor_Solution[p1] != 0);
        //find positions with value 1
        do {
            p2 = random.nextInt(vertices - 1);
        } while (neighbor_Solution[p2] != 1);

        //Switch
        neighbor_Solution[p1] = 1;
        neighbor_Solution[p2] = 0;

        this.solution = neighbor_Solution;

        this.cost = func.calculate_cost(this, edgeList);
    }

    private void change_random_point_to_zero() {
        Random random = new Random();
        int num_to_change;
        //Get a random point that is at 0
        do {
            num_to_change = random.nextInt(solution.length);
        } while (solution[num_to_change] != 0);
        solution[num_to_change] = 0;
    }

    private void change_random_point_to_one() {
        Random random = new Random();
        int num_to_change;
        //Get a random point that is at 1
        do {
            num_to_change = random.nextInt(solution.length);
        } while (solution[num_to_change] != 1);
        solution[num_to_change] = 1;
    }

    public void repair_invalid_solution(ArrayList<Edge> edgeList) {
        do {
            for (int i = 0; i < solution.length; i++) {
                if (solution[i] == 1 && copy[i] == 0) {
                    change_random_point_to_zero();
                    solution[i] = 0;
                }
            }
            this.cost = func.calculate_cost(this, edgeList);
        } while (cost == 0);
    }

    public void repair_solution(int k) {
        int count = count_points();
        do {
            //If the number of 1s is smaller than k, add 1s random
            if (count > k)
                change_random_point_to_zero();
            //If the number of 1s is higher than k, remove 1s random
            if (count < k)
                change_random_point_to_one();
            count = count_points();
        } while (count != k);
    }

    public void recombine_solution(int start, int end, Solution solution) {
        for (int i = 0; i < start; i++) {
            this.solution[i] = solution.getSolution()[i];
        }
        for (int i = start; i < end; i++) {
            this.solution[i] = solution.getSolution()[i];
        }
    }

    public void swap_solution(Solution solution) {
        this.solution = solution.getSolution();
        this.cost = solution.getCost();
        this.copy = solution.getCopy();
    }

    public int count_points() {
        int k = 0;
        for (int j : solution)
            if (j == 1) k++;
        return k;
    }

    public int[] getSolution() {
        return solution;
    }

    public void setSolution(int[] solution) {
        this.solution = solution;
    }

    public int[] getCopy() {
        return copy;
    }

    public void setCopy(int[] copy) {
        this.copy = copy;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Solution{" +
                Arrays.toString(solution) +
                ", cost=" + cost +
                '}';
    }
}
