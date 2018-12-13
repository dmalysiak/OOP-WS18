package solutions.exercise9;

public class KernelThread extends Thread {

    private Kernel k;
    private long threadID;
    private KernelThread(){}

    public KernelThread(Kernel k, long threadID)
    {
        this.k = k;
        this.threadID = threadID;
    }

    @Override
    public final void run()
    {
        k.compute(threadID);
    }

    public final Object getResult()
    {
        return k.getResult();
    }

}
