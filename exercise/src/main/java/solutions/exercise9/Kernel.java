package solutions.exercise9;

public abstract class Kernel{
    abstract void compute(long idx);
    abstract Kernel copy();
    abstract Object getResult();
}
