package scheduling;

class Process {
    private String name ;
    private int priority;
    private int burstTime;
    private int arrivalTime;
    private  int remainingBurstTime;
    private int waitingTime;
    private int quantum ;

    public Process(String name, int burstTime, int arrivalTime, int priority, int quantum) {
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.quantum = quantum;
    }
}
