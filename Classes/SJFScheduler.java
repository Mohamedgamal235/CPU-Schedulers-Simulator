package scheduling;
import java.util.Scanner;
import java.util.*;
class SJFScheduler {
 public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time  " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        
        int currentTime = 0;
        List<Process> completedProcesses = new ArrayList<>();
        while (completedProcesses.size() < n) {
            
            List<Process> readyQueue = new ArrayList<>();
            for (Process p : processes) {
                if (!completedProcesses.contains(p) && p.arrivalTime <= currentTime) {
                    readyQueue.add(p);
                }
            }

            if (!readyQueue.isEmpty()) {
                readyQueue.sort(Comparator.comparingInt(p -> p.burstTime));
                Process selected = readyQueue.get(0);

                currentTime += selected.burstTime;
                selected.completionTime = currentTime;
                selected.turnaroundTime = selected.completionTime - selected.arrivalTime;
                selected.waitingTime = selected.turnaroundTime - selected.burstTime;

                completedProcesses.add(selected);
            } else {
                currentTime++; // No process ready inc time 
            }
        }

        System.out.println("\nProcess\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        for (Process p : processes) {
            System.out.println(p.id + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                    p.completionTime + "\t\t" + p.turnaroundTime + "\t\t" + p.waitingTime);
        }

        double totalTurnaround = 0, totalWaiting = 0;
        for (Process p : processes) {
            totalTurnaround = totalTurnaround+p.turnaroundTime;
            totalWaiting =totalWaiting + p.waitingTime;
        }
        System.out.println("\nAverage Turnaround Time: " + (totalTurnaround / n));
        System.out.println("Average Waiting Time: " + (totalWaiting / n));
    }
}
