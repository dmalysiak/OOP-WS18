package solutions.exercise9;

public class MaxResult {
    public long segment;
    public int result;

    public MaxResult(long segment, int result)
    {
        this.segment = segment;
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "("+segment+","+result+")";
    }
}
