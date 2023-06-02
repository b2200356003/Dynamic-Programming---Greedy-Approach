import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        this.calculateCompatibility();
        maxWeight = new Double[taskArray.length];
        this.calculateMaxWeight(taskArray.length-1);

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */

    public int binarySearch(int index) {
        int low = 0;
        int high = index-1;
        int x = Task.calculateMinutes(taskArray[index].getStartTime());

        while ((high-low)>1){
            int mid = (high+low)/2;
            if (Task.calculateMinutes(taskArray[mid].getFinishTime())>x){
                high = mid - 1;
            }
            else {
                low = mid ;
            }
        }
        if (high==-1){
            return -1;
        }
        if (Task.calculateMinutes(taskArray[high].getFinishTime())<=x){
            return high;
        }
        else if (Task.calculateMinutes(taskArray[low].getFinishTime())<=x){
            return low;
        }
        return -1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        for (int i = 0; i < this.compatibility.length; i++) {
            this.compatibility[i]=this.binarySearch(i);
        }
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        System.out.println();
        System.out.println("Calculating the dynamic solution");
        System.out.println("--------------------------------");
        solveDynamic(this.taskArray.length-1);

        System.out.println();
        System.out.println("Dynamic Schedule");
        System.out.println("----------------");

        for (int i = 0; i < this.planDynamic.size(); i++) {
            System.out.println("At " + this.planDynamic.get(i).getStartTime() +", " +this.planDynamic.get(i).getName()+".");
        }

        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {

        System.out.println("Called solveDynamic(" + i + ")");

        if ((i!=0 && Objects.equals(this.maxWeight[i], this.maxWeight[i - 1]))){
            solveDynamic(i-1);
        }

        else {
            if (this.compatibility[i]!=-1) {
                solveDynamic(this.compatibility[i]);
            }
            this.planDynamic.add(this.taskArray[i]);
        }

    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */

    public Double calculateMaxWeight(int i) {
        System.out.println("Called calculateMaxWeight(" + i + ")");

        double a;
        double b;

        if (i == -1){
            return 0.0;
        }

        if (this.maxWeight[i]!=null && i!=0){
            return this.maxWeight[i];
        }

        else {
            b = (calculateMaxWeight(this.compatibility[i]) + taskArray[i].getWeight());
            a = calculateMaxWeight(i - 1);
            this.maxWeight[i] = a > b ? a : b;
        }


        return this.maxWeight[i];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        planGreedy.add(this.taskArray[0]); // select first task

        for (int i = 1; i < this.taskArray.length; i++) {
            // check if it is compatible or not with the last element of planGreedy
            if (Task.calculateMinutes(this.taskArray[i].getStartTime()) >= Task.calculateMinutes(planGreedy.get(planGreedy.size()-1).getFinishTime())) {
                planGreedy.add(this.taskArray[i]);
            }
        }

        System.out.println("Greedy Schedule");
        System.out.println("---------------");

        for (int i = 0; i < planGreedy.size(); i++) {
            System.out.println("At " + planGreedy.get(i).getStartTime() + ", " + planGreedy.get(i).getName() + ".");
        }

        return planGreedy;
    }
}
