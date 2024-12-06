package scheduling;

import java.util.*;

public class SRTFScheduler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Name for process " + (i + 1) + ": ");
            String processName = scanner.next();
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(processName, burstTime, arrivalTime, 0, 0);
        }

        System.out.print("Enter context switching time: ");
        int contextSwitchingTime = scanner.nextInt();
        System.out.print("Enter aging factor (maximum waiting time before preempting the process): ");
        int agingFactor = scanner.nextInt();

        // Sort processes by arrival time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.getArrivalTime()));

        int currentTime = 0;
        List<Process> completedProcesses = new ArrayList<>();
        Process lastSelectedProcess = null;
        List<String> executionOrder = new ArrayList<>(); // For execution order

        while (completedProcesses.size() < n) {
            List<Process> readyQueue = new ArrayList<>();
            for (Process p : processes) {
                if (!completedProcesses.contains(p) && p.getArrivalTime() <= currentTime) {
                    readyQueue.add(p);
                }
            }

            // all processes exceeding the aging factor
            List<Process> agingProcesses = new ArrayList<>();
            for (Process p : readyQueue) {
                if (p.getWaitingTime() >= agingFactor) {
                    agingProcesses.add(p);
                }
            }

            Process selectedProcess = null;

            if (!agingProcesses.isEmpty()) {
                // Sort aging processes by remaining burst time (if multiple processes exceed the aging factor)
                agingProcesses.sort(Comparator.comparingInt(Process::getRemainingBurstTime)
                        .thenComparingInt(Process::getArrivalTime));
                selectedProcess = agingProcesses.get(0);
            } else if (!readyQueue.isEmpty()) {
                // If no processes exceed the aging factor, use SRTF
                readyQueue.sort(Comparator.comparingInt(Process::getRemainingBurstTime));
                selectedProcess = readyQueue.get(0);
            }

            if (selectedProcess == null) {
                // If no process is ready-> increment waiting times of all processes in the readyQueue
                for (Process p : readyQueue) {
                    p.setWaitingTime(p.getWaitingTime() + 1);
                }
                currentTime++;
                continue;
            }

            if (lastSelectedProcess != null && lastSelectedProcess != selectedProcess) {
                // Add context-switching time to the current time and update waiting times
                for (int i = 0; i < contextSwitchingTime; i++) {
                    currentTime++;
                    for (Process p : readyQueue) {
                        if (p != selectedProcess) {
                            p.setWaitingTime(p.getWaitingTime() + 1);
                        }
                    }
                }
            }

            selectedProcess.setRemainingBurstTime(selectedProcess.getRemainingBurstTime() - 1);
            currentTime++;

            // Add selected process to execution order
            executionOrder.add(selectedProcess.getName());

            // If the process completes
            if (selectedProcess.getRemainingBurstTime() == 0) {
                selectedProcess.setCompletionTime(currentTime);
                selectedProcess.setTurnaroundTime(selectedProcess.getCompletionTime() - selectedProcess.getArrivalTime());
                selectedProcess.setWaitingTime(selectedProcess.getTurnaroundTime() - selectedProcess.getBurstTime());
                selectedProcess.setCompleted(true);
                completedProcesses.add(selectedProcess);
            }

            // Update waiting time for all processes in the ready queue
            for (Process p : readyQueue) {
                if (p != selectedProcess) {
                    p.setWaitingTime(p.getWaitingTime() + 1);
                }
            }

            // Update the last selected process
            lastSelectedProcess = selectedProcess;
        }

       
        System.out.println("\nProcess\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        double totalTurnaround = 0, totalWaiting = 0;
        for (Process p : processes) {
            System.out.println(p.getName() + "\t\t" + p.getArrivalTime() + "\t\t" + p.getBurstTime() + "\t\t" +
                    p.getCompletionTime() + "\t\t\t" + p.getTurnaroundTime() + "\t\t\t" + p.getWaitingTime());
            totalTurnaround += p.getTurnaroundTime();
            totalWaiting += p.getWaitingTime();
        }

        System.out.println("\nAverage Turnaround Time: " + (totalTurnaround / n));
        System.out.println("Average Waiting Time: " + (totalWaiting / n));

        // Print the execution order
        System.out.println("\nProcesses Execution Order:");
        for (String processName : executionOrder) {
            System.out.print(processName + " ");
        }
    }
}
