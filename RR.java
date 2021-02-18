/*
 *
 * Round-robin scheduling algorithm.
 *
 * Add your implementation inside the RR class below.
 *
 */
 
import java.util.*;

import static java.lang.Integer.min;

public class RR implements Algorithm
{
    int quantumTime = 10;
    private final List<Task> queue;
    private final int numTasks;
    private final int[] waitTimes;
    private int[] lastRunFinished;

    public RR(List<Task> queue) {
        this.queue = queue;
        numTasks = queue.size();
        waitTimes = new int[queue.size()];
        lastRunFinished = createLastRunArray(queue);
    }

    private int[] createLastRunArray(List<Task> queue) {
        lastRunFinished = new int[queue.size()];
        for (Task task : queue){
            lastRunFinished[task.getTid()] = task.getArrivalTime();
        }
        return lastRunFinished;
    }

    @Override
    public void schedule() {
        System.out.println("Round Robin Scheduling \n");

        Task currentTask;

        while (!queue.isEmpty()) {
            currentTask = pickNextTask();

            waitTimes[currentTask.getTid()] += (CPU.getCurrentTime() - lastRunFinished[currentTask.getTid()]);
            int runTime = min(currentTask.getBurst(), quantumTime);
            CPU.run(currentTask, runTime);
            lastRunFinished[currentTask.getTid()] = CPU.getCurrentTime();
            currentTask.setBurst(currentTask.getBurst() - runTime);
            currentTask.setArrivalTime(CPU.getCurrentTime());
            queue.remove(currentTask);

            if (currentTask.getBurst() < 1) {
                System.out.println(currentTask.getName() + " finished at time "+CPU.getCurrentTime() + ". Its waiting time is: " + waitTimes[currentTask.getTid()]);
            } else {
                insertIntoQueue(currentTask);
            }
        }
        int totalWaitingTime = calculateTotalWaitTime();
        double averageWaitingTime = totalWaitingTime / (double) numTasks;

        System.out.printf("\nThe average waiting time is: %.2f\n", averageWaitingTime);
    }

    private int calculateTotalWaitTime() {
        int total = 0;
        for (int waitTime : waitTimes) {
            total += waitTime;
        }
        return total;
    }

    private void insertIntoQueue(Task addedTask) {
        for (int index = 0; index < queue.size(); index++) {
            Task task = queue.get(index);
            if (task.getArrivalTime() >= addedTask.getArrivalTime()) {
                queue.add(index, addedTask);
                return;
            }
        }
        queue.add(addedTask);

    }

    @Override
    public Task pickNextTask() {
        return queue.get(0);
    }
}
