package solutions.exercise7;

public class Thingi extends Observable implements Runnable {
    @Override
    public void run() {
        this.updateObservers();
    }
}
