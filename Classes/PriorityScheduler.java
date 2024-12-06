package scheduling;

import java.util.*;

public class PriorityScheduler {
    private List<Process> processes;
    private PriorityQueue<Process> readyQueue = new PriorityQueue<>();
    private List<Process> excutedprocesses = new ArrayList<>();
    private int currentTime = 0;

    int contextSwitchingTime;
    public PriorityScheduler(List<Process> processes, int contextSwitchingTime) {
        this.processes = processes;
        this.contextSwitchingTime = contextSwitchingTime;
    }

    public void Scheduler() {

        // Sort by priority and then arrival time

        Collections.sort(processes, Comparator.comparingInt((Process p) -> p.getArrivalTime())
                .thenComparingInt(p -> p.getPriority()));

        currentTime = 0;
        while (!processes.isEmpty() || !readyQueue.isEmpty()){
            Iterator<Process> iterator = processes.iterator();
            while (iterator.hasNext()) {
                Process process = iterator.next();
                if (currentTime >= process.getArrivalTime()) {
                    readyQueue.add(process);
                    iterator.remove();
                }
            }
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();


                currentTime += currentProcess.getBurstTime() + contextSwitchingTime;
                currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
                excutedprocesses.add(currentProcess);
                displayResults(currentProcess);
            } else {
                currentTime += 1;
            }

        }
    }

    public void displayResults(Process currProcess) {
        System.out.println("| Time -> " + currentTime + " | Process -> " + currProcess.getName() + " | Brust -> " + currProcess.getRemainingBurstTime() + " | Arrival -> " + currProcess.getArrivalTime() + " | Priority -> " + currProcess.getPriority());
        System.out.println("-----------------------------------");
    }

    public void printResults() {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("Process\tWaiting Time\tTurnaround Time");
        for (Process p : excutedprocesses) {
            System.out.println(p.getName() + "\t" + p.getWaitingTime() + "\t\t" + p.getTurnaroundTime());
            totalWaitingTime += p.getWaitingTime();
            totalTurnaroundTime += p.getTurnaroundTime();
        }

        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / excutedprocesses.size());
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / excutedprocesses.size());
    }
}