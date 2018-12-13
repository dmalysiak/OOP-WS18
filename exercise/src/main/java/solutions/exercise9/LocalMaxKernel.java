package solutions.exercise9;

import java.util.Arrays;

public class LocalMaxKernel extends Kernel {

    private int[] data;
    private MaxResult result;
    private int localSize;

    public LocalMaxKernel(int[] data, int localSize)
    {
        this.data = data;
        this.localSize = localSize;
    }

    @Override
    public final Object getResult()
    {
        return result;
    }

    @Override
    void compute(long idx) {
        long startIndex = localSize * idx;
        long endIndex = 0;

        if(startIndex + localSize <= data.length)
        {
            endIndex = startIndex + localSize;
        }
        else
        {
            endIndex = startIndex + (data.length - startIndex);
        }

        //get the subarray
        final int[] localData = Arrays.copyOfRange(data, (int) startIndex, (int) endIndex);

        result = new MaxResult(idx,Arrays.stream(localData).max().getAsInt());
    }

    @Override
    Kernel copy() {
        return new LocalMaxKernel(data, localSize);
    }


}
