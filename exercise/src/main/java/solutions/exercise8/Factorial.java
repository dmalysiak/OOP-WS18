package solutions.exercise8;

import java.math.BigDecimal;

public abstract class Factorial {

    public BigDecimal calculateFactorial(BigDecimal n)
    {
        long a = System.currentTimeMillis();
        BigDecimal res = calculateFactorial_(n);
        long b = System.currentTimeMillis();
        System.out.println("Time needed in ms: "+(b-a));
        return res;
    }

    protected abstract BigDecimal calculateFactorial_(BigDecimal n);

}
