package scheduling;

import java.util.*;
import java.lang.Math.* ;

class FCAIScheduler {
    // name - burstTime - arrivalTime - priority - Quantum
    private List<Process> processes ;
    private double v1 ; // last arrival time of all processes/10
    private double v2 ; // max burst time of all processes/10
    private Deque<Process> readyQueue = new LinkedList<>() ;
    private int currTime = 0 ;
    private int remainTime ;
    private int unusedQuantum = 0 ;
    Process currProcess ;
    boolean isPreempted ;
    private int currQuantum ;


    public FCAIScheduler(List<Process> processes) {
        this.processes = new ArrayList<>(processes);
    }

    private double updateFCAIFactor(Process currProcess) {
        return ((10 - currProcess.getPriority()) + (currProcess.getArrivalTime() / v1 ) + (currProcess.getRemainingBurstTime() / v2)) ;
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
        v1 = lastArr / 10 ;
        v2 = getMaxBurstTime(processes) / 10;


        for (Process p : processes) {
            p.setRemainingBurstTime(p.getBurstTime());
            double facor = Math.ceil(updateFCAIFactor(p)) ;
            p.setFcaiFactor(facor);
            System.out.println(facor);
        }

        while (!processes.isEmpty() || !readyQueue.isEmpty()){

            Iterator<Process> it = processes.iterator();
            while (it.hasNext()){  // push process based on arrival time
                Process p = it.next();
                if (currTime >= p.getArrivalTime()){
                    readyQueue.addLast(p);
                    it.remove();
                }
            }


            if (!readyQueue.isEmpty()){
                currProcess = readyQueue.pollFirst();
                currQuantum = (int)currProcess.getQuantum();
                int runTime = (int) Math.ceil(currQuantum * 0.4);
                unusedQuantum = currQuantum - runTime;

                remainTime = (int)currProcess.getRemainingBurstTime();
                runTime = Math.min(runTime, remainTime);
                currTime += runTime;
                remainTime -= runTime ;

                currProcess.setRemainingBurstTime(remainTime);
                currProcess.setBurstTime(remainTime);

                double facor = Math.ceil(updateFCAIFactor(currProcess)) ;
                currProcess.setFcaiFactor(facor);

                display(currProcess);

                Process preempted = currProcess;
                isPreempted = false ;
                for (Process p : readyQueue) {
                    if(p.getFcaiFactor() <= preempted.getFcaiFactor()){
                        preempted = p;
                        isPreempted = true ;
                    }
                }

                if (isPreempted && preempted != currProcess){
                    currProcess.setQuantum(currQuantum + unusedQuantum);
                    readyQueue.addLast(currProcess);
                    currProcess = preempted;

                    // smallest factor
                    Process p = readyQueue.stream()
                            .min(Comparator.comparingDouble(Process::getFcaiFactor))
                            .orElse(null);

                    readyQueue.remove(p);
                    readyQueue.addFirst(p);

                    System.out.println("Process " + preempted.getName() + " preempted " + currProcess.getName());
                    continue;
                }

                while (unusedQuantum > 0 && remainTime > 0){
                    currTime++ ;
                    unusedQuantum-- ;
                    remainTime-- ;
                    currProcess.setRemainingBurstTime(remainTime);
                    currProcess.setBurstTime(remainTime);

                    // update FCAI factor impoooorrttaannntt
                    facor = Math.ceil(updateFCAIFactor(currProcess)) ;
                    currProcess.setFcaiFactor(facor);

                    display(currProcess);

                    // if new arrivals
                    it = processes.iterator();
                    while (it.hasNext()){
                        Process p = it.next();
                        if(currTime >= p.getArrivalTime()){
                            readyQueue.addLast(p);
                            it.remove();
                        }
                    }

                    preempted = currProcess;
                    isPreempted = false ;
                    for (Process p : readyQueue) {
                        if(p.getFcaiFactor() <= preempted.getFcaiFactor()){
                            preempted = p;
                            isPreempted = true ;
                        }
                    }


                    if (isPreempted && preempted != currProcess){
                        currProcess.setQuantum(currQuantum + unusedQuantum);
                        readyQueue.addLast(currProcess);
                        currProcess = preempted;

                        Process p = readyQueue.stream()
                                .min(Comparator.comparingDouble(Process::getFcaiFactor))
                                .orElse(null);

                        readyQueue.remove(p);
                        readyQueue.addFirst(p);

                        System.out.println("Process " + preempted.getName() + " preempted " + currProcess.getName());
                        break; // out to start make % 40 moves firstly ya kbeeeer
                    }

                    // check is complete or not
                    if(currProcess.getRemainingBurstTime() <= 0)
                        System.out.println("Process " + currProcess.getName() + " completed at " + currTime);
                    else if (unusedQuantum == 0){
                        // quntam finish but process not complete
                        currProcess.setQuantum(currQuantum + 2);
                        readyQueue.addLast(currProcess);
                        System.out.println("Process " + currProcess.getName() + " re-added to queue with quantum " + currProcess.getQuantum());
                        break;
                    }
                }
            }
            else
                currTime++ ;
        }
    }


    public void display(Process currProcess){
        System.out.println("| Time -> " + currTime + " | Process -> " + currProcess.getName() + " | Brust -> " + currProcess.getBurstTime() + " | Arrival -> " + currProcess.getArrivalTime() + " | Priority -> " + currProcess.getPriority() + " | Quantum -> " + currProcess.getQuantum() + " | Factor -> " + currProcess.getFcaiFactor());
        System.out.println("-----------------------------------");
    }

}
