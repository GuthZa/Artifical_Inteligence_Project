import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Solution {
    private int[] solution;
    private int[] copy; //array that saves the points that were analysed for cost
    private int cost;
    private Random random = new Random();
    public Solution(int k, int vertices, ArrayList<Edge> edgeList) {
        solution = new int[vertices];
        copy = new int[vertices];
        init_Solution(k, vertices, edgeList);
    }

    //adds an empty solution, for parents
    public Solution(int vertices) {
        solution = new int[vertices];
        copy = new int[vertices];
        Arrays.fill(solution, 0);
        Arrays.fill(copy, 0);
        cost = 0;
    }

    public void init_Solution(int k, int vertices, ArrayList<Edge> edgeList) {
        int position;
        do {
            Arrays.fill(solution,0);
            Arrays.fill(copy,0);
            for (int i = 0; i < k; i++) {
                //Adds points up to the number predefined in the file as k
                do {
                    position = random.nextInt(vertices);
                } while (solution[position] == 1);
                solution[position] = 1;
            }
            this.cost = func.calculate_cost(this, edgeList);
            //this creates new solutions until they are valid
        } while (cost == 0);
    }

    public void init_Neighbor(int vertices, ArrayList<Edge> edgeList, int[] solution) {
        int p1, p2;
        // Find positions with value 0
        do {
            p1 = random.nextInt(vertices);
        } while (solution[p1] == 1);
        //find positions with value 1
        do {
            p2 = random.nextInt(vertices);
        } while (solution[p2] == 0);

        //Switch
        solution[p1] = 1;
        solution[p2] = 0;

        this.cost = func.calculate_cost(this, edgeList);
    }

    private void change_random_point_to_zero() {
        int num_to_change;
        //Get a random point that is at 0
        do {
            num_to_change = random.nextInt(solution.length);
        } while (solution[num_to_change] == 0);
        solution[num_to_change] = 0;
    }

    private void change_random_point_to_one() {
        int num_to_change;
        //Get a random point that is at 1
        do {
            num_to_change = random.nextInt(solution.length);
        } while (solution[num_to_change] == 1);
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
        while (count != k) {
            //If the number of 1s is smaller than k, add 1s random
            if (count > k)
                for (int i = 0; i < (count - k); i++)
                    change_random_point_to_zero();
            //If the number of 1s is higher than k, remove 1s random
            if (count < k)
                for (int i = 0; i < (k - count); i++)
                    change_random_point_to_one();
            count = count_points();
        }
    }

    public void recombine_solution(int start, int end, Solution solution) {
        for (int i = 0; i < start; i++) {
            this.solution[i] = solution.getSolution()[i];
        }
        for (int i = start; i < end; i++) {
            this.solution[i] = solution.getSolution()[i];
        }
    }

    public int count_points() {
        int k = 0;
        for (int j : solution)
            if (j == 1)
                k++;
        return k;
    }

    public int[] getSolution() {
        return solution;
    }

    public int[] getCopy() {
        return copy;
    }

    public void resetCopy() {
        Arrays.fill(copy, 0);
        cost = 0;
    }

    public int getCost() {
        return cost;
    }

    //Setters
    public void set_solution(Solution solution) {
        System.arraycopy(solution.getSolution(), 0, this.solution, 0, solution.getSolution().length);
        System.arraycopy(solution.getCopy(), 0, this.copy, 0, solution.getSolution().length);
        this.copy = solution.getCopy();
    }

    @Override
    public String toString() {
        return "Solution{" +
                Arrays.toString(solution) +
                ", cost=" + cost +
                '}';
    }
}
