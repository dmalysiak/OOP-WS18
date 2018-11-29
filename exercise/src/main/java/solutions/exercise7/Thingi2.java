package solutions.exercise7;

import java.util.Observable;

public class Thingi2 extends Observable implements Runnable {
    @Override
    public void run() {
        setChanged();
        this.notifyObservers();
    }
}
