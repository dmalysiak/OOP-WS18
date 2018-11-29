package solutions.exercise7;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable {
    Set<Observer> observers = new HashSet<>();

    public void addObserver(Observer o)
    {
        observers.add(o);
    }

    public void removeObserver(Observer o)
    {
        observers.remove(o);
    }

    final protected void updateObservers()
    {
        observers.stream().forEach(observer -> {observer.update();});
    }
}
