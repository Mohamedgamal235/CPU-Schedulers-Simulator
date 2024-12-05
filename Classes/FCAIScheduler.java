package scheduling;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math.* ;

class FCAIScheduler {
    // name - burstTime - arrivalTime - priority - Quantum
    private List<Process> processes;
    private double v1; // last arrival time of all processes/10
    private double v2; // max burst time of all processes/10
    private Deque<Process> readyQueue = new LinkedList<>() ;
    private int currTime = 0 ;

    private double updateFCAIFactor(Process currProcess) {
        return (10 - currProcess.getPriority()) + (currProcess.getArrivalTime() / v1 ) + (currProcess.getRemainingBurstTime() / v2) ;
    }

    private static double getMaxBurstTime(List<Process> process){
        double maxBurstTime = 0;
        for (Process p : process)
            maxBurstTime = Math.max(maxBurstTime , p.getBurstTime());
        return maxBurstTime;
    }

    public void fcaiScheduling(){
        int len = processes.size();
        double lastArr = processes.get(len - 1).getArrivalTime() ;
        double v1 = lastArr / 10 ;
        double v2 = getMaxBurstTime(processes) / 10;

        for (Process p : processes) {
            double facor = Math.ceil(updateFCAIFactor(p)) ;
            p.setFcaiFactor(facor);
        }

        while (!processes.isEmpty() || !readyQueue.isEmpty()){
            for (Process p : processes) {
                if (currTime >= p.getArrivalTime()){
                    readyQueue.addLast(p);
                    processes.remove(p);
                }
            }


            if (!readyQueue.isEmpty()){
                Process currProcess = readyQueue.pollFirst();
                int currQuantum = currProcess.getQuantum();
                int runTime = (int) Math.ceil(currQuantum * 0.4);
                int remainTime = currProcess.getRemainingBurstTime();
                runTime = Math.min(runTime, remainTime);
                currTime += runTime;
                currProcess.setRemainingBurstTime(remainTime - runTime);

                // after %40 check preempted

            }
        }
    }


}
