package cooldudes.stalkmarket.model;

public class Transaction implements Comparable<Transaction>{

    private String tId, sId;
    private int action, cost, balance;
    private long time;

    public Transaction(){}

    public Transaction(int actionType, int cost, int balance){
        this.action = actionType;
        this.cost = cost;
        this.balance = balance;
        // records time of request
        this.time = System.currentTimeMillis();
    }

    public int getTimePassed(){
        // finds time difference in seconds
        Long diff = System.currentTimeMillis() - time;
        int secDiff = (int) (diff / 1000);
        return secDiff;
    }

    // formats how long has passed
    public String getStringTimeLeft(long time){
        long t = getTimePassed();
        String timeLeft = "";
        if (t < 60){
            timeLeft =  t + " sec";
        } else if (t < 3600) {
            timeLeft += (t / 60) + " min";
        } else {
            timeLeft += (t/3600) + " hours";
        }
        timeLeft += " ago";
        return timeLeft;
    }

    // storing and obtaining the contents of the Firebase into objects
    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    // determining which transactions are of higher priority based on their how long ago it was sent
    @Override
    public int compareTo(Transaction o) {
        // returns 0 (same), 1 (puts o higher), -1 (puts this higher)
        if (getTimePassed() > o.getTimePassed()){
            return 1;
        } else {
            return -1;
        }
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
