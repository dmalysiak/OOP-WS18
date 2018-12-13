package solutions.exercise9;

import java.util.stream.IntStream;

public class Philosopher extends Thread{

    final static int SLEEP_TIME_IN_MS = 300;
    final static int MAX_ITERATIONS = 10;
    private String name;
    private Fork leftFork;
    private Fork rightFork;


    public Philosopher(String name,Fork leftFork,Fork rightFork)
    {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run()
    {
        IntStream.range(0,MAX_ITERATIONS).forEach( i -> {
            try {
                think();
                eat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void think() throws InterruptedException
    {
        System.out.println(name+" is thinking!");
        Thread.sleep(SLEEP_TIME_IN_MS);
    }

    private void eat() throws InterruptedException
    {
        leftFork.get();
        rightFork.get();
        System.out.println(name+" is eating!");
        leftFork.layBack();
        rightFork.layBack();
    }
}
