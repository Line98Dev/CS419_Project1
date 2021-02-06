/*
  Task to be scheduled by the scheduling algorithm.

  Each task is represented by

   String name - a task name, not necessarily unique

   int tid - unique task identifier

   int burst - the CPU burst of this this task
 */

import java.util.concurrent.atomic.AtomicInteger;

public class Task
{

    private final String name;

    /* tid represents a unique task id for each task;
       the tid will automatically increment by one for each
       new task.
     */
    private final int tid;

    /* arrivalTime represents the arrival time of a task
     */
    private int arrivalTime;

    /*burst represents the CPU burst duration required by a task;
      Assume every task has a single CPU burst and no I/O wait
     */
    private int burst;

    /**
     * We use an atomic integer to assign each task a unique task id.
     */
    private final static AtomicInteger tidAllocator = new AtomicInteger();

    public Task(String name, int arrivalTime, int burst) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burst = burst;
        this.tid = tidAllocator.getAndIncrement();
    }

    /**
     * Appropriate getters
     */
    public String getName() {
        return name;
    }

    public int getTid() {
        return tid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurst() {
        return burst;
    }

    /**
     * Appropriate setters
     */
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurst(int burst) {
        this.burst = burst;
    }

    /*
     * We override equals() so we can use a
     * Task object in Java collection classes.
     */
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof Task))
            return false;

        /*
         * Otherwise we are dealing with another Task.
         * two tasks are equal if they have the same tid.
         */
        Task rhs = (Task)other;
        return (this.tid == rhs.tid);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", tid=" + tid +
                ", arrivalTime=" + arrivalTime +
                ", burst=" + burst +
                '}';
    }
}
