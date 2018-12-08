package solutions.exercise8;

import java.math.BigDecimal;

public class FactorialRecursive extends Factorial {
    @Override
    protected BigDecimal calculateFaculty_(BigDecimal n) {
        BigDecimal res = n;

        if(n.compareTo(BigDecimal.ONE)==0)
        {
            res = BigDecimal.ONE;
        }
        else
        {
            res = res.multiply( calculateFaculty_(n.subtract(BigDecimal.ONE)) );
        }

        return res;
    }
}
