package solutions.exercise9;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        /*int count = 5;

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
        });*/

        int size = 7;
        int localSize = 3;
        Long threads = (long)Math.ceil((float)size / (float)localSize);
        int[] array = new int[size];

        IntStream.range(0,size).forEach(i->{array[i] = (int)(10.0 * Math.random());});

        LocalMaxKernel kernel = new LocalMaxKernel(array, localSize);
        KernelLauncher launcher = new KernelLauncher();
        Set<?> result = launcher.launchKernel(kernel,threads);

        Arrays.stream(array).forEach(i -> System.out.print(i+","));
        System.out.println("");
        result.stream().forEach(r -> System.out.print(r.toString()+","));
    }
}
