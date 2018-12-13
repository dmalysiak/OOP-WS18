package solutions.exercise9;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class KernelLauncher {

    public final Set<?> launchKernel(Kernel k, Long threadCount)
    {
        KernelThread[] threads = new KernelThread[threadCount.intValue()];

        //create the threads
        LongStream.range(0, threadCount).forEach( i -> {threads[(int)i] = new KernelThread(k.copy(),i);} );

        //start the threads
        LongStream.range(0, threadCount).forEach( i -> {threads[(int)i].start();} );

        //wait for the threads
        LongStream.range(0, threadCount).forEach( i -> {
            try {
                threads[(int)i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } );

        //collect the results
        return LongStream.range(0, threadCount).mapToObj( i -> { return threads[(int)i].getResult();} ).collect(Collectors.toSet());
    }

}
