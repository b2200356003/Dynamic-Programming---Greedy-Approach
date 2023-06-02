import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    public Task(String name,String start,int duration,int importance,boolean urgent){
        this.name=name;
        this.start=start;
        this.duration=duration;
        this.importance=importance;
        this.urgent=urgent;
    }

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        String finishTime = "";
        String[] arr = new String[5];
        arr[2]=":";
        int total = this.calculateTotal();

        int hour = total / 60;
        int minute = total % 60;

        arr[0]=Integer.toString(hour/10);
        arr[1]=Integer.toString(hour%10);
        arr[3]=Integer.toString(minute/10);
        arr[4]=Integer.toString(minute%10);

        for (String i : arr) {
            finishTime+=i;
        }

        return finishTime;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        int urgentValue;
        if (this.urgent) {urgentValue=2000;}
        else {urgentValue=1;}

        double weight = ((this.importance * urgentValue) / this.duration) ;

        return weight;

    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */

    @Override
    public int compareTo(Object o) {
        Task o1 = (Task) o;
        if (this.calculateTotal()>o1.calculateTotal()){return 1;}
        else if (this.calculateTotal()==o1.calculateTotal()){return 0;}
        return -1;
    }

    public int calculateTotal(){
        String[] arr = null;
        arr = this.start.split("");
        int total = ((Integer.valueOf(arr[0]) *600 + Integer.valueOf(arr[1])*60) + (Integer.valueOf(arr[3])*10 + Integer.valueOf(arr[4])) + (Integer.valueOf(this.duration)*60));
        return total;
    }

    public static int calculateMinutes(String time){
        String[] arr = time.split("");
        int total = ((Integer.valueOf(arr[0]) *600 + Integer.valueOf(arr[1])*60) + (Integer.valueOf(arr[3])*10 + Integer.valueOf(arr[4])));
        return total;
    }
}
