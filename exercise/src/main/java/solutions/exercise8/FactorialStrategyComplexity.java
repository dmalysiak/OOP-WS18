package solutions.exercise8;

import java.math.BigDecimal;

public class FactorialStrategyComplexity implements FactorialStrategy{
    @Override
    public Factorial getStrategy(BigDecimal n) {
        if(integerDigits(n)<3) {
            return new FactorialRecursive();
        }
        else {
            return new FactorialIterative();
        }
    }

    private static int integerDigits(BigDecimal n) {
        n = n.stripTrailingZeros();
        return n.precision() - n.scale();
    }
}
