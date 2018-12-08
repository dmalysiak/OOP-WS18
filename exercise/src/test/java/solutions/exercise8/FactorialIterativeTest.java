package solutions.exercise8;

import org.junit.Test;

import java.math.BigDecimal;

public class FactorialIterativeTest {

    @Test
    public void shouldSucceed()
    {
        final String value = "30";
        Factorial fa = new FactorialIterative();
        assert(fa.calculateFactorial(new BigDecimal(value)).toString().compareTo("265252859812191058636308480000000")==0);
    }

}
