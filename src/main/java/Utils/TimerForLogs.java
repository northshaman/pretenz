package Utils;

/**
 * MakeTimer
 */
public class TimerForLogs {
    private long startTime;

    public TimerForLogs() {
        this.startTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return startTime;
    }

    public long getRound() {
        Long round = (System.currentTimeMillis() - this.getStartTime())/1000;
        return round;
    }

    public void resetTimer(){
        this.startTime = System.currentTimeMillis();
    }
}
