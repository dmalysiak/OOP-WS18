package solutions.exercise9;

import org.junit.Test;

import java.util.stream.IntStream;

public class PhilosophersTest {

    @Test
    public void allThreadsShouldTerminate() {
        int count = 5;
        Exception ex = null;

        Philosopher[] philosophers = new Philosopher[count];
        Fork[] forks = new Fork[count];

        IntStream.range(0,count).forEach(i -> {
            forks[i] = new Fork();
        });

        IntStream.range(0,count).forEach(i -> {
            philosophers[i] = new Philosopher("Philosopher "+i,forks[i],forks[(i+1) % count]);
        });

        IntStream.range(0,count).forEach(i -> {
            philosophers[i].start();
        });

        for(int i=0;i<count;i++){
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                ex = e;
            }
        };

        assert(ex == null);
    }
}
