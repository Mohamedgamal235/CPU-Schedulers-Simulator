package Scheduler;

import java.util.*;

public class PriorityScheduler {
    private List<Process> processes;
    private PriorityQueue<Process> readyQueue;

    public PriorityScheduler(List<Process> processes, int contextSwitchingTime) throws NullPointerException {
        int contextSwitchTime = contextSwitchingTime;
        if (processes == null) {
            throw new NullPointerException("Processes list cannot be null");
        }
        if (contextSwitchingTime < 0) {
            throw new IllegalArgumentException("Context switching time cannot be negative");
        }
        this.processes = processes;
        Collections.sort(processes, Comparator.comparingInt((Process p) -> p.arrivalTime )
                .thenComparingInt(p -> p.priority));
        int currentTime = 0;
        while (!processes.isEmpty() || !readyQueue.isEmpty()){
            for (Process process : processes) {
                if (currentTime >= process.arrivalTime) {
                    readyQueue.add(process);
                }
            }
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();

                // Calculate waiting time, considering context switching
                currentProcess.waitingTime = currentTime - currentProcess.arrivalTime + contextSwitchingTime;
                currentTime += currentProcess.burstTime + contextSwitchTime;
                currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.burstTime;
            } else {
                // If the ready queue is empty, increment time by context switching time
                currentTime += 1;
            }
        }

    }


    public void displayResults() {
        System.out.println("Priority Scheduling:");
        printResults(processes);
    }

    private void printResults(List<Process> processes) {
        // Sort by priority and then arrival time
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("Process\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            System.out.println(process.name + "\t" + process.waitingTime + "\t\t" + process.turnaroundTime);
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }

        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / processes.size());
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / processes.size());
    }
}
