/*
 * FCFS scheduling algorithm.
 */
 
import java.util.*;

public class FCFS implements Algorithm
{
    private final List<Task> queue;

    //the total number of processes to be scheduled
    private final int numTasks;

    public FCFS(List<Task> queue) {
        this.queue = queue;
        numTasks = queue.size();
    }

    public void schedule() {
        System.out.println("First-Come, First-Served Scheduling \n");

        //to keep track of the total waiting time
        int totalWaitingTime = 0;

        Task currentTask;

        while (!queue.isEmpty()) {
            currentTask = pickNextTask();

            //the waiting time for a process in FCFS is the time it is given the CPU minus its arrival time
            int wTime = 0;
            if (CPU.getCurrentTime() > currentTask.getArrivalTime()){
                wTime = CPU.getCurrentTime() - currentTask.getArrivalTime();
            }
            totalWaitingTime += wTime;

            CPU.run(currentTask, currentTask.getBurst());

            System.out.println(currentTask.getName() + " finished at time "+CPU.getCurrentTime() + ". Its waiting time is: " + wTime);

            // remove the completed process
            queue.remove(currentTask);
        }

        //need to cast either the numerator or the denominator to the double type
        //otherwise, when both are integers, their division result will always be rounded to integer
        double averageWaitingTime = totalWaitingTime / (double) numTasks;
        //use printf for formatted out (only show two digits after decimal points)
        System.out.printf("\nThe average waiting time is: %.2f\n", averageWaitingTime);
    }

    //select the next process to be executed by the CPU.
    public Task pickNextTask() {
        return queue.get(0);
    }
}
