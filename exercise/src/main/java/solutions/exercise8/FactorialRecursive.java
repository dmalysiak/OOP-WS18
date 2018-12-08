package solutions.exercise8;

import java.math.BigDecimal;

public class FactorialRecursive extends Factorial {
    @Override
    protected BigDecimal calculateFactorial_(BigDecimal n) {
        BigDecimal res = n;

        if(n.compareTo(BigDecimal.ONE)==0)
        {
            res = BigDecimal.ONE;
        }
        else
        {
            res = res.multiply( calculateFactorial_(n.subtract(BigDecimal.ONE)) );
        }

        return res;
    }
}
