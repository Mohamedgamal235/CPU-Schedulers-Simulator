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

package scheduling;

public class Process implements Comparable<Process> {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int remainingBurstTime;
    private int priority;
    private int waitingTime;
    private int turnaroundTime;

    public Process(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
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
    public void decRemainingTime() {
        remainingBurstTime--;
    }
    public int getPriority() {
        return priority;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }
    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    @Override
    public int compareTo(Process other) {
        // Compare by priority first, then by arrival time if priorities are equal
        if (this.priority != other.priority) {
            return Integer.compare(this.priority, other.priority);
        } else {
            return Integer.compare(this.arrivalTime, other.arrivalTime);
        }
    }
}
