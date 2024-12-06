package scheduling;

// class Process {
//     private String name ;
//     private int priority;
//     private int burstTime;
//     private int arrivalTime;
//     private  int remainingBurstTime;
//     private int waitingTime;
//     private int quantum ;
//     int completionTime;
//     int turnaroundTime;
//     int id;

//     public Process(String name, int burstTime, int arrivalTime, int priority, int quantum) {
//         this.name = name;
//         this.burstTime = burstTime;
//         this.arrivalTime = arrivalTime;
//         this.priority = priority;
//         this.quantum = quantum;
//         this.id=id;
//     }
// }

public class Process {
    private String name;
    private int burstTime;
    private int arrivalTime;
    private int remainingBurstTime;
    private int waitingTime;
    private int completionTime;
    private int turnaroundTime;
    private boolean isCompleted;
    private int priority;
    private int quantum ;
    int id;
    public Process(String name, int burstTime, int arrivalTime,int priority,int quantum) {
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.remainingBurstTime = burstTime;
        this.isCompleted = false;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
