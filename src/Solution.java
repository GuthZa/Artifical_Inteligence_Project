import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Solution {
    private int[] solution;
    private int[] copy; //array that saves the points that were analysed for cost
    private int cost;
    private int num_points;
    private final Random random = new Random();

    public Solution(int k, int vertices, ArrayList<Edge> edgeList) {
        solution = new int[vertices];
        copy = new int[vertices];
        init_Solution(k, vertices, edgeList);
        this.num_points = k;
    }

    //adds an empty solution
    public Solution(int vertices) {
        solution = new int[vertices];
        copy = new int[vertices];
        Arrays.fill(solution, 0);
        Arrays.fill(copy, 0);
        cost = 0;
        this.num_points = 0;
    }

    public void init_Solution(int k, int vertices, ArrayList<Edge> edgeList) {
        int position;
        do {
            Arrays.fill(solution, 0);
            Arrays.fill(copy, 0);
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

    public void init_Neighbor(ArrayList<Edge> edgeList, Solution parent) {
        this.set_solution(parent);
        int p1, p2;
        // Find positions with value 0
        do {
            p1 = random.nextInt(solution.length);
        } while (solution[p1] == 1);
        //find positions with value 1
        do {
            p2 = random.nextInt(solution.length);
        } while (solution[p2] == 0);

        //Switch
        solution[p1] = 1;
        solution[p2] = 0;

        this.cost = func.calculate_cost(this, edgeList);
    }

    private void change_random_point_to_zero() {
        int num_to_change;
        //Get a random point that is at 1
        do {
            num_to_change = random.nextInt(solution.length);
        } while (solution[num_to_change] == 0);
        solution[num_to_change] = 0;
    }

    private void change_random_point_to_one() {
        int num_to_change;
        //Get a random point that is at 0
        do {
            num_to_change = random.nextInt(solution.length);
        } while (solution[num_to_change] == 1);
        solution[num_to_change] = 1;
    }

    public void repair_invalid_solution(ArrayList<Edge> edgeList) {
        this.cost = func.calculate_cost(this, edgeList);
        //Changes 1 point randomly until the cost is != 0
        while (cost == 0) {
            for (int i = 0; i < solution.length; i++) {
                if (solution[i] == 1 && copy[i] == 0) {
                    change_random_point_to_one();
                    solution[i] = 0;
                }
            }
            this.cost = func.calculate_cost(this, edgeList);
        }
    }

    public void repair_solution(int k) {
        count_points();
        //If the number of 1s is higher than k, remove 1s random
        if (k < num_points) //There's more points than k
            for (int i = 0; i < (num_points - k); i++)
                change_random_point_to_zero();
        //If the number of 1s is smaller than k, add 1s random
        if (k > num_points) //There's fewer points than k
            for (int i = 0; i < (k - num_points); i++)
                change_random_point_to_one();
    }

    public void recombine_solution(int start, int end, Solution solution) {
        for (int i = 0; i < start; i++) {
            this.solution[i] = solution.getSolution()[i];
        }
        for (int i = start; i < end; i++) {
            this.solution[i] = solution.getSolution()[i];
        }
    }

    private int count_points() {
        num_points = 0;
        for (int j : solution)
            if (j == 1)
                num_points++;
        return num_points;
    }

    public int getNum_points() {
        return num_points;
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
        this.cost = solution.getCost();
        this.num_points = solution.count_points();
    }

    @Override
    public String toString() {
        return "Solution{" +
                Arrays.toString(solution) +
                ", cost=" + cost +
                '}';
    }
}
