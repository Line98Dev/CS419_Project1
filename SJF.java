/*
 * SJF scheduling algorithm.
 *
 * Add your implementation inside the SJF class below.
 */

import java.util.ArrayList;
import java.util.List;

public class SJF implements Algorithm
{
    private final List<Task> queue;
    private final List<Task> waitingQueue = new ArrayList<>();

    //the total number of processes to be scheduled
    private final int numTasks;

    public SJF(List<Task> queue) {
        this.queue = queue;
        numTasks = queue.size();
    }

    @Override
    public void schedule() {
        System.out.println("Shortest Job First (Non-Preemptive) Scheduling \n");
        Task currentTask;
        int waitingTime = 0;

        while (!waitingQueue.isEmpty() || !queue.isEmpty()) {
            addTasksToWaitingQueue(CPU.getCurrentTime());

            currentTask = pickNextTask();
            if (currentTask != null) {
                int wTime = 0;
                if ((CPU.getCurrentTime() - currentTask.getArrivalTime()) > 0) {
                    wTime = CPU.getCurrentTime() - currentTask.getArrivalTime();
                }
                waitingTime += wTime;
                CPU.run(currentTask, currentTask.getBurst());
                System.out.println(currentTask.getName() + " finished at time "+CPU.getCurrentTime() + ". Its waiting time is: " + wTime);

            }
            waitingQueue.remove(currentTask);
        }
        double averageWaitingTime = waitingTime / (double) numTasks;
        System.out.printf("\nThe average waiting time is: %.2f\n", averageWaitingTime);

    }

    @Override
    public Task pickNextTask() {
        if (waitingQueue.isEmpty()) {
            Task task = queue.get(0);
            queue.remove(task);
            return task;
        }
        else {
            return getShortestTask();
        }
    }

    private void addTasksToWaitingQueue(int time){
        List<Task> found = new ArrayList<>();
        for (Task task : queue) {
            if (task.getArrivalTime() <= time) {
                waitingQueue.add(task);
                found.add(task);
            }
        }
        queue.removeAll(found);
    }

    private Task getShortestTask() {
        Task shortestTask = new Task("Infinite", 0, Integer.MAX_VALUE );
        for (Task task : waitingQueue) {
            if (task.getBurst() < shortestTask.getBurst()) {
                shortestTask = task;
            }
        }
        if (shortestTask.getBurst() != Integer.MAX_VALUE) {
            return shortestTask;
        }
        return null;
    }
}
