package solutions.exercise9;

import java.util.concurrent.Semaphore;

public class Fork {
    Semaphore m = new Semaphore(1);

    public Fork()
    {
        m.release();
    }

    public void get() throws InterruptedException
    {
        m.acquire();
    }

    public void layBack()
    {
        m.release();
    }
}
