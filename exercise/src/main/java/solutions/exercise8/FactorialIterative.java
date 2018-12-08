package solutions.exercise8;

import java.math.BigDecimal;

public class FactorialIterative extends Factorial {
    @Override
    protected BigDecimal calculateFactorial_(BigDecimal n) {
        BigDecimal res = BigDecimal.ONE;
        for(BigDecimal i = BigDecimal.ONE; i.compareTo(n) <= 0;i=i.add(BigDecimal.ONE))
        {
            res = res.multiply(i);
        }
        return res;
    }
}
